package com.stackroute.notifications.services;
import com.stackroute.notifications.model.ContactUs;
import com.stackroute.notifications.model.Email;
import com.stackroute.notifications.model.Notification;
import com.stackroute.notifications.model.NotificationMessage;
import com.stackroute.notifications.repository.ContactUsRepo;
import com.stackroute.notifications.repository.NotificationRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    ContactUsRepo contactUsRepo;

    public void sendMail(Email email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,false);
        mimeMessageHelper.setFrom("abc@gmail.com");
        mimeMessageHelper.setTo(email.getReceiver());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setText(email.getMessage());
//        mailSender.send(message);
    }

    @RabbitListener(queues = "register-notification")
    public void sendRegistrationNotification(String id) throws MessagingException {
        String subject = "Account Creation at LendingLane";
        String message = "Welcome to LendingLane! Your LendingLane account has been successfully created. Now you can log in to your account and start exploring our platform.\n\n" +
                "Here are some features you might find interesting:\n" +
                "- Quick and easy loan applications\n" +
                "- Competitive interest rates\n" +
                "- Secure and reliable platform\n" +
                "- Expert financial advice and guidance\n\n" +
                "Feel free to contact us if you have any questions or concerns. We're looking forward to helping you achieve your financial goals!\n\n" +
                "Best regards,\n" +
                "The LendingLane Team";
        this.sendMail(createEmail(id, subject, message));
        Notification notification = new Notification();
        notification.setId(id);
        NotificationMessage notificationMessage = new NotificationMessage(1, "Account created", LocalDateTime.now());
        List<NotificationMessage> notificationMessageList = notification.getMessages();
        notificationMessageList.add(notificationMessage);
        notification.setMessages(notificationMessageList);
        notificationRepository.save(notification);
    }

    @RabbitListener(queues = "loan-notification")
    public void sendLoanApplicationNotification(String id) throws MessagingException {
        String subject = "Loan Application Received - LendingLane";
        String emailMessage = "Hi " + id + ",\n\n" +
                "We have successfully received your loan application at LendingLane. Our team is currently reviewing your submission and will get back to you as soon as possible.\n\n" +
                "Please note that the standard waiting period for loan applications is up to 15 business days. During this time, we will thoroughly evaluate your application and determine your eligibility for the loan.\n\n" +
                "Once the review process is complete, we will notify you of our decision and provide further instructions on the next steps. In the meantime, you can log in to your LendingLane account to check the status of your application.\n\n" +
                "If you have any questions or concerns, please don't hesitate to contact our support team. We're here to help you with all your financial needs.\n\n" +
                "Thank you for choosing LendingLane!\n\n" +
                "Best regards,\n" +
                "The LendingLane Team";
        this.sendMail(createEmail(id,subject,emailMessage));
        Notification notification = createNotification(id,subject);
        notificationRepository.save(notification);
    }
    @RabbitListener(queues = "loan-approval-notification")
    public void sendApprovalNotification(JSONObject object) throws MessagingException {
        String lenderId = object.get("sender").toString();
        String borrowerId = object.get("receiver").toString();
        String subject = "Loan Approved - LendingLane";
        String emailMessage = "Greetings " + borrowerId + ",\n\n" +
                "We are pleased to inform you that your loan application has been approved by " + lenderId + ". Congratulations!\n\n" +
                "Here's a summary of your loan details:\n" +
                "- Loan Amount: [insert loan amount]\n" +
                "- Interest Rate: [insert interest rate]\n" +
                "- Loan Term: [insert loan term]\n" +
                "- Monthly Payment: [insert monthly payment]\n\n" +
                "To accept the loan and proceed with the disbursement process, please log in to your LendingLane account and follow the instructions provided. Once you complete the necessary steps, the funds will be transferred to your designated bank account.\n\n" +
                "If you have any questions or need assistance, feel free to contact our support team. We're always here to help you with your financial needs.\n\n" +
                "Thank you for choosing LendingLane, and we wish you success in your financial journey!\n\n" +
                "Best regards,\n" +
                "The LendingLane Team";
        sendMail(this.createEmail(borrowerId,subject,emailMessage));
        Notification notification = createNotification(borrowerId,subject);
        notificationRepository.save(notification);
    }
    @RabbitListener(queues = "pay-emi-notification")
    public void sendEmiPaymentNotification(JSONObject object) throws MessagingException {
        String borrowerId = object.get("sender").toString();
        String lenderId = object.get("receiver").toString();
        String subject = "EMI Payment Received - LendingLane";
        String emailMessageForBorrower = "Greetings " + borrowerId + ",\n\n" +
                "We want to confirm that your EMI payment has been successfully processed. Here are the payment details:\n" +
                "- EMI Amount: [insert EMI amount]\n" +
                "- Payment Date: [insert payment date]\n" +
                "- Remaining Loan Balance: [insert remaining loan balance]\n\n" +
                "Thank you for making your payment on time. Consistent, timely payments will help improve your credit score and financial standing.\n\n" +
                "If you have any questions or concerns, please feel free to contact our support team.\n\n" +
                "Best regards,\n" +
                "The LendingLane Team";
        String emailMessageForLender = "Greetings " + lenderId + ",\n\n" +
                "We want to inform you that the EMI payment from " + borrowerId + " has been successfully processed. Here are the payment details:\n" +
                "- EMI Amount: [insert EMI amount]\n" +
                "- Payment Date: [insert payment date]\n" +
                "- Remaining Loan Balance: [insert remaining loan balance]\n\n" +
                "Thank you for using LendingLane to manage your loan. If you have any questions or concerns, please feel free to contact our support team.\n\n" +
                "Best regards,\n" +
                "The LendingLane Team";
        sendMail(this.createEmail(borrowerId,subject,emailMessageForBorrower));
        sendMail(this.createEmail(lenderId,subject,emailMessageForLender));
        Notification notification = createNotification(lenderId,subject);
        notificationRepository.save(notification);
    }
    public Email createEmail(String id,String subject, String message){
        Email email = new Email();
        email.setReceiver(id);
        email.setSubject(subject);
        email.setMessage(message);
        return email;
    }
    private Notification createNotification(String id,String message) throws MessagingException {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if(optionalNotification.isPresent()){
            Notification notification =optionalNotification.get();
            List<NotificationMessage> notificationMessageList = notification.getMessages();
            NotificationMessage notificationMessage = new NotificationMessage(notificationMessageList.size()+1,message,LocalDateTime.now());
            notificationMessageList.add(notificationMessage);
            notification.setMessages(notificationMessageList);
            return notification;
        }
        throw new RuntimeException("User doesn't exist");
    }
    public List<NotificationMessage> getAllNotification(String id){
        if(notificationRepository.findById(id).isPresent())
            return notificationRepository.findById(id).get().getMessages();
        throw new RuntimeException("User doesn't exist");
    }

    public ContactUs saveContact(ContactUs contact) {
        return contactUsRepo.save(contact);
    }

}

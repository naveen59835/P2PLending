package com.stackroute.notifications.services;
import com.stackroute.notifications.model.Email;
import com.stackroute.notifications.model.Notification;
import com.stackroute.notifications.model.NotificationMessage;
import com.stackroute.notifications.repository.NotificationRepository;
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

    public void sendMail(Email email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,false);
        mimeMessageHelper.setFrom("ashu57das@gmail.com");
        mimeMessageHelper.setTo(email.getReceiver());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setText(email.getMessage());
        mailSender.send(message);
    }
    @RabbitListener(queues = "register-notification")
    public void sendRegistrationNotification(String id) throws MessagingException {
        String subject = "Account creation";
        String message = "Welcome to LendingLane !, Your LendingLane account has been successfully created, now you can login to your account";
        this.sendMail(createEmail(id,subject,message));
        Notification notification = new Notification();
        notification.setId(id);
        NotificationMessage notificationMessage = new NotificationMessage(1,"Account created", LocalDateTime.now());
        List<NotificationMessage> notificationMessageList = notification.getMessages();
        notificationMessageList.add(notificationMessage);
        notification.setMessages(notificationMessageList);
        notificationRepository.save(notification);
    }
    @RabbitListener(queues = "loan-notification")
    public void sendLoanApplicationNotification(String id) throws MessagingException {
        String subject = "Loan applied";
        String emailMessage = "Hi ! "+id+" , you have successfully applied for a loan. The standard waiting period is upto 15 days.\n Regards LendingLane";
        this.sendMail(createEmail(id,subject,emailMessage));
        Notification notification = createNotification(id,subject);
        notificationRepository.save(notification);
    }
//    @RabbitListener(queues = "approval")
//    public void sendApprovalNotification(String borrowerId,String lenderId){
//        String subject = "Loan approved";
//        String emailMessage = "Greeting,\n you loan has been approved by "+ lenderId;
//        this.createEmail(borrowerId,subject,emailMessage);
//        Notification notification = createNotification(borrowerId,subject);
//        notificationRepository.save(notification);
//    }
    private Email createEmail(String id,String subject, String message){
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

}

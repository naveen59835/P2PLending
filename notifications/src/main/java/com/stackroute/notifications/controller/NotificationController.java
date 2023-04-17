package com.stackroute.notifications.controller;

import com.stackroute.notifications.model.ContactUs;
import com.stackroute.notifications.model.Email;
import com.stackroute.notifications.services.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    @Autowired
    NotificationServiceImpl notificationService;
    @GetMapping("/getAll/{id}")
    public ResponseEntity<?> getAll(@PathVariable String id) throws MessagingException {
        return new ResponseEntity<>(notificationService.getAllNotification(id), HttpStatus.OK);
    }
    @PostMapping("/reachus")
    public void contactUs(@RequestBody ContactUs contactus) throws MessagingException {
        String userEmail = contactus.getEmail();
        String subject = "LendingLane - We've Received Your Inquiry";
        String emailMessage = "Hi there," +contactus.getName()+ ",\n\n" +
                "Thank you for reaching out to LendingLane. We have successfully received your message and appreciate your interest in our services.\n\n" +
                "Our support team is reviewing your inquiry and will respond as soon as possible, typically within 1-2 business days. In the meantime, you may find answers to your questions in our FAQ section or by logging in to your LendingLane account.\n\n" +
                "We're committed to providing exceptional support and ensuring your satisfaction. Please don't hesitate to reach out if you have any further questions or concerns.\n\n" +
                "Thank you for choosing LendingLane, and we look forward to assisting you!\n\n" +
                "Best regards,\n" +
                "The LendingLane Team";

        Email emails = notificationService.createEmail(userEmail, subject, emailMessage);
        notificationService.sendMail(emails);
    }

    @PostMapping("/contactus")
    public ResponseEntity<?> post(@RequestBody ContactUs contact){
        return new ResponseEntity <>(notificationService.saveContact(contact), HttpStatus.OK);

    }
    }


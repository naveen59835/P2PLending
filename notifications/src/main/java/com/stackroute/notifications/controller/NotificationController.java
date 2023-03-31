package com.stackroute.notifications.controller;

import com.stackroute.notifications.services.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/notification")
@CrossOrigin
public class NotificationController {
    @Autowired
    NotificationServiceImpl notificationService;
    @GetMapping("/getAll/{id}")
    public ResponseEntity<?> getAll(@PathVariable String id) throws MessagingException {
        return new ResponseEntity<>(notificationService.getAllNotification(id), HttpStatus.OK);
    }
}

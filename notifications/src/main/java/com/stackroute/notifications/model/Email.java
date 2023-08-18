package com.stackroute.notifications.model;

import lombok.Data;

@Data
public class Email {
    private String message;
    private String receiver;
    private String subject;

}
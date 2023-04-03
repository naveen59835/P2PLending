package com.stackroute.notifications.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificationMessage {
    private int id;
    private String message;
    private LocalDateTime timeStamp;
}
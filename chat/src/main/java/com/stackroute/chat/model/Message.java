package com.stackroute.chat.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Message {
    private String message;
    private LocalDateTime timeStamp;
    private String senderId;
    private String receiverId;
}

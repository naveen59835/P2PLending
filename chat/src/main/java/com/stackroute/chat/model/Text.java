package com.stackroute.chat.model;

import lombok.Data;

@Data
public class Text {
    private String message;
    private String role;
    private String recipientId;
    private String senderId;
}

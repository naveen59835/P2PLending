package com.stackroute.notifications.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Notification {
    @Id
    private String id;
    private List<NotificationMessage> messages = new ArrayList<>();
}

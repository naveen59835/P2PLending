package com.stackroute.chat.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Chat {
    @Id
    private String id;
    private String borrowerId;
    private String lenderId;
    private List <Message> messages = new ArrayList<>();

}

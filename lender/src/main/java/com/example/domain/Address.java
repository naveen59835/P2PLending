package com.example.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Address {

    private String address;
    private String city;
    private String pin;
    private String state;

}

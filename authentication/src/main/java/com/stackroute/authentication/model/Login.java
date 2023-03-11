package com.stackroute.authentication.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Login {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String name;
    private String role; // ENUM Can be used
}

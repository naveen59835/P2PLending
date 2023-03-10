package com.example.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Document
public class Lender {
    @Id
    private String emailId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String confirmPassword;
    private Address address;
    private String pan;
    private String aadhaar;
    private double amountToInvest;
    private double interestRate;
    private int loanDuration;
    private int creditScore;
    private double investedAmount;
    private double availableBalance;


}

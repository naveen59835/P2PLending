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
    private Address address=new Address();
    private String pan;
    private String aadhaar;
    private byte[] aadhaarImage=new byte[0];
    private  byte[] panImage=new byte[0];
    private double amountToInvest;
    private double interestRate;
    private int loanDuration;
    private String creditScore;
    private double investedAmount;
    private double availableBalance;
}

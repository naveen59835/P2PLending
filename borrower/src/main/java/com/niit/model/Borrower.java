/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Borrower {
    private String firstName;
    private String lastName;
    @Id
    private String emailId;
    private String password;
    private String confirmPassword;
    private String aadhaarNo="";
    private String panNo="";
    private String phoneNo="";
    private double amount;
    private double cibilScore;
    private Address address=new Address();


}

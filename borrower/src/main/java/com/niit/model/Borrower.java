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
    String firstName;
    String lastName;
    @Id
    String emailId;
    String password;
    String confirmPassword;
    String aadhaarNo;
    String panNo;
    String phoneNo;
    double amount;
    double cibilScore;
    Address address;


}

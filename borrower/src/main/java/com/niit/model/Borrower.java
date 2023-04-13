/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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
    private int cibilScore;
    private Address address=new Address();
    private byte[] aadharImage=new byte[0];
    private byte[] panImage=new byte[0];
    private byte[] cibilImage=new byte[0];
    private String aadharImageName;
    private String panImageName;
    private String cibilImageName;



}

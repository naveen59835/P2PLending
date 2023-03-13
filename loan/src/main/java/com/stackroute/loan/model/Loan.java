package com.stackroute.loan.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Loan {
    @Id
    private String id;
    private double amount;
    private String lenderId;
    private String borrowerId;
    private double interestRate;
    private boolean approved;
    private EMI emi;
    private boolean rejected;
    private boolean expired;
    private int terms;
}

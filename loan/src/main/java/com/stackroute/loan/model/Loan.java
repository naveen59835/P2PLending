package com.stackroute.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document
@Data
@AllArgsConstructor
public class Loan {
    @Id
    private String id;
    private double amount;
    private String lenderId;
    private String borrowerId;
    private double interestRate;
    private boolean approved;
    private List<EMI> emi = new ArrayList<>();
    private boolean rejected;
    private boolean expired;
    private int terms;
    private LocalDate dateOfLoan;


    public void addToEMI(EMI emi){
        this.emi.add(emi);
    }
}

package com.stackroute.loan.configuartion;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {
    private double amount;
    private String sender;
    private String receiver;
    private Date paymentDate;
    private String loanId;
}

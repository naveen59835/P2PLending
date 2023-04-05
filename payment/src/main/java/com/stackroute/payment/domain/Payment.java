package com.stackroute.payment.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document
public class Payment {
    @Id
    private String id;
    private double amount;
    private String fromAccount;
    private String toAccount;
    private String paymentStatus;
    private Date paymentDate;
    private String loanId;

}

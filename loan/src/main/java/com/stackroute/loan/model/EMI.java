package com.stackroute.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class EMI {
    private int id;
    private double price;
    private LocalDate startDate;
    private LocalDate paymentDate;
    private boolean paymentStatus;
    private String paymentId;
    private boolean isLateFeeAdded = false;
    private double lateFee=0;

    public EMI(int id, double price, boolean paymentStatus) {
        this.id = id;
        this.price = price;
        this.paymentStatus = paymentStatus;
        this.startDate = LocalDate.now();
    }
}

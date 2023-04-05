package com.stackroute.loan.configuartion;

import lombok.Data;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmiDTO {
    private String paymentId;
    private String loanId;
    private int emiId;
    private LocalDate time;

    public EmiDTO(JSONObject jsonObject){
        this.setPaymentId(jsonObject.get("paymentId").toString());
        this.setLoanId(jsonObject.get("loanId").toString());
        this.setTime(LocalDate.now());
        this.setEmiId((int) jsonObject.get("emiId"));
    }


}

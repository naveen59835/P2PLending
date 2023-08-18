package com.stackroute.payment.controller.config;

import com.stackroute.payment.domain.Payment;
import lombok.Data;
import org.json.simple.JSONObject;

@Data
public class PaymentDTO {

    private JSONObject jsonObject;

    public void setJsonObject(Payment payment) {

        this.jsonObject = new JSONObject();
        this.jsonObject.put("amount",payment.getAmount());
        this.jsonObject.put("date",payment.getPaymentDate());
        this.jsonObject.put("sender",payment.getFromAccount());
        this.jsonObject.put("receiver",payment.getToAccount());
        this.jsonObject.put("loanId",payment.getLoanId());
    }
}

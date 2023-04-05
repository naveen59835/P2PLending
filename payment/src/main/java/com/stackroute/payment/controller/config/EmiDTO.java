package com.stackroute.payment.controller.config;

import com.stackroute.payment.domain.Payment;
import lombok.Data;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;

@Data
public class EmiDTO {
    private JSONObject jsonObject;

    public void setJsonObject(String paymentId, String loanId, int emiId) {

        this.jsonObject = new JSONObject();
        this.jsonObject.put("paymentId",paymentId);
        this.jsonObject.put("loanId",loanId);
        this.jsonObject.put("emiId",emiId);
        this.jsonObject.put("date", LocalDateTime.now());
    }
}

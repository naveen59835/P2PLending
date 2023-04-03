package com.stackroute.payment.controller.config;

import org.json.JSONObject;

public class PaymentDTO {

    private JSONObject jsonObject;

    public PaymentDTO() {
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}

package com.niit.configuration;

import org.json.simple.JSONObject;

public class BorrowerDTO {
    private JSONObject jsonObject;

    public BorrowerDTO() {

    }


    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}

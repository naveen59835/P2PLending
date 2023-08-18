package com.stackroute.recommendation.configuration;

import lombok.Data;
import org.json.simple.JSONObject;

@Data
public class LoanDTO {
    private double amount;
    private String borrowerId;
    private double interestRate;
    private String loanId;

    public LoanDTO(JSONObject object){
        this.setAmount((double) object.get("amount"));
        this.setBorrowerId(object.get("borrowerId").toString());
        this.setInterestRate((double) object.get("interestRate"));
        this.setLoanId(object.get("loanId").toString());
    }

}

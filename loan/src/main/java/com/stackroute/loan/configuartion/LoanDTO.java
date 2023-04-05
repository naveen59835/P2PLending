package com.stackroute.loan.configuartion;

import com.stackroute.loan.model.Loan;
import lombok.Data;
import org.json.simple.JSONObject;

@Data
public class LoanDTO {
    private JSONObject jsonObject;

    public void setSelectiveJsonObject(Loan loan) {
        this.jsonObject = new JSONObject();
        this.jsonObject.put("amount", loan.getAmount());
        this.jsonObject.put("borrowerId", loan.getBorrowerId());
        this.jsonObject.put("interestRate", loan.getInterestRate());
        this.jsonObject.put("loanId",loan.getId());
    }
}

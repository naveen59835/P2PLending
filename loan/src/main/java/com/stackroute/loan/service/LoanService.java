package com.stackroute.loan.service;

import com.stackroute.loan.model.Loan;
import org.json.simple.JSONObject;


import java.time.LocalDateTime;
import java.util.List;

public interface LoanService {
    Loan applyLoan(Loan loan);

    void approveLoan(JSONObject jsonObject);


    void emiPaid(JSONObject object);

    Loan getLoan(String loanId);

    List<Loan> getLoans(String id, String role);


    void addEMI();

    void payEMI(String loanId, int emiID, String paymentId);

    boolean hasDocuments(String id);



    List<Loan> findLoansFromLender(String id);
}

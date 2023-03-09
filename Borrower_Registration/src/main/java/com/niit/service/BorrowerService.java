package com.niit.service;

import com.niit.model.Borrower;

import java.util.List;

public interface BorrowerService {
    Borrower saveBorrower(Borrower borrower);
    List<Borrower> getBorrowerList();
    public Borrower getBorrowerByEmailId(String emailId);

}

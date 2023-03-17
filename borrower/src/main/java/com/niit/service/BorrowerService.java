package com.niit.service;

import com.niit.exception.BorrowerAlreadyFoundException;
import com.niit.model.Borrower;

import java.util.List;

public interface BorrowerService {
    Borrower saveBorrower(Borrower borrower) throws BorrowerAlreadyFoundException;
    List<Borrower> getBorrowerList();
    public Borrower getBorrowerByEmailId(String emailId);
    public Borrower updateBorrower(Borrower borrower,String emailId);
    public boolean deleteBorrower(String emailId);

}


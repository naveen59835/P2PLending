package com.example.service;

import com.example.domain.Address;
import com.example.domain.Lender;
import com.example.exception.LenderAlreadyExistException;
import com.example.exception.LenderNotFoundException;

public interface LenderService {

    Lender registerLender(Lender lender) throws LenderAlreadyExistException;
    public Lender getLenderById(String lenderId) throws LenderNotFoundException;
    public Lender updateLender(Lender lender, String emailId) throws LenderNotFoundException;
    public Lender updateLenderAadhaarImage(byte[] imageArray, String emailId) throws LenderNotFoundException;
    public Lender updatePanImage(byte[] Array, String emailId) throws LenderNotFoundException;
}

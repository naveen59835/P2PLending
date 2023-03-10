package com.example.service;

import com.example.domain.Lender;
import com.example.exception.LenderAlreadyExistException;

public interface LenderService {

    Lender registerLender(Lender lender) throws LenderAlreadyExistException;
}

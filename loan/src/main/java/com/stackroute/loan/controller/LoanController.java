package com.stackroute.loan.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {
    public ResponseEntity<?> getAllLoans(){
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

package com.stackroute.loan.controller;

import com.stackroute.loan.model.Loan;
import com.stackroute.loan.service.LoanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {
    @Autowired
    LoanServiceImpl loanService;
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllLoans(){
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{loanId}")
    public ResponseEntity<?> updateLoans(@RequestBody Loan loan, @PathVariable String loanId){
//        loanService.updateLoan(loanId,loan);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@RequestBody Loan loan){
        loanService.applyLoan(loan);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

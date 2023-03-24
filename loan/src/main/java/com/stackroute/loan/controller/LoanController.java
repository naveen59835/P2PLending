package com.stackroute.loan.controller;

import com.stackroute.loan.model.Loan;
import com.stackroute.loan.service.LoanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/loan")
@CrossOrigin
public class LoanController {
    @Autowired
    LoanServiceImpl loanService;
    @PostMapping("/getAll")
    public ResponseEntity<?> getAllLoans(@RequestBody Map<String,String> userData){
        try{
            return new ResponseEntity<>(loanService.getLoans(userData.get("id"),userData.get("role")),HttpStatus.OK);
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
    @GetMapping("/getLoan/{loanId}")
    public ResponseEntity<?> getLoan(@PathVariable String loanId){
        try{
        return new ResponseEntity<>(loanService.getLoan(loanId),HttpStatus.OK);}
        catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@RequestBody Loan loan){
        loanService.applyLoan(loan);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

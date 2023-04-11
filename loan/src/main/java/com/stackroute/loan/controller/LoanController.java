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
public class LoanController {

    LoanServiceImpl loanService;

    @Autowired
    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/Loan")
    public ResponseEntity<?> getAllLoans(@RequestParam("id") String id, @RequestParam("role") String role){
        try{
            return new ResponseEntity<>(loanService.getLoans(id,role),HttpStatus.OK);
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
        try{
            return new ResponseEntity<>(loanService.applyLoan(loan),HttpStatus.CREATED);
        }catch (Exception exception){
            System.out.println(exception);
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getLenderLoan/{id}")
    public ResponseEntity<?> getLenderLoan(@PathVariable String id){
        return new ResponseEntity<>(loanService.findLoansFromLender(id),HttpStatus.OK);
    }
}

/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.exception.BorrowerAlreadyFoundException;
import com.niit.model.Borrower;
import com.niit.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class BorrowerController {
    ResponseEntity responseEntity;
    @Autowired
    BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }
    @GetMapping("/demo")
    public ResponseEntity<String> get() {
        return new ResponseEntity<String>("Sample", HttpStatus.OK);
    }

    @PostMapping("/borrower")
    public ResponseEntity<?> saveBorrower(@RequestBody Borrower borrower) throws BorrowerAlreadyFoundException   {
        try {
            Borrower borrower1 = borrowerService.saveBorrower(borrower);
            return new ResponseEntity<Borrower>(borrower1, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("exception arised");
            throw new BorrowerAlreadyFoundException();
        }
    }

    @GetMapping("/borrowers")
    public ResponseEntity<?> getAllBorrowers() {
        try {
            responseEntity = new ResponseEntity(borrowerService.getBorrowerList(), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/borrower/{emailId}")
    public ResponseEntity<Borrower> getBorrowerByEmailId(@PathVariable String emailId) {
        Borrower borrower = borrowerService.getBorrowerByEmailId(emailId);
        if (borrower == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(borrower);
        }
    }

    @DeleteMapping("/borrower/{emailId}")
    public ResponseEntity<?> deleteBorrower(@PathVariable ("emailId") String emailId) {
              try {
            borrowerService.deleteBorrower(emailId);
            return responseEntity=new ResponseEntity<String>("Successfully deleted",HttpStatus.OK);
        } catch (Exception e) {
            return responseEntity=new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/borrowers/{emailId}")
    public ResponseEntity<?> updateBorrower(@RequestBody Borrower borrower, @PathVariable String emailId) {
        Borrower update=borrowerService.updateBorrower(borrower,emailId);
        if(update!=null){
            return new ResponseEntity<Borrower>(update,HttpStatus.OK);

        }else {
            return new ResponseEntity<String>("Failed to Update",HttpStatus.FAILED_DEPENDENCY);
        }
    }



}

/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.controller;

import com.niit.exception.BorrowerAlreadyFoundException;
import com.niit.model.Borrower;
import com.niit.repo.BorrowerRepo;
import com.niit.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/borrower")
public class BorrowerController {
    ResponseEntity responseEntity;
    BorrowerService borrowerService;
    @Autowired
    BorrowerRepo borrowerRepo;
    @Autowired
    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }
    @GetMapping("/demo")
    public ResponseEntity<String> get() {
        return new ResponseEntity<String>("Sample", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveBorrower(@RequestBody Borrower borrower) throws BorrowerAlreadyFoundException   {
        try {
            return new ResponseEntity<>(borrowerService.saveBorrower(borrower), HttpStatus.OK);

        } catch (BorrowerAlreadyFoundException e) {
            throw new BorrowerAlreadyFoundException();
        }
        catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    @GetMapping("/borrowers")
    public ResponseEntity<?> getAllBorrowers() {
        try {
            responseEntity = new ResponseEntity<>(borrowerService.getBorrowerList(), HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/borrower/{emailId}")
    public ResponseEntity<Borrower> getBorrowerByEmailId(@PathVariable String emailId) {
        try{
            Borrower borrower = borrowerService.getBorrowerByEmailId(emailId);
            if(borrower!=null){
                return new ResponseEntity<>(borrower,HttpStatus.OK);
            }else{
                throw new RuntimeException("Borrower not found") ;
            }
        }
        catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
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

    @PutMapping(value = "/borrowers/{emailId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateBorrower(@RequestPart("details") Borrower borrower, @PathVariable String emailId) {
        Borrower update = borrowerService.updateBorrower(borrower, emailId);
        if (update != null) {
            return new ResponseEntity<Borrower>(update, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Failed to Update", HttpStatus.FAILED_DEPENDENCY);
        }
    }



    @PutMapping(value = "/borrowers/image/{emailId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateBorrowerImage(@RequestPart(name = "aadhar",required = false) MultipartFile aadharImage,@RequestPart(name = "pan",required = false) MultipartFile panImage,@RequestPart(name = "cibil",required = false) MultipartFile cibilImage, @PathVariable String emailId) throws IOException {
        if(aadharImage!=null){
            borrowerService.saveBorrowerImage(aadharImage,emailId,aadharImage.getName());
        }
        if(panImage!=null){
            borrowerService.saveBorrowerImage(panImage,emailId,panImage.getName());
        }
        if(cibilImage!=null){
            borrowerService.saveBorrowerImage(cibilImage,emailId,cibilImage.getName());
        }
        return null;

    }










}

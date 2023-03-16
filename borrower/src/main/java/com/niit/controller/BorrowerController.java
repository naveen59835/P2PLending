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
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/borrower")
public class BorrowerController {
    ResponseEntity responseEntity;
    BorrowerService borrowerService;
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
                throw new RuntimeException("Borrower not found") ; // add custom exception
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
    public ResponseEntity<?> updateBorrower(@RequestPart("details") Borrower borrower,@RequestPart("aadhar") MultipartFile image, @PathVariable String emailId) throws IOException {
        borrower.setAadharImage(image.getBytes());
        Borrower update=borrowerService.updateBorrower(borrower,emailId);
        if(update!=null){
            return new ResponseEntity<Borrower>(update,HttpStatus.OK);

        }else {
            return new ResponseEntity<String>("Failed to Update",HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PutMapping("/borrowers/image/{emailId}")
    public ResponseEntity<?> updateBorrowerImage(@RequestPart() MultipartFile image,@PathVariable String emailId){
        System.out.println(image.getName());
        System.out.println(image.getOriginalFilename());
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

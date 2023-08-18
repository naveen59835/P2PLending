package com.example.controller;

import com.example.domain.Address;
import com.example.domain.Lender;
import com.example.exception.LenderAlreadyExistException;
import com.example.exception.LenderNotFoundException;
import com.example.service.LenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/lender")
public class LenderController {


    LenderService lenderService;

   private   ResponseEntity<?> responseEntity;


@Autowired
    public LenderController(LenderService lenderService) {
        this.lenderService = lenderService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerLender(@RequestBody Lender lender) throws LenderAlreadyExistException {
        try {
            Lender lender1=lenderService.registerLender(lender);
         return   new ResponseEntity<>(lender1, HttpStatus.CREATED);
        }
        catch (LenderAlreadyExistException e)
        {
            throw new LenderAlreadyExistException();
        }
        catch (Exception e)
        {

            return new ResponseEntity<String>("Error occur",HttpStatus.NOT_FOUND);
        }
   }

    @GetMapping("/lenderMail/{lenderId}")
    public ResponseEntity<?> getLenderDetailById(@PathVariable String lenderId) throws LenderNotFoundException {


        try {
            Lender lender = lenderService.getLenderById(lenderId);
            return new ResponseEntity<>(lender, HttpStatus.OK);
        } catch (LenderNotFoundException e) {
            throw new LenderNotFoundException();
        } catch (Exception e) {
            System.out.println();

            return new ResponseEntity<String>("Error occur", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/updateLender/{lenderId}")
    public ResponseEntity<?>updateLender(@RequestBody Lender lender , @PathVariable String lenderId) throws LenderNotFoundException {
        try {
            System.out.println(lender);
            Lender lender1=lenderService.updateLender(lender,lenderId);

            return new ResponseEntity<Lender>(lender1,HttpStatus.OK);

        } catch (LenderNotFoundException e) {
            throw new LenderNotFoundException();
        }
        catch (Exception e) {
            System.out.println();

            return new ResponseEntity<String>("Error occur", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/lenderImage/{lenderId}")
    public ResponseEntity<?> updateLenderAadhaarImage(@RequestPart("aadhaar") MultipartFile image, @PathVariable String lenderId) throws LenderNotFoundException {

        try {

            Lender lender1=lenderService.updateLenderAadhaarImage(image.getBytes(),lenderId);

            return new ResponseEntity<Lender>(lender1,HttpStatus.OK);

        } catch (LenderNotFoundException e) {
            throw new LenderNotFoundException();
        }
        catch (Exception e) {
            System.out.println();

            return new ResponseEntity<String>("Error occur", HttpStatus.NOT_FOUND);
        }



    }

    @PutMapping("/lenderPanImage/{lenderId}")
    public ResponseEntity<?> updateLenderPanImage(@RequestPart("pan") MultipartFile image1, @PathVariable String lenderId) throws LenderNotFoundException {

        try {

            Lender lender1=lenderService.updatePanImage(image1.getBytes(),lenderId);

            return new ResponseEntity<Lender>(lender1,HttpStatus.OK);

        } catch (LenderNotFoundException e) {
            throw new LenderNotFoundException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}

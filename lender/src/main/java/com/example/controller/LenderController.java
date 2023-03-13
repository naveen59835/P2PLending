package com.example.controller;

import com.example.domain.Lender;
import com.example.exception.LenderAlreadyExistException;
import com.example.service.LenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            System.out.println();

            return new ResponseEntity<String>("Error occur",HttpStatus.NOT_FOUND);
        }
   }



}

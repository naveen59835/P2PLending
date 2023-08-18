package com.stackroute.authentication.controller;

import com.stackroute.authentication.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> loginData){
        try{
            return new ResponseEntity<>(authService.loginUser(loginData), HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST); //will be changed with custom exception
        }
    }
    @GetMapping("/totalUsers")
    public ResponseEntity<?> getTotalUsers(){
        try{
            return new ResponseEntity<>(authService.getTotalUsers(), HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST); //will be changed with custom exception
        }
    }

}

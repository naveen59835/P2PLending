package com.stackroute.authentication.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Email or Password is not valid")
public class UsernamePasswordMismatchException extends Exception{
    public UsernamePasswordMismatchException(String message){
        super(message);
    }
}

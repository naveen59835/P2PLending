package com.stackroute.authentication.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "User Already Exists")
public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String message){
        super(message);
    }
}

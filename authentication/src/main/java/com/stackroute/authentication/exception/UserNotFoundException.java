package com.stackroute.authentication.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "User not found")
public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message){
        super(message);
    }
}

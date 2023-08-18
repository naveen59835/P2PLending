package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Lender is already present")

public class LenderAlreadyExistException extends Exception{
}

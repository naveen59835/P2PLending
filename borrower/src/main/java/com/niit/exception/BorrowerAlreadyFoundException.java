/*
 * Author : Naveen Kumar
 * Date : 10-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Borrower Already Found")
public class BorrowerAlreadyFoundException extends Exception {



}

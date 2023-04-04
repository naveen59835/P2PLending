/*
 * Author : Naveen Kumar
 * Date : 31-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.example.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Lender has already given review")
public class ReviewAlreadyExistsException extends Exception {

}

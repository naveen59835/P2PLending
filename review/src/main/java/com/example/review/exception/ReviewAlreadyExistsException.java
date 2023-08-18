

package com.example.review.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Lender has already given review")
public class ReviewAlreadyExistsException extends Exception {

}

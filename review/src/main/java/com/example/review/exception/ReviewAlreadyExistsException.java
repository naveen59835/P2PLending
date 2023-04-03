/*
 * Author : Naveen Kumar
 * Date : 31-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.example.review.exception;

public class ReviewAlreadyExistsException extends Exception {
    public ReviewAlreadyExistsException(String message) {
        super(message);
    }
}

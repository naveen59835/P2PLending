package com.example.review.service;

import com.example.review.exception.ReviewAlreadyExistsException;
import com.example.review.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewsForBorrower(String borrowerEmailId);

    public Review addReview(Review review) throws ReviewAlreadyExistsException;


}

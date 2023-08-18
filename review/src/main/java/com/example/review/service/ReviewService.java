package com.example.review.service;

import com.example.review.exception.ReviewAlreadyExistsException;
import com.example.review.model.Review;
import com.example.review.model.ReviewRating;

import java.util.List;

public interface ReviewService {
    public String addBorrower(Review review);
    public String addReview(String borrowerId, ReviewRating reviewRating)throws ReviewAlreadyExistsException;
    public List<ReviewRating> getAllRatingAndReview(String borrowerId);
    public int averageRating(String borrowerId);
}

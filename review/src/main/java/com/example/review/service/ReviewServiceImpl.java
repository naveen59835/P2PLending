/*
 * Author : Naveen Kumar
 * Date : 31-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.example.review.service;

import com.example.review.exception.ReviewAlreadyExistsException;
import com.example.review.model.Review;
import com.example.review.model.ReviewAndRating;
import com.example.review.repo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    ReviewRepo reviewRepository;

    public ReviewServiceImpl(ReviewRepo reviewRepository) {
        this.reviewRepository = reviewRepository;

    }


    @Override
    public List<Review> getReviewsForBorrower(String borrowerEmailId) {
        return reviewRepository.findByBorrowerEmailId(borrowerEmailId);
    }

    @Override
    public Review addReview(Review review) throws ReviewAlreadyExistsException {
        List<Review> existingReviews = reviewRepository.findByBorrowerEmailId(review.getBorrowerEmailId());
        for (Review existingReview : existingReviews) {
            for (ReviewAndRating existingReviewAndRating : existingReview.getReviewsAndRatings()) {
                if (existingReviewAndRating.getLenderEmailId().equals(review.getReviewsAndRatings().get(0).getLenderEmailId())) {
                    throw new ReviewAlreadyExistsException("A review from this lender already exists for this borrower.");
                }
            }
        }
        Review addedReview = reviewRepository.save(review);
        return addedReview;
    }


}

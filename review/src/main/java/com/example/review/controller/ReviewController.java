/*
 * Author : Naveen Kumar
 * Date : 31-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.example.review.controller;

import com.example.review.exception.ReviewAlreadyExistsException;
import com.example.review.model.Review;
import com.example.review.model.ReviewAndRating;
import com.example.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{borrowerEmailId}")
    public List<Review> getReviewsForBorrower(@PathVariable String borrowerEmailId) {
        return reviewService.getReviewsForBorrower(borrowerEmailId);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addReview(@RequestBody Review review) {
        try {
            String lenderEmailId = review.getReviewsAndRatings().get(0).getLenderEmailId();
            String borrowerEmailId = review.getBorrowerEmailId();
            List<Review> reviews = reviewService.getReviewsForBorrower(borrowerEmailId);
            for (Review r : reviews) {
                for (ReviewAndRating rr : r.getReviewsAndRatings()) {
                    if (rr.getLenderEmailId().equals(lenderEmailId)) {
                        throw new ReviewAlreadyExistsException("Lender has already given a review for this borrower.");
                    }
                }
            }
            Review addedReview = reviewService.addReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
        } catch (ReviewAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }






}

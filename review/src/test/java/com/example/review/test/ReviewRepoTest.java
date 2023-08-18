/*
 * Author : Naveen Kumar
 * Date : 11-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.example.review.test;


import com.example.review.model.Review;
import com.example.review.model.ReviewRating;
import com.example.review.repo.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataMongoTest
@ActiveProfiles("test")

public class ReviewRepoTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private Review review;

    @BeforeEach
    public void setUp() {
        review = new Review("borrower1", Arrays.asList(
                new ReviewRating("lender1", "Great experience!", 5),
                new ReviewRating("lender2", "Smooth transaction.", 4)
        ));
    }

    @AfterEach
    public void tearDown() {
        reviewRepository.deleteAll();
    }

    @Test
    public void testSaveReview_success() {
        Review savedReview = reviewRepository.save(review);
        Optional<Review> foundReview = reviewRepository.findById("borrower1");

        assertThat(foundReview).isNotEmpty();
        assertThat(foundReview.get()).usingRecursiveComparison().isEqualTo(savedReview);
    }

    @Test
    public void testSaveReview_failure() {
        Optional<Review> foundReview = reviewRepository.findById("nonExistentBorrowerId");
        assertThat(foundReview).isEmpty();
    }

}

/*
 * Author : Naveen Kumar
 * Date : 11-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.example.review.test;
import com.example.review.controller.ReviewController;
import com.example.review.exception.ReviewAlreadyExistsException;
import com.example.review.model.ReviewRating;
import com.example.review.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)

public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    private ReviewRating reviewRating;

    @BeforeEach
    public void setUp() {
        reviewRating = new ReviewRating("lender1", "Great experience!", 5);
    }

    @Test
    public void testAddReview_success() throws Exception {
        when(reviewService.addReview(anyString(), any(ReviewRating.class))).thenReturn("Review added successfully");

        mockMvc.perform(post("/api/v1/review/add/{borrowerId}", "borrower1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reviewRating)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.review").value("Review added successfully"));

        verify(reviewService, times(1)).addReview(anyString(), any(ReviewRating.class));
    }

    @Test
    public void testAddReview_failure() throws Exception {
        when(reviewService.addReview(anyString(), any(ReviewRating.class))).thenThrow(new ReviewAlreadyExistsException());


        mockMvc.perform(post("/api/v1/review/add/{borrowerId}", "borrower1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reviewRating)))
                .andExpect(status().isNotFound());

        verify(reviewService, times(1)).addReview(anyString(), any(ReviewRating.class));
    }

    @Test
    public void testGetReviewAndRating_success() throws Exception {
        when(reviewService.getAllRatingAndReview(anyString())).thenReturn(Arrays.asList(reviewRating));

        mockMvc.perform(get("/api/v1/review/get/{borrowerId}", "borrower1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].lenderId").value("lender1"))
                .andExpect(jsonPath("$.[0].review").value("Great experience!"))
                .andExpect(jsonPath("$.[0].stars").value(5));

        verify(reviewService, times(1)).getAllRatingAndReview(anyString());
    }

    @Test
    public void testGetAverageRating_success() throws Exception {
        when(reviewService.averageRating(anyString())).thenReturn((int) 4.0);

        mockMvc.perform(get("/api/v1/review/getAverage/{id}", "borrower1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4.0));

        verify(reviewService, times(1)).averageRating(anyString());
    }


    @Test
    public void testGetReviewAndRating_failure() throws Exception {
        when(reviewService.addReview(anyString(), any(ReviewRating.class))).thenThrow(new ReviewAlreadyExistsException());

        mockMvc.perform(get("/api/v1/review/get/{borrowerId}", "nonExistentBorrowerId"))
                .andExpect(status().isOk());

        verify(reviewService, times(1)).getAllRatingAndReview(anyString());
    }

    @Test
    public void testGetAverageRating_failure() throws Exception {
        when(reviewService.addReview(anyString(), any(ReviewRating.class))).thenThrow(new ReviewAlreadyExistsException());

        mockMvc.perform(get("/api/v1/review/getAverage/{id}", "nonExistentBorrowerId"))
                .andExpect(status().isOk());

        verify(reviewService, times(1)).averageRating(anyString());
    }
}


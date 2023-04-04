/*
 * Author : Naveen Kumar
 * Date : 31-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.example.review.service;

import com.example.review.exception.ReviewAlreadyExistsException;
import com.example.review.model.Review;
import com.example.review.model.ReviewRating;
import com.example.review.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    ReviewRepository reviewRepository;


    public String addBorrower(Review review)
    {
        reviewRepository.save(review);
        return  "done";

    }

    public String addReview(String borrowerId, ReviewRating reviewRating) throws ReviewAlreadyExistsException {
        Review review=new Review();
        String Review="";
        if(!reviewRepository.findById(borrowerId).isPresent())

        {
            review.setBorrowerId(borrowerId);

            addBorrower(review);

            Review review1=reviewRepository.findById(borrowerId).get();
            List<ReviewRating> reviewRatingList=review1.getReviewRatingList();
            boolean flag=false;
            for(ReviewRating reviewRating1:reviewRatingList)
            {
                if(reviewRating1.getLenderId().equals(reviewRating.getLenderId()))
                    flag=true;
            }
            if(flag)
            {
                Review= "lender has already given review";
                throw new ReviewAlreadyExistsException();

            }
            else {
                reviewRatingList.add(reviewRating);
                review1.setReviewRatingList(reviewRatingList);
                reviewRepository.save(review1);
                Review="review added";
            }

        }

        else {
            Review review2=reviewRepository.findById(borrowerId).get();
            System.out.println( review2.getBorrowerId());
            List<ReviewRating> reviewRatingList1=review2.getReviewRatingList();
            boolean valid=false;

            for(ReviewRating reviewRating2:reviewRatingList1)
            {

                if(reviewRating2.getLenderId().equals(reviewRating.getLenderId()))
                    valid =true;
            }
            if(valid)
            {
                throw new ReviewAlreadyExistsException();
            }
            else {
                reviewRatingList1.add(reviewRating);
                review2.getReviewRatingList().add(reviewRating);
                reviewRepository.save(review2);
                Review="review added";
            }
        }



        return  Review;


    }


    public List<ReviewRating> getAllRatingAndReview(String borrowerId)
    {
        Review review=reviewRepository.findById(borrowerId).get();
        List<ReviewRating> reviewRatingList=review.getReviewRatingList();

        return  reviewRatingList;

    }


    public int averageRating(String borrowerId)
    {
        int sum = 0, avg=0;
        Review review=reviewRepository.findById(borrowerId).get();
        List<ReviewRating> reviewRatingList=review.getReviewRatingList();

        for(int i = 0; i < reviewRatingList.size(); i++)
        {
            sum = sum + reviewRatingList.get(i).getStars();

        }


        avg = sum / reviewRatingList.size();

        return avg;
    }


}

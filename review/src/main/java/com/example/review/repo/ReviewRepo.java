/*
 * Author : Naveen Kumar
 * Date : 31-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.example.review.repo;

import com.example.review.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends MongoRepository<Review,Long> {
    List<Review> findByBorrowerEmailId(String borrowerEmailId);



}

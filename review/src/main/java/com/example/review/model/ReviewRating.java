package com.example.review.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Document
public class ReviewRating {

    private String lenderId;
    private String review;
    private int stars;


}

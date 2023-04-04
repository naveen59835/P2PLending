

package com.example.review.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review {

    @Id
    private String borrowerId;
    private List<ReviewRating> reviewRatingList=new ArrayList<>();
}

/*
 * Author : Naveen Kumar
 * Date : 31-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.example.review.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewAndRating {

    private String lenderEmailId;
    private String review;
    private int stars;
}

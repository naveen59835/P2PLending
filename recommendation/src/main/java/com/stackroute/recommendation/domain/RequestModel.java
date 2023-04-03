package com.stackroute.recommendation.domain;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter

public class RequestModel {

    private String name;
    private int cibilScore;
    private String borrowerId;
}

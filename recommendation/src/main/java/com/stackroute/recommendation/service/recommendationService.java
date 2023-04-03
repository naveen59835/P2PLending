package com.stackroute.recommendation.service;

import com.stackroute.recommendation.domain.RecommendedBorrower;
import com.stackroute.recommendation.domain.RequestModel;

import java.util.List;

public interface recommendationService {
    public String SavePerson(RequestModel requestModel);

    public List<RecommendedBorrower> getAllBorrower(String score);




}

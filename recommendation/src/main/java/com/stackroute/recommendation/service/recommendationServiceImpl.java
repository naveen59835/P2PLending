package com.stackroute.recommendation.service;

import com.stackroute.recommendation.domain.RecommendedBorrower;
import com.stackroute.recommendation.domain.CibilScore;
import com.stackroute.recommendation.domain.RequestModel;
import com.stackroute.recommendation.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class recommendationServiceImpl implements recommendationService{

    @Autowired
    RecommendationRepository recommendationRepository;

    @Override
    public String SavePerson(RequestModel requestModel) {
        int lower = (requestModel.getCibilScore()/ 100) * 100 + 1;  // Calculate lower bound
        int upper = lower + 99;
        String cibilscore=lower+"-"+upper;
        CibilScore cibilScore1=new CibilScore();
        cibilScore1.setScore(cibilscore);
        RecommendedBorrower borrower=new RecommendedBorrower();
        borrower.setCibilScore(cibilScore1);
        borrower.setCreditScore(requestModel.getCibilScore());
        borrower.setName(requestModel.getName());
        borrower.setBorrowerId(requestModel.getBorrowerId());
       recommendationRepository.save(borrower);

       return "done";
    }

    @Override
    public List<RecommendedBorrower> getAllBorrower(String score) {
        return recommendationRepository.getAllBorrowers(score);
    }


}











  //  MATCH(c:cibil {score:'601-700'})<-[:APPLIED]-(b:Borrower) return b
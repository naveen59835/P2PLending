package com.stackroute.recommendation.service;

import com.stackroute.recommendation.configuration.LoanDTO;
import com.stackroute.recommendation.domain.RecommendedBorrower;
import com.stackroute.recommendation.domain.CibilScore;
import com.stackroute.recommendation.domain.RequestModel;
import com.stackroute.recommendation.proxy.BorrowerDetailsProxy;
import com.stackroute.recommendation.repository.RecommendationRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class recommendationServiceImpl implements recommendationService{

    @Autowired
    RecommendationRepository recommendationRepository;
    @Autowired
    BorrowerDetailsProxy borrowerDetailsProxy;

    @RabbitListener(queues = "recommendation-queue")
    public void SavePerson(JSONObject jsonObject) {
        LoanDTO loan = new LoanDTO(jsonObject);
        Map<Object,Object> borrowerData = borrowerDetailsProxy.getBorrowerData(loan.getBorrowerId());
        double lower = (double)borrowerData.get("cibilScore");
        int upper = (int)lower + 100;
        String cibilscore=(int)lower+"-"+upper;
        CibilScore cibilScore1=new CibilScore();
        cibilScore1.setScore(cibilscore);
        RecommendedBorrower borrower=new RecommendedBorrower();
        borrower.setCibilScore(cibilScore1);
        borrower.setCreditScore((int)lower);
        borrower.setId(Long.parseLong(loan.getLoanId()));
        borrower.setBorrowerId(loan.getBorrowerId());
        borrower.setAmount(loan.getAmount());
       recommendationRepository.save(borrower);
    }

    @Override
    public String SavePerson(RequestModel requestModel) {
        return null;
    }

    @Override
    public List<RecommendedBorrower> getAllBorrower(String score) {
        return recommendationRepository.getAllBorrowers(score);
    }
}












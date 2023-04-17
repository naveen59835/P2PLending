package com.stackroute.recommendation;

import com.stackroute.recommendation.domain.CibilScore;
import com.stackroute.recommendation.domain.RecommendedBorrower;
import com.stackroute.recommendation.repository.RecommendationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataNeo4jTest
public class repositoryTest {


    @Autowired
    RecommendationRepository recommendationRepository;

    private RecommendedBorrower recommendedBorrower;
    private CibilScore cibilScore;



    @BeforeEach
    void Before() {
        cibilScore=new CibilScore("600-700");

        recommendedBorrower=new RecommendedBorrower("borrower",cibilScore,657,"borrower@borrow.borrow",275d);
    }

    @AfterEach
    void After() {
        recommendationRepository.delete(recommendedBorrower);
      recommendedBorrower=null;
      cibilScore=null;
    }


    @Test
    @DisplayName("test case for adding borrower success")
    void addBorrowerShouldReturnAddedBorrowerSuccess()
    {
      recommendationRepository.save(recommendedBorrower);
        Optional<RecommendedBorrower> borrowerPresent= Optional.ofNullable(recommendationRepository.findByBorrowerId(recommendedBorrower.getBorrowerId()));
        assertNotNull(borrowerPresent);
        assertEquals(recommendedBorrower.getBorrowerId(),borrowerPresent.get().getBorrowerId());
    }


    @Test
    @DisplayName("test case for adding borrower failure")
    void addBorrowerShouldReturnAddedBorrowerFailure()
    {
        recommendationRepository.save(recommendedBorrower);
        Optional<RecommendedBorrower> borrowerPresent= Optional.ofNullable(recommendationRepository.findByBorrowerId(recommendedBorrower.getBorrowerId()));
        assertNotEquals("borrower",borrowerPresent.get().getBorrowerId());
    }



    @Test
    @DisplayName("test case for fetching all recommended borrower success")
    void getAllRecommendedBorrowerSuccess()
    {

        recommendationRepository.save(recommendedBorrower);
        List<RecommendedBorrower> borrowerList=recommendationRepository.getAllBorrowers("600-700");

        boolean present=false;
        for(int i=0;i<borrowerList.size();i++)
        {
                if(recommendedBorrower.getCreditScore()==borrowerList.get(i).getCreditScore()){
                    present=true;
                    break;
                }
        }


        assertEquals(recommendationRepository.getAllBorrowers("600-700").size(),borrowerList.size());
        assertEquals(true,present);
    }

    @Test
    @DisplayName("test case for fetching all recommended borrower failure")
    void getAllRecommendedBorrowerFailure()
    {

        recommendationRepository.save(recommendedBorrower);
        List<RecommendedBorrower> borrowerList=recommendationRepository.getAllBorrowers("600-700");

        boolean present=false;
        for(int i=0;i<borrowerList.size();i++)
        {
            if(recommendedBorrower.getCreditScore()==borrowerList.get(i).getCreditScore()){
                present=true;
                break;
            }
        }


        assertNotEquals(false,present);
    }

    @Test
    @DisplayName("test case for delete borrower success")
    void deleteBorrowerSuccess()
    {

        recommendationRepository.save(recommendedBorrower);
        recommendationRepository.delete(recommendedBorrower);
        boolean flag=false;
        RecommendedBorrower valid=recommendationRepository.findByBorrowerId(recommendedBorrower.getBorrowerId());
       if(valid==null)
       {
           flag=true;

       }

        assertEquals(true,flag);
    }

    @Test
    @DisplayName("test case for delete borrower failure")
    void deleteBorrowerFailure()
    {

        recommendationRepository.save(recommendedBorrower);
        recommendationRepository.delete(recommendedBorrower);
        boolean flag=false;
        RecommendedBorrower valid=recommendationRepository.findByBorrowerId(recommendedBorrower.getBorrowerId());
        if(valid==null)
        {
            flag=true;

        }

        assertNotEquals(false,flag);
    }








}

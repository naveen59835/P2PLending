package com.stackroute.recommendation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.recommendation.controller.RecommendationController;
import com.stackroute.recommendation.domain.CibilScore;
import com.stackroute.recommendation.domain.RecommendedBorrower;
import com.stackroute.recommendation.service.recommendationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class controllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Mock
    recommendationServiceImpl recommendationService;


    @InjectMocks
    RecommendationController recommendationController;

    private RecommendedBorrower recommendedBorrower;
    private CibilScore cibilScore;

    List<RecommendedBorrower> recommendedBorrowers;

    @BeforeEach
    void Before() {
        cibilScore=new CibilScore("600-700");
        recommendedBorrower=new RecommendedBorrower("borrower",cibilScore,657,"borrower@borrow.borrow",275d);
       recommendedBorrowers= Arrays.asList(recommendedBorrower);
        mockMvc = MockMvcBuilders.standaloneSetup(recommendationController).build();
    }

    @AfterEach
    void After() {

        recommendedBorrower=null;
        cibilScore=null;
        recommendedBorrowers=null;
    }



    @Test
    @DisplayName("test case to get list of recommended borrower success")
    void getRecommendedBorrowerSuccess() throws Exception {
        when(recommendationService.getAllBorrower(anyString())).thenReturn(recommendedBorrowers);
        mockMvc.perform(get("/api/v1/recommendation/get/600-700")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(recommendedBorrower)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(recommendationService,times(1)).getAllBorrower(anyString());
    }






    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }

}

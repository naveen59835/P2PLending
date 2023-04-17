package com.example;

import com.example.controller.LenderController;
import com.example.domain.Address;
import com.example.domain.Lender;
import com.example.exception.LenderAlreadyExistException;
import com.example.exception.LenderNotFoundException;
import com.example.service.LenderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
public class controllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Mock
    LenderServiceImpl lenderService;

    @InjectMocks
    LenderController lenderController;


    private Lender lender;
    private Address address;

    @BeforeEach
    void Before() {
        byte[] aadharArray = new byte[0];
        byte[] panArray = new byte[0];
        address = new Address("lender address", "lender city", "7578787", "lender state");
        lender = new Lender("lender@lender.com", "lender", "lender", "7567567956", "lender", "lender", address, "LENDER", "LENDER", aadharArray, panArray, 2756d, 16d, 2, "657", 2756d, 2747d);
        mockMvc = MockMvcBuilders.standaloneSetup(lenderController).build();


    }

    @AfterEach
    void After() {
        lender = null;
        address = null;

    }

    @Test
    @DisplayName("test case for add lender success")
    void addLenderSuccess() throws Exception {
        when(lenderService.registerLender(any())).thenReturn(lender);
        mockMvc.perform(post("/api/v1/lender/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(lender)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(lenderService,times(1)).registerLender(any());
    }

    @Test
    @DisplayName("test case for add lender failure")
    void addLenderFailure() throws Exception {
        when(lenderService.registerLender(any())).thenThrow(LenderAlreadyExistException.class);
        mockMvc.perform(post("/api/v1/lender/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(lender)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(lenderService,times(1)).registerLender(any());
    }


    @Test
    @DisplayName("test case for get lender success")
    void getLenderSuccess() throws Exception {
        when(lenderService.getLenderById(anyString())).thenReturn(lender);
        mockMvc.perform(get("/api/v1/lender/lenderMail/lender")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(lender)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(lenderService,times(1)).getLenderById(anyString());
    }

    @Test
    @DisplayName("test case for get lender failure")
    void getLenderFailure() throws Exception {
        when(lenderService.getLenderById(anyString())).thenThrow(LenderNotFoundException.class);
        mockMvc.perform(get("/api/v1/lender/lenderMail/lender")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(lender)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(lenderService,times(1)).getLenderById(anyString());
    }


    @Test
    @DisplayName("test case for update lender success")
    void updateLenderSuccess() throws Exception {
        when(lenderService.updateLender(anyObject(),anyString())).thenReturn(lender);
        mockMvc.perform(put("/api/v1/lender/updateLender/lender")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(lender)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(lenderService,times(1)).updateLender(anyObject(),anyString());
    }

    @Test
    @DisplayName("test case for update lender failure")
    void updateLenderFailure() throws Exception {
        when(lenderService.updateLender(anyObject(),anyString())).thenThrow(LenderNotFoundException.class);
        mockMvc.perform(put("/api/v1/lender/updateLender/lender")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(lender)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(lenderService,times(1)).updateLender(anyObject(),anyString());
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

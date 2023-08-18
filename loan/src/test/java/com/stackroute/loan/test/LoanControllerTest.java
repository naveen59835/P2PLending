/*
 * Author : Naveen Kumar
 * Date : 11-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.loan.test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.loan.controller.LoanController;
import com.stackroute.loan.model.Loan;
import com.stackroute.loan.service.LoanServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoanServiceImpl loanService;

    private Loan createSampleLoan() {
        Loan loan = new Loan();
        loan.setId("1");
        loan.setAmount(5000);
        loan.setBorrowerId("borrower1");
        loan.setLenderId("lender1");
        loan.setEmi(null);
        loan.setDateOfLoan(LocalDate.now());
        loan.setTerms(12);
        loan.setApproved(false);
        loan.setExpired(false);
        loan.setRejected(false);
        return loan;
    }

    @Test
    public void testGetAllLoans_success() throws Exception {
        Loan loan = createSampleLoan();
        when(loanService.getLoans(anyString(), anyString())).thenReturn(Arrays.asList(loan));

        mockMvc.perform(post("/api/v1/loan/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("id", "1", "role", "lender"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(loan.getId()));
    }

    // Add more test cases for getAllLoans

    @Test
    public void testGetLoan_success() throws Exception {
        Loan loan = createSampleLoan();
        when(loanService.getLoan(anyString())).thenReturn(loan);

        mockMvc.perform(get("/api/v1/loan/getLoan/{loanId}", loan.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(loan.getId()));
    }

    // Add test cases for getLoan

    @Test
    public void testCreateLoan_success() throws Exception {
        Loan loan = createSampleLoan();
        when(loanService.applyLoan(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(post("/api/v1/loan/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(loan.getId()));
    }

    // Add test cases for createLoan

    @Test
    public void testGetLenderLoan_success() throws Exception {
        Loan loan = createSampleLoan();
        when(loanService.findLoansFromLender(anyString())).thenReturn(Arrays.asList(loan));

        mockMvc.perform(get("/api/v1/loan/getLenderLoan/{id}", loan.getLenderId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(loan.getId()));
    }




    @Test
    public void testCreateLoan_failure() throws Exception {
        Loan loan = createSampleLoan();
        when(loanService.applyLoan(any(Loan.class))).thenThrow(new RuntimeException("Error creating loan"));

        mockMvc.perform(post("/api/v1/loan/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loan)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testGetAllLoans_failure() throws Exception {
        when(loanService.applyLoan(any(Loan.class))).thenThrow(new RuntimeException("Error creating loan"));

        mockMvc.perform(post("/api/v1/loan/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("id", "1", "role", "lender"))))
                .andExpect(status().isOk());

        verify(loanService, times(1)).getLoans(anyString(), anyString());
    }
    @Test
    public void testGetLoan_failure() throws Exception {
        when(loanService.applyLoan(any(Loan.class))).thenThrow(new RuntimeException("Error creating loan"));

        mockMvc.perform(get("/api/v1/loan/getLoan/{loanId}", "nonExistentLoanId"))
                .andExpect(status().isOk());

        verify(loanService, times(1)).getLoan(anyString());
    }

    @Test
    public void testGetLenderLoan_failure() throws Exception {
        when(loanService.applyLoan(any(Loan.class))).thenThrow(new RuntimeException("Error creating loan"));

        mockMvc.perform(get("/api/v1/loan/getLenderLoan/{id}", "nonExistentLenderId"))
                .andExpect(status().isOk());

        verify(loanService, times(1)).findLoansFromLender(anyString());
    }





}

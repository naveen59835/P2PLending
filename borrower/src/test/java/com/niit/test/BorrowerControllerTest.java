/*
 * Author : Naveen Kumar
 * Date : 06-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.controller.BorrowerController;
import com.niit.exception.BorrowerAlreadyFoundException;
import com.niit.model.Borrower;
import com.niit.repo.BorrowerRepo;
import com.niit.service.BorrowerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.Matchers.is;
@RunWith(SpringRunner.class)
@WebMvcTest(BorrowerController.class)
public class BorrowerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BorrowerService borrowerService;

    @MockBean
    private BorrowerRepo borrowerRepo;
    @Test
    public void testSaveBorrower_Success() throws Exception {
        Borrower borrower = new Borrower();
        borrower.setFirstName("John");
        borrower.setLastName("Doe");
        borrower.setEmailId("johndoe@example.com");
        borrower.setPassword("password");
        borrower.setConfirmPassword("password");
        borrower.setPhoneNo("1234567890");
        borrower.setAmount(10000);
        borrower.setCibilScore(750);

        Mockito.when(borrowerService.saveBorrower(Mockito.any(Borrower.class)))
                .thenReturn(borrower);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(borrower);

        mockMvc.perform(post("/api/v1/borrower/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.emailId").value("johndoe@example.com"))
                .andExpect(jsonPath("$.phoneNo").value("1234567890"))
                .andExpect(jsonPath("$.amount").value(10000))
                .andExpect(jsonPath("$.cibilScore").value(750));
    }

    @Test
    public void testSaveBorrower_Failure() throws Exception {
        Borrower borrower = new Borrower();
        borrower.setFirstName("John");
        borrower.setLastName("Doe");
        borrower.setEmailId("johndoe@example.com");
        borrower.setPassword("password");
        borrower.setConfirmPassword("password");
        borrower.setPhoneNo("1234567890");
        borrower.setAmount(10000);
        borrower.setCibilScore(750);

        Mockito.when(borrowerService.saveBorrower(Mockito.any(Borrower.class)))
                .thenThrow(new BorrowerAlreadyFoundException());

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(borrower);

        mockMvc.perform(post("/api/v1/borrower/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    public void testGetAllBorrowers_Success() throws Exception {
        List<Borrower> borrowerList = Arrays.asList(new Borrower(), new Borrower(), new Borrower());
        Mockito.when(borrowerService.getBorrowerList()).thenReturn(borrowerList);

        mockMvc.perform(get("/api/v1/borrower/borrowers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }




    @Test
    public void testDeleteBorrower_Failure() throws Exception {
        // Mocking the service method to throw an exception
        Mockito.doThrow(new RuntimeException("Failed to delete")).when(borrowerService).deleteBorrower("johndoe@example.com");


        mockMvc.perform(delete("/api/v1/borrower/borrower/johndoe@example.com"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$").value("Failed to delete"));
    }
    @Test
    public void testDeleteBorrowerSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/borrower/borrower/johndoe@example.com"))
                .andExpect(status().isOk());

        Mockito.verify(borrowerService, Mockito.times(1)).deleteBorrower("johndoe@example.com");
    }


    @Test
    public void testGetBorrowerByEmailId_Success() throws Exception {
        Borrower borrower = new Borrower();
        borrower.setEmailId("johndoe@example.com");

        Mockito.when(borrowerService.getBorrowerByEmailId("johndoe@example.com")).thenReturn(borrower);

        mockMvc.perform(get("/api/v1/borrower/borrower/johndoe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailId", is("johndoe@example.com")));
    }





}

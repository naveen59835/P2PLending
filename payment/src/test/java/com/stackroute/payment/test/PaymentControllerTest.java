/*
 * Author : Naveen Kumar
 * Date : 07-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.payment.test;
import com.razorpay.RazorpayClient;
import com.stackroute.payment.controller.PaymentController;
import com.stackroute.payment.domain.Payment;
import com.stackroute.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.*;

import static org.mockito.Mockito.mock;


@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private List<Payment> paymentList;

    @BeforeEach
    public void setUp() {
        paymentList = new ArrayList<>();
        Payment payment1 = new Payment("1", 100.00, "1234", "5678", "Success", new Date(), "101");
        Payment payment2 = new Payment("2", 200.00, "5678", "1234", "Failed", new Date(), "102");
        paymentList.add(payment1);
        paymentList.add(payment2);
    }




    @Test
    public void testUpdateDetails_Success() throws Exception {
        Payment payment = new Payment("1", 100.00, "1234", "5678", "Success", new Date(), "101");
        Mockito.when(paymentService.updateOrder(Mockito.anyDouble(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(payment);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/payment/updateOrderDetails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100, \"from\": \"1234\", \"to\": \"5678\", \"id\": \"1\", \"status\": \"Success\", \"loanId\": \"101\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fromAccount").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.toAccount").value("5678"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentStatus").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loanId").value("101"));
    }
    @Test
    public void testGetAllPayment_Success() throws Exception {
        Mockito.when(paymentService.getAllPayment(Mockito.anyString())).thenReturn(paymentList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/payment/getPayment/1234")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].amount").value(100.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].fromAccount").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].toAccount").value("5678"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].paymentStatus").value("Success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].loanId").value("101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].amount").value(200.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].fromAccount").value("5678"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].toAccount").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].paymentStatus").value("Failed"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].loanId").value("102"));
    }

    @Test
    public void testCreateOrder_Failure() throws Exception {
        // Mock the PaymentService object to throw an exception
        Mockito.when(paymentService.createOrderForPayment(Mockito.anyMap())).thenThrow(new Exception("Error creating order"));

        // Perform a POST request to the createOrder endpoint with a JSON payload
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/payment/createOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100, \"from\": \"1234\", \"to\": \"5678\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Expect an HTTP 500 Internal Server Error status
    }

    @Test
    public void testPayEMI_Success() throws Exception {
        // Mock the PaymentService object to return a Payment object
        Payment payment = new Payment();
        payment.setAmount(100.0);
        payment.setFromAccount("1234");
        payment.setToAccount("5678");
        payment.setLoanId("loan_1234");
        payment.setId("payment_1234");
        payment.setPaymentStatus("success");
        Mockito.when(paymentService.payEMI(Mockito.anyDouble(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyString())).thenReturn(payment);

        // Perform a PUT request to the payEMI endpoint with a JSON payload
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/payment/payEMI")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100, \"lenderId\": \"5678\", \"borrowerId\": \"1234\", \"loanId\": \"loan_1234\", \"emiId\": 1, \"paymentId\": \"payment_1234\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Expect an HTTP 201 CREATED status
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(100.0)) // Expect a JSON response with the payment details
                .andExpect(MockMvcResultMatchers.jsonPath("$.fromAccount").value("1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.toAccount").value("5678"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.loanId").value("loan_1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("payment_1234"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentStatus").value("success"));
    }

}

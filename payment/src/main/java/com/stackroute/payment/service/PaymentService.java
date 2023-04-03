package com.stackroute.payment.service;

import com.stackroute.payment.domain.Payment;

import java.util.Map;

public interface PaymentService {

    public String createOrderForPayment(Map<String,Object> data)throws Exception;
    public Payment updateOrder(double amount, String from, String to, String Id,String paymentStatus);
}



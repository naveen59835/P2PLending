package com.stackroute.payment.controller;

import com.stackroute.payment.Repository.PaymentRepository;
import com.stackroute.payment.domain.Payment;
import com.stackroute.payment.service.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.MarshalledObject;
import java.security.Principal;
import java.util.Date;
import java.util.Map;

import com.razorpay.*;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/api/v1/payment")

public class PaymentController {


    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;

    }

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody Map<String, Object> data) throws Exception{






    double amount=Double.parseDouble(data.get("amount").toString());
      String toAccount=  data.get("to").toString();
      String fromAccount=data.get("from").toString();

        var client = new RazorpayClient("rzp_test_J2dEVfdieEnRme", "nRmnRmsVfdieEnRmEn");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", amount * 100);
        jsonObject.put("currency", "INR");
        Order order = client.orders.create(jsonObject);

        return order.toString();
    }


    @PutMapping("/updateOrderDetails")
    public ResponseEntity<?> updateDetails(@RequestBody Map<String, Object> data)
    {

        double amount=Double.parseDouble(data.get("amount").toString());
        String toAccount=  data.get("to").toString();
        String fromAccount=data.get("from").toString();
        String orderId=data.get("id").toString();
        String paymentStatus=data.get("status").toString();

        return new ResponseEntity<>(paymentService.updateOrder(amount,fromAccount,toAccount,orderId,paymentStatus),HttpStatus.CREATED);

    }



























}

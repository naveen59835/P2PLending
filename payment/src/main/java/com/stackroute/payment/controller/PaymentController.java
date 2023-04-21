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

        var client = new RazorpayClient("rzp_test_oKaXFoOdFGcLX5", "2Upe0FPcysd5vh0rwrIUkuIB");

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
        String loanId=data.get("loanId").toString();

        return new ResponseEntity<>(paymentService.updateOrder(amount,fromAccount,toAccount,orderId,paymentStatus,loanId),HttpStatus.CREATED);

    }
    @PutMapping("/payEMI")
    public ResponseEntity<?> payEMI(@RequestBody Map<String, Object> paymentData)
    {
        int amount= (int)paymentData.get("amount");
        String lenderId=  paymentData.get("lenderId").toString();
        String borrowerId=paymentData.get("borrowerId").toString();
        String loanId=paymentData.get("loanId").toString();
        int emiId=(int)paymentData.get("emiId");
        String orderId=paymentData.get("paymentId").toString();
        return new ResponseEntity<>(paymentService.payEMI(amount,lenderId,borrowerId,orderId,emiId,loanId),HttpStatus.CREATED);
    }
    @GetMapping("/getPayment/{id}")
    ResponseEntity<?> getAllPayment(@PathVariable String id ){
        return new ResponseEntity<>(paymentService.getAllPayment(id),HttpStatus.OK);
    }
    @GetMapping("/getAllPayments")
    ResponseEntity<?> getAllPayments(){
        return new ResponseEntity<>(paymentService.getAllPayments(),HttpStatus.OK);
    }
}

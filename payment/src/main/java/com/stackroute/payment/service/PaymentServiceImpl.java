package com.stackroute.payment.service;

import com.stackroute.payment.Repository.PaymentRepository;
import com.stackroute.payment.domain.Payment;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentRepository paymentRepository;


    @Override
    public String createOrderForPayment(Map<String, Object> data)throws Exception {



        int amount = Integer.parseInt(data.get("amount").toString());
        String fromAccount=  data.get("from").toString();
        System.out.println(fromAccount);
        System.out.println(amount);

        return "done";

    }


    public Payment updateOrder(double amount,String from,String to,String Id,String status)
    {
        Payment payment=new Payment();


        if(status=="success") {
            payment.setAmount(amount);
            payment.setId(Id);
            payment.setToAccount(to);
            payment.setFromAccount(from);
            payment.setPaymentStatus(status);
            payment.setPaymentDate(new Date());

        }
        else if (status=="fail"){
            payment.setAmount(amount);
            payment.setId(Id);
            payment.setToAccount(to);
            payment.setFromAccount(from);
            payment.setPaymentDate(new Date());
            payment.setPaymentStatus(status);

        }
        else {
            payment.setAmount(amount);
            payment.setId(Id);
            payment.setToAccount(to);
            payment.setFromAccount(from);
            payment.setPaymentDate(new Date());
            payment.setPaymentStatus(status);

        }


        return    paymentRepository.save(payment);


    }


}

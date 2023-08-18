package com.stackroute.payment.service;

import com.stackroute.payment.Repository.PaymentRepository;
import com.stackroute.payment.controller.config.EmiDTO;
import com.stackroute.payment.controller.config.PaymentDTO;
import com.stackroute.payment.domain.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    RabbitTemplate template;

    @Override
    public String createOrderForPayment(Map<String, Object> data)throws Exception {
        int amount = Integer.parseInt(data.get("amount").toString());
        String fromAccount=  data.get("from").toString();
        return "done";
    }

    public Payment updateOrder(double amount,String from,String to,String Id,String status, String loanId)
    {
        Payment payment=new Payment();
        if(status.equals("success")) {
            payment.setAmount(amount);
            payment.setId(Id);
            payment.setToAccount(to);
            payment.setFromAccount(from);
            payment.setPaymentStatus(status);
            payment.setPaymentDate(new Date());
            payment.setLoanId(loanId);
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setJsonObject(payment);
            template.convertAndSend("loan-approval-exchange","route-key",paymentDTO.getJsonObject());
            template.convertAndSend("loan-approval-notification-exchange","route-key",paymentDTO.getJsonObject());
            template.convertAndSend("loan-approval-recommendation-exchange","route-key", Map.of("id",loanId));
        }
        else if (status.equals("fail")){
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
    public Object payEMI(double amount,String lenderId,String borrowerId,String orderId,int emiId,String loanId){
        Payment payment = new Payment();
        payment.setLoanId(loanId);
        payment.setPaymentDate(new Date());
        payment.setPaymentStatus("success");
        payment.setFromAccount(borrowerId);
        payment.setToAccount(lenderId);
        payment.setAmount(amount);
        payment.setId(orderId);
        EmiDTO emiDTO = new EmiDTO();
        emiDTO.setJsonObject(orderId,loanId,emiId);
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setJsonObject(payment);
        template.convertAndSend("pay-emi-exchange","route-key",emiDTO.getJsonObject());
        template.convertAndSend("pay-emi-notification-exchange","route-key",paymentDTO.getJsonObject() );
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayment(String id) {
        List<Payment> allPayments = paymentRepository.getPaymentsByFromAccountOrToAccount(id,id);
        return  allPayments;
    }
    @Override
    public Map<String, Double> getAllPayments() {
        List<Payment> allPayments = paymentRepository.findAll();
        double totalMoney = 0;
        for (Payment payment : allPayments) {
            totalMoney+=payment.getAmount();
        }
        return Map.of("total",totalMoney);
    }

}

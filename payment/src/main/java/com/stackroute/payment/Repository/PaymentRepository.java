package com.stackroute.payment.Repository;

import com.stackroute.payment.domain.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment,String> {
    List<Payment> getPaymentsByFromAccountOrToAccount(String id,String id2);
}

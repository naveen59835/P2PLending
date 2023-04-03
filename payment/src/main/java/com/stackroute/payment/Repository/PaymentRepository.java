package com.stackroute.payment.Repository;

import com.stackroute.payment.domain.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment,String> {
}

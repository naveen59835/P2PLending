/*
 * Author : Naveen Kumar
 * Date : 07-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.payment.test;
import com.stackroute.payment.domain.Payment;
import com.stackroute.payment.Repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;


@DataMongoTest
public class PaymentRepoTest {
    @Autowired
    private PaymentRepository paymentRepository;

    @BeforeEach
    public void setUp() {
        Payment payment1 = new Payment("1", 100.00, "1234", "5678", "Success", new Date(), "101");
        Payment payment2 = new Payment("2", 200.00, "5678", "1234", "Failed", new Date(), "102");
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
    }

    @Test
    public void testGetPaymentsByFromAccountOrToAccount_Success() {
        List<Payment> payments = paymentRepository.getPaymentsByFromAccountOrToAccount("1234", "5678");
        Assertions.assertNotNull(payments);
        Assertions.assertEquals(1, payments.size());
    }

    @Test
    public void testGetPaymentsByFromAccountOrToAccount_Failure() {
        List<Payment> payments = paymentRepository.getPaymentsByFromAccountOrToAccount("9999", "8888");
        Assertions.assertNotNull(payments);
        Assertions.assertEquals(0, payments.size());
    }
}

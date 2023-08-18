/*
 * Author : Naveen Kumar
 * Date : 06-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.test;

import com.niit.model.Borrower;
import com.niit.repo.BorrowerRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
@DataMongoTest
public class BorrowerRepoTest {

    @Autowired
    private BorrowerRepo borrowerRepo;

    @Test
    public void testFindByEmailIdSuccess() {
        Borrower borrower = new Borrower();
        borrower.setEmailId("test@example.com");
        borrower.setFirstName("John");
        borrower.setLastName("Doe");
        borrowerRepo.save(borrower);

        Borrower result = borrowerRepo.findByEmailId("test@example.com");

        Assertions.assertEquals("test@example.com", result.getEmailId());
        Assertions.assertEquals("John", result.getFirstName());
        Assertions.assertEquals("Doe", result.getLastName());
    }

    @Test
    public void testFindByEmailIdFailure() {
        Borrower result = borrowerRepo.findByEmailId("nonexistent@example.com");

        Assertions.assertNull(result);
    }
}

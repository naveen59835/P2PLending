/*
 * Author : Naveen Kumar
 * Date : 11-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.loan.test;
import com.stackroute.loan.model.Loan;
import com.stackroute.loan.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class LoanRepoTest {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void testFindLoansByBorrowerId_success() {
        // Create sample loans and save to repository
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        loan1.setId("1");
        loan1.setAmount(5000);
        loan1.setBorrowerId("borrower1");
        loan1.setLenderId("lender1");
        loan1.setEmi(null);
        loan1.setDateOfLoan(LocalDate.now());
        loan1.setTerms(12);
        loan1.setApproved(false);
        loan1.setExpired(false);
        loan1.setRejected(false);

        loan2.setId("2");
        loan2.setAmount(10000);
        loan2.setBorrowerId("borrower2");
        loan2.setLenderId("lender2");
        loan2.setEmi(null);
        loan2.setDateOfLoan(LocalDate.now());
        loan2.setTerms(24);
        loan2.setApproved(false);
        loan2.setExpired(false);
        loan2.setRejected(false);

        loanRepository.save(loan1);
        loanRepository.save(loan2);

        // Test findLoansByBorrowerId
        String borrowerId = "borrower1";
        List<Loan> loans = loanRepository.findLoansByBorrowerId(borrowerId);
        assertThat(loans).isNotEmpty();
        assertThat(loans.size()).isEqualTo(1);
        assertThat(loans.get(0).getBorrowerId()).isEqualTo(borrowerId);
    }

    @Test
    public void testFindLoansByBorrowerId_failure() {
        // Test findLoansByBorrowerId with non-existing borrowerId
        String nonExistingBorrowerId = "borrower3";
        List<Loan> loans = loanRepository.findLoansByBorrowerId(nonExistingBorrowerId);
        assertThat(loans).isEmpty();
    }

    @Test
    public void testFindLoansByLenderId_success() {
        // Create sample loans and save to repository
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        loan1.setId("1");
        loan1.setAmount(5000);
        loan1.setBorrowerId("borrower1");
        loan1.setLenderId("lender1");
        loan1.setEmi(null);
        loan1.setDateOfLoan(LocalDate.now());
        loan1.setTerms(12);
        loan1.setApproved(false);
        loan1.setExpired(false);
        loan1.setRejected(false);

        loan2.setId("2");
        loan2.setAmount(10000);
        loan2.setBorrowerId("borrower2");
        loan2.setLenderId("lender2");
        loan2.setEmi(null);
        loan2.setDateOfLoan(LocalDate.now());
        loan2.setTerms(24);
        loan2.setApproved(false);
        loan2.setExpired(false);
        loan2.setRejected(false);
        loanRepository.save(loan1);
        loanRepository.save(loan2);

        // Test findLoansByLenderId
        String lenderId = "lender1";
        List<Loan> loans = loanRepository.findLoansByLenderId(lenderId);
        assertThat(loans).isNotEmpty();
        assertThat(loans.size()).isEqualTo(1);
        assertThat(loans.get(0).getLenderId()).isEqualTo(lenderId);
    }

    @Test
    public void testFindLoansByLenderId_failure() {
        // Test findLoansByLenderId with non-existing lenderId
        String nonExistingLenderId = "lender3";
        List<Loan> loans = loanRepository.findLoansByLenderId(nonExistingLenderId);
        assertThat(loans).isEmpty();
    }
}

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
        Loan loan1 = new Loan("1", 5000, "lender1", "borrower1", 5, false, null, false, false, 12, LocalDate.now());
        Loan loan2 = new Loan("2", 10000, "lender2", "borrower2", 7, true, null, false, false, 24, LocalDate.now());
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
        Loan loan1 = new Loan("1", 5000, "lender1", "borrower1", 5, false, null, false, false, 12, LocalDate.now());
        Loan loan2 = new Loan("2", 10000, "lender2", "borrower2", 7, true, null, false, false, 24, LocalDate.now());
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

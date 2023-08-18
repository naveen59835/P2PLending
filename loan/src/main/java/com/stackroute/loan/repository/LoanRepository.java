package com.stackroute.loan.repository;

import com.stackroute.loan.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LoanRepository extends MongoRepository<Loan,String> {
    public List<Loan> findLoansByBorrowerId(String borrowerId);
    public List<Loan> findLoansByLenderId(String lenderId);
}

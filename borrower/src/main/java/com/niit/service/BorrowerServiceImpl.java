/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.model.Borrower;
import com.niit.repo.BorrowerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerServiceImpl implements BorrowerService{
    @Autowired
    BorrowerRepo borrowerRepo;
    @Override
    public Borrower saveBorrower(Borrower borrower) {
        return borrowerRepo.save(borrower);
    }

    @Override
    public List<Borrower> getBorrowerList() {
        return borrowerRepo.findAll();
    }

    public Borrower getBorrowerByEmailId(String emailId) {
        return borrowerRepo.findByEmailId(emailId);
    }
}

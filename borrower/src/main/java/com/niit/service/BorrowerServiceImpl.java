/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.exception.BorrowerAlreadyFoundException;
import com.niit.model.Borrower;
import com.niit.repo.BorrowerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerServiceImpl implements BorrowerService {
    @Autowired
    BorrowerRepo borrowerRepo;

    @Override
    public Borrower saveBorrower(Borrower borrower) throws BorrowerAlreadyFoundException {
        try {
            return borrowerRepo.save(borrower);
        } catch (Exception e) {
            throw new BorrowerAlreadyFoundException();
        }
    }

    @Override
    public List<Borrower> getBorrowerList() {
        return borrowerRepo.findAll();
    }

    public Borrower getBorrowerByEmailId(String emailId) {
        return borrowerRepo.findByEmailId(emailId);
    }

    @Override
    public Borrower updateBorrower(Borrower borrower, String emailId) {
        if (borrowerRepo.findById(emailId).isPresent()) {
            Borrower borrower1 = borrowerRepo.findById(emailId).get();
            if (borrower1.getFirstName() != null) {
                borrower1.setFirstName(borrower.getFirstName());
            }
            if (borrower1.getLastName() != null) {
                borrower1.setLastName(borrower.getLastName());
            }
            if (borrower1.getEmailId() != null) {
                borrower1.setEmailId(borrower.getEmailId());
            }
            if (borrower1.getPhoneNo() != null) {
                borrower1.setPhoneNo(borrower.getPhoneNo());
            }
            return borrowerRepo.save(borrower1);
        }
        return null;
    }

    @Override
    public boolean deleteBorrower(String emailId) {
        if (borrowerRepo.findById(emailId).isPresent()) {
            Borrower borrower2 = borrowerRepo.findById(emailId).get();
            borrowerRepo.delete(borrower2);
            return true;
        }
        return false;
    }
}

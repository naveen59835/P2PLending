/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.configuration.BorrowerDTO;
import com.niit.exception.BorrowerAlreadyFoundException;
import com.niit.model.Borrower;
import com.niit.repo.BorrowerRepo;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerServiceImpl implements BorrowerService {
    @Autowired
    BorrowerRepo borrowerRepo;
    @Autowired
    RabbitTemplate template;

    @Override
    public Borrower saveBorrower(Borrower borrower) throws BorrowerAlreadyFoundException {
        try {
            BorrowerDTO borrowerDTO = new BorrowerDTO();
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",borrower.getEmailId());
            jsonObject.put("password",borrower.getPassword());
            jsonObject.put("name",borrower.getFirstName()+" "+borrower.getLastName());
            jsonObject.put("role","borrower");
            borrowerDTO.setJsonObject(jsonObject);
            template.convertAndSend("auth-exchange","route-key", borrowerDTO.getJsonObject());
            return borrowerRepo.save(borrower);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
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
            Borrower borrowerData = borrowerRepo.findById(emailId).get();
            if (borrowerData.getFirstName() != null) {
                borrowerData.setFirstName(borrower.getFirstName());
            }
            if (borrowerData.getLastName() != null) {
                borrowerData.setLastName(borrower.getLastName());
            }
            if (borrowerData.getPhoneNo() != null) {
                borrowerData.setPhoneNo(borrower.getPhoneNo());
            }
            if (borrowerData.getPassword() != null) {
                borrowerData.setPassword(borrower.getPassword());
            }
            if (borrowerData.getConfirmPassword() != null) {
                borrowerData.setConfirmPassword(borrower.getConfirmPassword());
            }
            if(borrowerData.getAadharImage() != null){
                borrowerData.setAadharImage(borrower.getAadharImage());
            }
            return borrowerRepo.save(borrowerData);
        }
        return null;
    }

    @Override
    public boolean deleteBorrower(String emailId) {
        if (borrowerRepo.findById(emailId).isPresent()) {
            Borrower borrowerDelete = borrowerRepo.findById(emailId).get();
            borrowerRepo.delete(borrowerDelete);
            return true;
        }
        return false;
    }
}

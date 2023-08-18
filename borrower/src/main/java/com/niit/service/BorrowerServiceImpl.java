/*
 * Author : Naveen Kumar
 * Date : 09-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.service;

import com.niit.configuration.BorrowerDTO;
import com.niit.exception.BorrowerAlreadyFoundException;
import com.niit.model.Address;
import com.niit.model.Borrower;
import com.niit.repo.BorrowerRepo;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            template.convertAndSend("register-notification-exchange","notification-route-key",borrower.getEmailId());
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
            if (borrower.getFirstName() != null) {
                borrowerData.setFirstName(borrower.getFirstName());
            }
            if (borrower.getLastName() != null) {
                borrowerData.setLastName(borrower.getLastName());
            }
            if (borrower.getPhoneNo() != null) {
                borrowerData.setPhoneNo(borrower.getPhoneNo());
            }
            if (borrower.getPassword() != null) {
                borrowerData.setPassword(borrower.getPassword());
            }
            if (borrower.getConfirmPassword() != null) {
                borrowerData.setConfirmPassword(borrower.getConfirmPassword());
            }
            if (borrower.getAadharImage() != null && borrower.getAadharImage().length > 0) {
                borrowerData.setAadharImage(borrower.getAadharImage());
            }
            if (borrower.getAadhaarNo() != null) {
                borrowerData.setAadhaarNo(borrower.getAadhaarNo());
            }
            if (borrower.getPanNo() != null) {
                borrowerData.setPanNo(borrower.getPanNo());
            }
            if(borrower.getCibilScore()>0){
                borrowerData.setCibilScore(borrower.getCibilScore());
            }
            if (borrower.getAddress() != null) {
                Address userAddress = borrowerData.getAddress();
                Address updatedAddress = borrower.getAddress();
                if(updatedAddress.getAddress() !=null){
                    userAddress.setAddress(updatedAddress.getAddress());
                }if(updatedAddress.getCity() !=null){
                    userAddress.setCity(updatedAddress.getCity());
                }if(updatedAddress.getState() !=null){
                    userAddress.setState(updatedAddress.getState());
                }if(updatedAddress.getPin() !=null){
                    userAddress.setPin(updatedAddress.getPin());
                }
                borrowerData.setAddress(userAddress);
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
    @Override
    public Borrower saveBorrowerImage(MultipartFile borrowerImage, String emailId, String imageName) throws IOException {
        if (borrowerRepo.findById(emailId).isPresent()) {
            Borrower borrower = borrowerRepo.findById(emailId).get();
            if (imageName.equals("aadhar")) {
                borrower.setAadharImage(borrowerImage.getBytes());
                borrower.setAadharImageName(borrowerImage.getOriginalFilename());
            }
            if (imageName.equals("pan")) {
                borrower.setPanImage(borrowerImage.getBytes());
                borrower.setPanImageName(borrowerImage.getOriginalFilename());
            }
            if (imageName.equals("cibil")) {
                borrower.setCibilImage(borrowerImage.getBytes());
                borrower.setCibilImageName(borrowerImage.getOriginalFilename());
            }

            return borrowerRepo.save(borrower);
        }

        throw new IllegalStateException("Image not found");


    }
}
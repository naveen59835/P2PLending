package com.example.service;

import com.example.config.LenderDTO;
import com.example.domain.Address;
import com.example.domain.Lender;
import com.example.exception.LenderAlreadyExistException;
import com.example.exception.LenderNotFoundException;
import com.example.repository.LenderRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LenderServiceImpl implements LenderService {
    LenderRepository lenderRepository;
    RabbitTemplate rabbitTemplate;
    DirectExchange exchange;
    @Autowired
    public LenderServiceImpl(LenderRepository lenderRepository, RabbitTemplate rabbitTemplate, DirectExchange exchange) {
        this.lenderRepository = lenderRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }
    @Override
    public Lender registerLender(Lender lender) throws LenderAlreadyExistException {
        LenderDTO lenderDTO=new LenderDTO();
        if (lenderRepository.findById(lender.getEmailId()).isPresent()) {
            throw new LenderAlreadyExistException();
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("email",lender.getEmailId());
        jsonObject.put("password",lender.getPassword());
        jsonObject.put("name",lender.getFirstName()+" "+lender.getLastName());
        jsonObject.put("role","lender");
        lenderDTO.setJsonObject(jsonObject);
        rabbitTemplate.convertAndSend(exchange.getName(),"route-key",lenderDTO.getJsonObject());
        return lenderRepository.save(lender);
    }

    @Override
    public Lender getLenderById(String lenderId) throws LenderNotFoundException {

        if(!lenderRepository.findById(lenderId).isPresent())
        {
            throw new LenderNotFoundException();
        }
        return lenderRepository.findById(lenderId).get();

    }


    @Override
    public Lender updateLender(Lender lender, String emailId) throws LenderNotFoundException {

        if(!lenderRepository.findById(emailId).isPresent())
        {
            throw new LenderNotFoundException();
        }


        if(lenderRepository.findById(emailId).isPresent())
        {

            Lender existingLender=lenderRepository.findById(emailId).get();
            if (lender.getFirstName()!=null)
            {
                existingLender.setFirstName(lender.getFirstName());
                System.out.println(existingLender.getFirstName());
            }
            if (lender.getLastName()!=null)
            {
                existingLender.setLastName(lender.getLastName());
            }

            if(lender.getPhoneNumber()!=null)
            {
                existingLender.setPhoneNumber(lender.getPhoneNumber());
            }
            if(lender.getAadhaar()!=null)
            {

                existingLender.setAadhaar(lender.getAadhaar());
                System.out.println(existingLender.getAadhaar());
            }
            if(lender.getPan()!=null)
            {
                existingLender.setPan(lender.getPan());
            }
            return lenderRepository.save(existingLender);
        }
        return null;
    }



    @Override
    public Lender updateLenderAddress(Address address, String emailId) throws LenderNotFoundException {

        if (!lenderRepository.findById(emailId).isPresent()) {
            throw new LenderNotFoundException();
        }

        if (lenderRepository.findById(emailId).isPresent()) {

            Lender existingLender = lenderRepository.findById(emailId).get();
            Address address1=new Address();
            if (address.getAddress()!=null)
            {
                address1.setAddress(address.getAddress());


                existingLender.getAddress().setAddress(address1.getAddress());
            }
            if (address.getCity()!=null)
            {
                address1.setCity(address.getCity());


                existingLender.getAddress().setCity(address1.getCity());

            }
            if (address.getState()!=null)
            {
                address1.setState(address.getState());


                existingLender.getAddress().setState(address1.getState());

            }
            if (address.getPin()!=null)
            {
                address1.setPin(address.getPin());

                existingLender.getAddress().setPin(address1.getPin());

            }
            return lenderRepository.save(existingLender);
        }
        return null;
    }

}

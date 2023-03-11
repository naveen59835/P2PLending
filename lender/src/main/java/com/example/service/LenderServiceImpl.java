package com.example.service;

import com.example.config.LenderDTO;
import com.example.domain.Lender;
import com.example.exception.LenderAlreadyExistException;
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
        jsonObject.put("phone",lender.getPhoneNumber());
        lenderDTO.setJsonObject(jsonObject);
        rabbitTemplate.convertAndSend(exchange.getName(),"route-key",lenderDTO.getJsonObject());
        return lenderRepository.save(lender);
    }
}

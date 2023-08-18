package com.stackroute.authentication.service;

import com.stackroute.authentication.configuration.AuthDTO;
import com.stackroute.authentication.exception.UserNotFoundException;
import com.stackroute.authentication.exception.UsernamePasswordMismatchException;
import com.stackroute.authentication.model.Login;
import com.stackroute.authentication.repository.LoginRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    LoginRepository repository;

    public AuthServiceImpl(LoginRepository repository) {
        this.repository = repository;
    }

    public Map <String,Object> loginUser(Map <String,String> loginDetails) throws UsernamePasswordMismatchException, UserNotFoundException {
        Login userDetails = repository.findByEmailAndRole(loginDetails.get("email"),loginDetails.get("role"));
        if(userDetails!=null){
            if(userDetails.getPassword().equals(loginDetails.get("password"))){
                Map<String,Object> responseData = new HashMap<>();
                responseData.put("email",userDetails.getEmail());
                responseData.put("name",userDetails.getName());
                responseData.put("role",userDetails.getRole());
                String token = Jwts.builder().setClaims(responseData).signWith(SignatureAlgorithm.HS256,"My-Secret-Key").compact();
                responseData.put("token",token);
                return responseData;
            }
            else throw new UsernamePasswordMismatchException("Incorrect password");
        }
        throw new UserNotFoundException("User not found");
    }
    @RabbitListener(queues = "auth")
    public void saveUser(JSONObject data){
        AuthDTO dto = new AuthDTO(data);
        repository.save(dto.getLogin());
    }
    public Map<String,Integer> getTotalUsers(){
        return Map.of("totalUsers",repository.findAll().size());
    }
}

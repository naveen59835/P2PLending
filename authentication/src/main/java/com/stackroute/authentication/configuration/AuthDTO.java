package com.stackroute.authentication.configuration;

import com.stackroute.authentication.model.Login;
import lombok.Data;
import org.json.simple.JSONObject;

@Data
public class AuthDTO {
    Login login;
    public AuthDTO(JSONObject object){
        login = new Login();
        login.setName(object.get("name").toString());
        login.setEmail(object.get("email").toString());
        login.setPassword(object.get("password").toString());
        login.setRole(object.get("role").toString());
    }
}

package com.stackroute.authentication.configuration;

import com.stackroute.authentication.model.Login;
import org.json.simple.JSONObject;

public class AuthDTO {
    Login login;
    public AuthDTO(JSONObject object){
        login = new Login();
        login.setName(object.get("name").toString());
        login.setEmail(object.get("email").toString());
        login.setPassword(object.get("password").toString());
        login.setRole(object.get("role").toString());
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "AuthDTO{" +
                "login=" + login +
                '}';
    }
}

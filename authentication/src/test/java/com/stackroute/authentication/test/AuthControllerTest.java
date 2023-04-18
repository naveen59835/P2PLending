/*
 * Author : Naveen Kumar
 * Date : 06-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.authentication.test;

import com.stackroute.authentication.controller.AuthController;
import com.stackroute.authentication.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.authentication.controller.AuthController;
import com.stackroute.authentication.exception.UserNotFoundException;
import com.stackroute.authentication.exception.UsernamePasswordMismatchException;
import com.stackroute.authentication.service.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthServiceImpl authService;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(authService))
                .build();
    }

    @Test
    public void testLogin_Successful() throws Exception {
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", "testuser@example.com");
        loginDetails.put("password", "password");
        loginDetails.put("role", "user");

        Map<String, Object> response = new HashMap<>();
        response.put("email", "testuser@example.com");
        response.put("name", "Test User");
        response.put("role", "user");
        response.put("token", "testtoken");

        when(authService.loginUser(anyMap())).thenReturn(response);

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/v1/authentication/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.role").value("user"))
                .andExpect(jsonPath("$.token").value("testtoken"));
    }

    @Test
    public void testLogin_Unsuccessful() throws Exception {
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", "testuser@example.com");
        loginDetails.put("password", "wrongpassword");
        loginDetails.put("role", "user");

        when(authService.loginUser(anyMap())).thenThrow(new UsernamePasswordMismatchException("Incorrect password"));

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/v1/authentication/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDetails)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Incorrect password"));
    }
}


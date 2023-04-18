/*
 * Author : Naveen Kumar
 * Date : 06-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.authentication.test;

import com.stackroute.authentication.model.Login;
import com.stackroute.authentication.repository.LoginRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthRepoTest {

    @Autowired
    private LoginRepository loginRepository;

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setEmail("user@example.com");
        login.setPassword("password123");
        login.setName("John Doe");
        login.setRole("ROLE_USER");
    }

    @AfterEach
    void tearDown() {
        loginRepository.deleteAll();
        login = null;
    }

    @Test
    @DisplayName("Test Case for saving Login object")
    public void testSaveLogin() {
        loginRepository.save(login);
        Optional<Login> retrievedLogin = loginRepository.findById(login.getId());
        assertEquals(login.getId(), retrievedLogin.get().getId());
    }

    @Test
    @DisplayName("Test Case for finding Login by email and role")
    public void testFindByEmailAndRole() {
        loginRepository.save(login);
        Login retrievedLogin = loginRepository.findByEmailAndRole(login.getEmail(), login.getRole());
        assertNotNull(retrievedLogin);
        assertEquals(login.getEmail(), retrievedLogin.getEmail());
        assertEquals(login.getRole(), retrievedLogin.getRole());
    }

    @Test
    @DisplayName("Test Case for finding Login by email and role when it does not exist")
    public void testFindByEmailAndRoleWhenLoginDoesNotExist() {
        loginRepository.save(login);
        Login retrievedLogin = loginRepository.findByEmailAndRole("nonexistent@example.com", "ROLE_ADMIN");
        assertNull(retrievedLogin);
    }

}

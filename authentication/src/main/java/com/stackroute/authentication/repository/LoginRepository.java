package com.stackroute.authentication.repository;

import com.stackroute.authentication.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login,Long> {
    public Login findByEmailAndRole(String phone,String role);
}

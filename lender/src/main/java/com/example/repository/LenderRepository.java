package com.example.repository;

import com.example.domain.Lender;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LenderRepository extends MongoRepository<Lender,String> {
}

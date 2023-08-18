package com.niit.repo;

import com.niit.model.Borrower;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepo extends MongoRepository <Borrower,String> {
    Borrower findByEmailId(String emailId);

}

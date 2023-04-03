package com.stackroute.contactus.repo;

import com.stackroute.contactus.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends MongoRepository <Contact,String> {

}

/*
 * Author : Naveen Kumar
 * Date : 13-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.notifications.repository;

import com.stackroute.notifications.model.ContactUs;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepo extends MongoRepository<ContactUs, ObjectId> {
}


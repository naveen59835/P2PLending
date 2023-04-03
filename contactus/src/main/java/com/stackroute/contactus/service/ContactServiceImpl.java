/*
 * Author : Naveen Kumar
 * Date : 29-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.contactus.service;

import com.stackroute.contactus.model.Contact;
import com.stackroute.contactus.repo.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    ContactRepo contactRepo;

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepo.save(contact);
    }
}

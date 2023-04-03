/*
 * Author : Naveen Kumar
 * Date : 29-03-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.contactus.controller;

import com.stackroute.contactus.model.Contact;
import com.stackroute.contactus.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ContactController {

    ResponseEntity responseEntity;
    @Autowired
    ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    @PostMapping("/contactus")
    public ResponseEntity<?> post(@RequestBody Contact contact){
        return new ResponseEntity <>(contactService.saveContact(contact), HttpStatus.OK);

    }
}

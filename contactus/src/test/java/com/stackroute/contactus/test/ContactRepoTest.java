/*
 * Author : Naveen Kumar
 * Date : 07-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.contactus.test;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.stackroute.contactus.model.Contact;
import com.stackroute.contactus.repo.ContactRepo;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class ContactRepoTest {
    @Autowired
    private ContactRepo contactRepo;

    @Test
    public void testSaveContact() {
        // Arrange
        Contact contact = new Contact("1", "test@example.com", "Hello, this is a test message.");

        // Act
        Contact savedContact = contactRepo.save(contact);

        // Assert
        assertNotNull(savedContact);
        assertEquals("1", savedContact.getId());
        assertEquals("test@example.com", savedContact.getEmail());
        assertEquals("Hello, this is a test message.", savedContact.getMessage());
    }


    @Test
    public void testGetContactById() {
        // Arrange
        Contact contact = new Contact("1", "test@example.com", "Hello, this is a test message.");
        contactRepo.save(contact);

        // Act
        Optional<Contact> foundContactOptional = contactRepo.findById("1");

        // Assert
        assertTrue(foundContactOptional.isPresent());
        Contact foundContact = foundContactOptional.get();
        assertEquals("test@example.com", foundContact.getEmail());
        assertEquals("Hello, this is a test message.", foundContact.getMessage());
    }

    @Test
    public void testDeleteContactById() {
        // Arrange
        Contact contact = new Contact("1", "test@example.com", "Hello, this is a test message.");
        contactRepo.save(contact);

        // Act
        contactRepo.deleteById("1");

        // Assert
        assertFalse(contactRepo.findById("1").isPresent());
    }
}

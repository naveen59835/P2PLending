/*
 * Author : Naveen Kumar
 * Date : 07-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.contactus.test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.stackroute.contactus.controller.ContactController;
import com.stackroute.contactus.model.Contact;
import com.stackroute.contactus.service.ContactService;

public class ContactControllerTest {

    @Test
    public void testPostContactSuccess() {
        // Arrange
        ContactService contactService = mock(ContactService.class);
        ContactController contactController = new ContactController(contactService);
        Contact contact = new Contact("1", "test@example.com", "Hello, this is a test message.");
        when(contactService.saveContact(any(Contact.class))).thenReturn(contact);

        // Act
        ResponseEntity<?> response = contactController.post(contact);

        // Assert
        verify(contactService, times(1)).saveContact(any(Contact.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contact, response.getBody());
    }

    @Test
    public void testPostContactFailure() {
        // Arrange
        ContactService contactService = mock(ContactService.class);
        ContactController contactController = new ContactController(contactService);
        Contact contact = new Contact("1", "test@example.com", "Hello, this is a test message.");
        when(contactService.saveContact(any(Contact.class))).thenReturn(null);

        // Act
        ResponseEntity<?> response = contactController.post(contact);

        // Assert
        verify(contactService, times(1)).saveContact(any(Contact.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }
}

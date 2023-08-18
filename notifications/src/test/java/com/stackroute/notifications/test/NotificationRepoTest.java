/*
 * Author : Naveen Kumar
 * Date : 07-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.notifications.test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.notifications.model.Notification;
import com.stackroute.notifications.model.NotificationMessage;
import com.stackroute.notifications.repository.NotificationRepository;



public class NotificationRepoTest {

    @Mock
    private NotificationRepository notificationRepository;

    private Notification testNotification;

    @BeforeEach
    public void setUp() {
        // initialize the mock NotificationRepository
        MockitoAnnotations.openMocks(this);
        testNotification = new Notification();
        NotificationMessage message = new NotificationMessage(1, "Test message", LocalDateTime.now());
        List<NotificationMessage> messages = new ArrayList<>();
        messages.add(message);
        testNotification.setMessages(messages);

        when(notificationRepository.findById(testNotification.getId())).thenReturn(Optional.of(testNotification));
    }

    @Test
    public void testFindByIdSuccess() {

        Optional<Notification> result = notificationRepository.findById(testNotification.getId());

        assertTrue(result.isPresent());
        assertEquals(testNotification, result.get());
    }

    @Test
    public void testFindByIdFailure() {
        when(notificationRepository.findById("nonexistent-id")).thenReturn(Optional.empty());
        Optional<Notification> result = notificationRepository.findById("nonexistent-id");
        assertFalse(result.isPresent());
    }

    @Test
    public void testSaveSuccess() {
        Notification newNotification = new Notification();
        NotificationMessage message = new NotificationMessage(2, "New message", LocalDateTime.now());
        List<NotificationMessage> messages = new ArrayList<>();
        messages.add(message);
        newNotification.setMessages(messages);
        when(notificationRepository.save(newNotification)).thenReturn(newNotification);

        Notification savedNotification = notificationRepository.save(newNotification);

        assertEquals(newNotification, savedNotification);
    }

}

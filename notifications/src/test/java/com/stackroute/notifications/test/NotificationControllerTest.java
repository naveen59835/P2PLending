/*
 * Author : Naveen Kumar
 * Date : 07-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.notifications.test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.stackroute.notifications.controller.NotificationController;
import com.stackroute.notifications.model.NotificationMessage;
import com.stackroute.notifications.services.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.stackroute.notifications.model.Notification;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationMessage mockNotificationMessage;

    @Test
    public void testGetAllSuccess() throws Exception {
        // create a test NotificationMessage list to return from the mock NotificationService
        List<NotificationMessage> notificationMessages = new ArrayList<>();
        NotificationMessage notificationMessage1 = new NotificationMessage(1, "Test message 1", LocalDateTime.now());
        NotificationMessage notificationMessage2 = new NotificationMessage(2, "Test message 2", LocalDateTime.now());
        notificationMessages.add(notificationMessage1);
        notificationMessages.add(notificationMessage2);

        // set up the mock NotificationService to return the test NotificationMessage list when getAllNotification() is called with a test id
        when(notificationService.getAllNotification("test-id")).thenReturn(notificationMessages);

        // perform a GET request to the "/api/v1/notification/getAll/test-id" endpoint using MockMvc
        mockMvc.perform(get("/api/v1/notification/getAll/test-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }





}

/*
 * Author : Naveen Kumar
 * Date : 07-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.chat.test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.chat.controller.ChatController;
import com.stackroute.chat.model.Chat;
import com.stackroute.chat.model.Message;
import com.stackroute.chat.model.Text;
import com.stackroute.chat.service.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDateTime;



@ExtendWith(SpringExtension.class)
@WebMvcTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatServiceImpl chatService;

    private ObjectMapper objectMapper;
    private Chat chat;
    private Text text;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        chat = new Chat();
        chat.setId("1");
        chat.setBorrowerId("borrower1");
        chat.setLenderId("lender1");

        text = new Text();
        text.setSenderId("borrower1");
        text.setRecipientId("lender1");
        text.setMessage("Hello");
        text.setRole("borrower");
    }


    @Test
    public void getAllChats_success() throws Exception {
        when(chatService.getAllChats("borrower1@example.com", "borrower")).thenReturn(Arrays.asList(chat));

        mockMvc.perform(get("/api/v1/chat/chats")
                        .param("email", "borrower1@example.com")
                        .param("role", "borrower"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].borrowerId").value("borrower1"))
                .andExpect(jsonPath("$[0].lenderId").value("lender1"));

        verify(chatService, times(1)).getAllChats("borrower1@example.com", "borrower");
    }

    @Test
    public void getMessages_success() throws Exception {
        when(chatService.getMessagesFromChat("1")).thenReturn(chat);

        mockMvc.perform(get("/api/v1/chat/chats/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.borrowerId").value("borrower1"))
                .andExpect(jsonPath("$.lenderId").value("lender1"));

        verify(chatService, times(1)).getMessagesFromChat("1");
    }


    @Test
    public void testAddNewChat() throws Exception {
        Map<String, String> chatData = new HashMap<>();
        chatData.put("borrowerId", "user1@example.com");
        chatData.put("lenderId", "user2@example.com");

        doNothing().when(chatService).addChat(chatData.get("borrowerId"), chatData.get("lenderId"));

        mockMvc.perform(post("/api/v1/chat/newChat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"borrowerId\":\"user1@example.com\",\"lenderId\":\"user2@example.com\"}"))
                .andExpect(status().isCreated());

        verify(chatService, times(1)).addChat(chatData.get("borrowerId"), chatData.get("lenderId"));
        verifyNoMoreInteractions(chatService);
    }


    @Test
    public void testGetAllChats() throws Exception {
        Message message1 = new Message();
        message1.setMessage("Hello");
        message1.setTimeStamp(LocalDateTime.now());
        message1.setSenderId("user1@example.com");
        message1.setReceiverId("user2@example.com");

        Message message2 = new Message();
        message2.setMessage("Hi");
        message2.setTimeStamp(LocalDateTime.now());
        message2.setSenderId("user2@example.com");
        message2.setReceiverId("user1@example.com");


        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        Chat chat1 = new Chat();
        chat1.setId("1");
        chat1.setBorrowerId("user1@example.com");
        chat1.setLenderId("user2@example.com");
        chat1.setMessages(messages);

        Chat chat2 = new Chat();
        chat2.setId("2");
        chat2.setBorrowerId("user1@example.com");
        chat2.setLenderId("user3@example.com");
        chat2.setMessages(new ArrayList<>());

        when(chatService.getAllChats("user1@example.com", "borrower")).thenReturn(Arrays.asList(chat1, chat2));

        mockMvc.perform(get("/api/v1/chat/chats")
                        .param("email", "user1@example.com")
                        .param("role", "borrower"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));

        verify(chatService, times(1)).getAllChats("user1@example.com", "borrower");
        verifyNoMoreInteractions(chatService);
    }




}

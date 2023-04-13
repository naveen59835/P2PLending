package com.stackroute.chat.service;

import com.stackroute.chat.model.Chat;
import com.stackroute.chat.model.Text;

import java.util.List;

public interface ChatService {
    void sendMessage(Text text);

    List<Chat> getAllChats(String userId, String role);

    Chat getMessagesFromChat(String chatId);

    String addChat(String borrowerId, String lenderId);
}

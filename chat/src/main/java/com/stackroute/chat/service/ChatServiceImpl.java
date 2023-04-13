package com.stackroute.chat.service;

import com.stackroute.chat.model.Chat;
import com.stackroute.chat.model.Message;
import com.stackroute.chat.model.Text;
import com.stackroute.chat.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    SimpMessagingTemplate template;

    @Override
    public void sendMessage(Text text){
        Chat chat = null;
        if(text.getRole().equals("borrower")){
            chat = chatRepository.findChatByBorrowerIdAndLenderId(text.getSenderId(),text.getRecipientId());
        }
        else if(text.getRole().equals("lender")){
            chat = chatRepository.findChatByBorrowerIdAndLenderId(text.getRecipientId(),text.getSenderId());
        }
        if(chat!=null){
            List<Message> messageList = chat.getMessages();
            Message newMassage = new Message();
            newMassage.setMessage(text.getMessage());
            newMassage.setTimeStamp(LocalDateTime.now());
            newMassage.setSenderId(text.getSenderId());
            newMassage.setReceiverId(text.getRecipientId());
            messageList.add(newMassage);
            chat.setMessages(messageList);
            chatRepository.save(chat);
            template.convertAndSend("/topic/messages/"+text.getRecipientId(),newMassage);
        }
    }
    @Override
    public List<Chat> getAllChats(String userId, String role){
        if(role.equals("borrower")){
            return chatRepository.findChatsByBorrowerId(userId);
        }
        else if(role.equals("lender")){
            return chatRepository.findChatsByLenderId(userId);
        }
        throw new RuntimeException("Doesn't match");
    }
    @Override
    public Chat getMessagesFromChat(String chatId){
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isPresent()){
            return chat.get();
        }
        throw new RuntimeException("Chat not found");
    }
    @Override
    public void addChat(String borrowerId, String lenderId){
        if(chatRepository.findChatByBorrowerIdAndLenderId(borrowerId,lenderId)==null){
            Chat chat = new Chat();
            chat.setBorrowerId(borrowerId);
            chat.setLenderId(lenderId);
            chatRepository.save(chat);
        }
        else throw new RuntimeException("Chat already Exists");
    }
}

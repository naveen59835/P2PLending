package com.stackroute.chat.repository;

import com.stackroute.chat.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<Chat,String> {
    Chat findChatByBorrowerIdAndLenderId (String borrowerId, String lenderId);
    List<Chat> findChatsByBorrowerId(String borrowerId);
    List<Chat> findChatsByLenderId(String lenderId);
}

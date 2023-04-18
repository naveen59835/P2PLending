/*
 * Author : Naveen Kumar
 * Date : 07-04-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.stackroute.chat.test;
import com.stackroute.chat.model.Chat;
import com.stackroute.chat.repository.ChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ChatRepoTest {
    @Autowired
    private ChatRepository chatRepository;

    private Chat chat1, chat2;

    @BeforeEach
    public void setUp() {
        chat1 = new Chat();
        chat1.setBorrowerId("borrower1");
        chat1.setLenderId("lender1");

        chat2 = new Chat();
        chat2.setBorrowerId("borrower2");
        chat2.setLenderId("lender2");

        chatRepository.save(chat1);
        chatRepository.save(chat2);
    }

    @AfterEach
    public void tearDown() {
        chatRepository.deleteAll();
    }

    @Test
    public void givenBorrowerIdAndLenderId_whenFindChatByBorrowerIdAndLenderId_thenChatShouldBeFound() {
        Chat foundChat = chatRepository.findChatByBorrowerIdAndLenderId(chat1.getBorrowerId(), chat1.getLenderId());
        assertNotNull(foundChat);
        assertThat(foundChat.getBorrowerId()).isEqualTo(chat1.getBorrowerId());
        assertThat(foundChat.getLenderId()).isEqualTo(chat1.getLenderId());
    }

    @Test
    public void givenInvalidBorrowerIdAndLenderId_whenFindChatByBorrowerIdAndLenderId_thenChatShouldNotBeFound() {
        Chat foundChat = chatRepository.findChatByBorrowerIdAndLenderId("invalidBorrowerId", "invalidLenderId");
        assertNull(foundChat);
    }

    @Test
    public void givenBorrowerId_whenFindChatsByBorrowerId_thenChatsShouldBeFound() {
        List<Chat> foundChats = chatRepository.findChatsByBorrowerId(chat1.getBorrowerId());
        assertThat(foundChats).isNotEmpty();
        assertThat(foundChats.get(0).getBorrowerId()).isEqualTo(chat1.getBorrowerId());
    }

    @Test
    public void givenLenderId_whenFindChatsByLenderId_thenChatsShouldBeFound() {
        List<Chat> foundChats = chatRepository.findChatsByLenderId(chat1.getLenderId());
        assertThat(foundChats).isNotEmpty();
        assertThat(foundChats.get(0).getLenderId()).isEqualTo(chat1.getLenderId());
    }

    @Test
    public void givenInvalidLenderId_whenFindChatsByLenderId_thenChatsShouldNotBeFound() {
        List<Chat> foundChats = chatRepository.findChatsByLenderId("invalidLenderId");
        assertThat(foundChats).isEmpty();
    }

    @Test
    public void givenChatId_whenFindById_thenChatShouldBeFound() {
        Optional<Chat> foundChat = chatRepository.findById(chat1.getId());
        assertTrue(foundChat.isPresent());
        assertThat(foundChat.get().getId()).isEqualTo(chat1.getId());
    }

    @Test
    public void givenInvalidChatId_whenFindById_thenChatShouldNotBeFound() {
        Optional<Chat> foundChat = chatRepository.findById("invalidChatId");
        assertFalse(foundChat.isPresent());
    }
    @Test
    public void givenInvalidBorrowerId_whenFindChatsByBorrowerId_thenChatsShouldNotBeFound() {
        List<Chat> foundChats = chatRepository.findChatsByBorrowerId("invalidBorrowerId");
        assertThat(foundChats).isEmpty();
    }

}

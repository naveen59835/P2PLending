package com.stackroute.chat.controller;
import com.stackroute.chat.model.Text;
import com.stackroute.chat.service.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {
    @Autowired
    ChatServiceImpl chatService;

    @MessageMapping("/send")
    public void sendMessage(@Payload Text text){
        chatService.sendMessage(text);
    }
    @GetMapping("/chats")
    public ResponseEntity<?> getAllChats(@RequestParam("email") String email, @RequestParam("role") String role){
        try{
            return new ResponseEntity<>(chatService.getAllChats(email,role), HttpStatus.OK);
        }catch(Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
    @GetMapping("/chats/{id}")
    public ResponseEntity<?> getMessages(@PathVariable String id){
        try{
            return new ResponseEntity<>(chatService.getMessagesFromChat(id), HttpStatus.OK);
        }
        catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }
    @PostMapping(value = "/newChat",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewChat(@RequestBody Map<String,String> chatData){
        try{
            return new ResponseEntity<>(Map.of("id",chatService.addChat(chatData.get("borrowerId"),chatData.get("lenderId"))),HttpStatus.CREATED);
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }

}

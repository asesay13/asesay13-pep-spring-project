package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageRepository messageRepository;

    public ResponseEntity<?> createMsg(Message msg) {
        
        if (msg.getMessageText() == null || msg.getMessageText().isBlank() || msg.getMessageText().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("message is blank/too long");
        }
    
        if (msg.getPostedBy() == null || !accountRepository.existsById(msg.getPostedBy())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user does not exist");
        }
    
        Message savedMsg = messageRepository.save(msg);
        return ResponseEntity.ok(savedMsg);
    }

    public ResponseEntity<List<Message>> getAll() {
        List<Message> messages = messageRepository.findAll(); 
        return ResponseEntity.ok(messages); 
    }

    public ResponseEntity<Message> getMsgById(Integer msgId) {
        return messageRepository.findById(msgId) .map(ResponseEntity::ok) .orElseGet(() -> ResponseEntity.ok(null));
    }

    public ResponseEntity<?> deleteMsgById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return ResponseEntity.ok(1); 
        } else {
            return ResponseEntity.ok().build(); 
        }
    }

    public ResponseEntity<?> updateMsg(int messageId, String newTxt) {
        if (newTxt == null || newTxt.isBlank() || newTxt.length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    
        Optional<Message> optionalMsg = messageRepository.findById(messageId);
    
        if (optionalMsg.isPresent()) {
            Message msg = optionalMsg.get();
            msg.setMessageText(newTxt);
            messageRepository.save(msg);
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    public List<Message> getMsgByAccId(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

   

}
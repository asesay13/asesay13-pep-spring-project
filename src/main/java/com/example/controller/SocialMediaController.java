
package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
        @Autowired
        private AccountService accountService;

        @Autowired
        private MessageService messageService;
    
        @PostMapping("/register")
        public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        return accountService.register(account);
        }

        @PostMapping("/login")
        public ResponseEntity<?> loginAccount(@RequestBody Account account) {
        return accountService.login(account);
    }

        @PostMapping("/messages")
        public ResponseEntity<?> postMessage(@RequestBody Message message) {
        return messageService.createMsg(message);
    }

    @GetMapping("/messages")
    public ResponseEntity<?> getAll() {
        return messageService.getAll(); 
    }
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMsgById(@PathVariable Integer messageId) {
        return messageService.getMsgById(messageId); 
    }

    @DeleteMapping("/messages/{message_id}")
public ResponseEntity<?> deleteMsgById(@PathVariable("message_id") Integer messageId) {
    return messageService.deleteMsgById(messageId);
}

@PatchMapping("/messages/{message_id}")
public ResponseEntity<?> updateMsg(@PathVariable("message_id") int messageId, @RequestBody Message updatedMessage) {
    return messageService.updateMsg(messageId, updatedMessage.getMessageText());
}

@GetMapping("/accounts/{account_id}/messages")
public ResponseEntity<?> getMessagesByAccountId(@PathVariable("account_id") int accountId) {
    return ResponseEntity.ok(messageService.getMsgByAccId(accountId));
}
    }


    


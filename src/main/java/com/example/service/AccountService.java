package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private MessageRepository messageRepository;

    public ResponseEntity<?> register(Account account) {

        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username cant be blank");
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("password should be at least 4 characters");
        }

        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user already exists");
        }

        Account savedAccount = accountRepository.save(account);
        return ResponseEntity.ok(savedAccount);
    }

            public ResponseEntity<?> login(Account account) {
                if (account.getUsername()== null || account.getPassword()==null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username or password cant be blank");
                }

                Account doesExist = accountRepository.findByUsername(account.getUsername()).orElse(null);

                if (doesExist == null || !doesExist.getPassword().equals(account.getPassword())) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid username or password");
                }

                return ResponseEntity.ok(doesExist);
            }

}
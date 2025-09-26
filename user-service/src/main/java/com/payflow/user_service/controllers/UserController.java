package com.payflow.user_service.controllers;

import com.payflow.user_service.entities.User;
import com.payflow.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(service.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}/can-transfer")
    public ResponseEntity<Boolean> canTransfer(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) throws Exception {
        return new ResponseEntity<>(service.canTransfer(id, amount), HttpStatus.OK);

    }

    @PostMapping("/update-balance")
    public ResponseEntity<Void> updateBalance(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam BigDecimal amount) throws Exception {
        service.updateBalance(senderId, receiverId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

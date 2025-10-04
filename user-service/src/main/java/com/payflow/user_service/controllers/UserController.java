package com.payflow.user_service.controllers;

import com.payflow.user_service.dtos.UserRequestDTO;
import com.payflow.user_service.dtos.UserResponseDTO;
import com.payflow.user_service.services.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO dto) {
        return new ResponseEntity<>(service.saveUser(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> GetUserById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getUserById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}/can-transfer")
    public ResponseEntity<Boolean> canTransfer(
            @PathVariable Long id,
            @RequestParam BigDecimal amount) {
        return new ResponseEntity<>(service.canTransfer(id, amount), HttpStatus.OK);
    }

    @PutMapping("/update-balance")
    public ResponseEntity<Void> updateBalance(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestParam BigDecimal amount) {
        service.updateBalance(senderId, receiverId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

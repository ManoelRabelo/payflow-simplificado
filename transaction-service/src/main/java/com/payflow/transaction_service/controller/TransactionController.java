package com.payflow.transaction_service.controller;

import com.payflow.transaction_service.entities.Transaction;
import com.payflow.transaction_service.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    public TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) throws Exception {
        return new ResponseEntity<>(service.createTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> GetTransactionById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(service.getTransactionById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() throws Exception {
        return new ResponseEntity<>(service.getAllTransactions(), HttpStatus.OK);
    }

}

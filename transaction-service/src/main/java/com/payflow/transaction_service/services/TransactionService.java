package com.payflow.transaction_service.services;

import com.payflow.transaction_service.clients.UserClient;
import com.payflow.transaction_service.entities.Transaction;
import com.payflow.transaction_service.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    public TransactionRepository repository;

    @Autowired
    public UserClient userClient;

    @Autowired
    public RestTemplate restTemplate;

    public Transaction createTransaction(Transaction transaction) throws Exception {
        return processTransaction(transaction);
    }

    public Transaction getTransactionById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("Transação não encontrada."));
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }


    public Transaction processTransaction(Transaction transaction) throws Exception {

        userClient.findById(transaction.getSenderId());
        userClient.findById(transaction.getReceiverId());

        userClient.canTransfer(transaction.getSenderId(), transaction.getAmount());

        if (!authorizeTransaction(transaction)) {
            throw new Exception("Transação não autorizada pelo serviço externo.");
        }

        transaction.setTimeStamp(LocalDateTime.now());

        Transaction savedTransaction = repository.save(transaction);

        userClient.updateBalance(transaction.getSenderId(), transaction.getReceiverId(), transaction.getAmount());

        return savedTransaction;
    }

    public Boolean authorizeTransaction(Transaction transaction) throws Exception {
        String url = "https://util.devi.tools/api/v2/authorize";

        ResponseEntity<Map> autorizationResponse = restTemplate.getForEntity(url, Map.class);

        if (autorizationResponse.getStatusCode() == HttpStatus.OK) {
            Map body = autorizationResponse.getBody();

            if (body != null) {
                String status = (String) body.get("status");
                Map data = (Map) body.get("data");
                boolean authorized = (Boolean) data.get("authorization");

                return authorized;
            }
        }

        return false;
    }
}

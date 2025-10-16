package com.payflow.transaction_service.services;

import com.payflow.transaction_service.clients.UserClient;
import com.payflow.transaction_service.dtos.AuthorizationResponseDTO;
import com.payflow.transaction_service.entities.Transaction;
import com.payflow.transaction_service.exception.AuthorizationDeniedException;
import com.payflow.transaction_service.exception.TransactionNotFoundException;
import com.payflow.transaction_service.exception.TransactionProcessingException;
import com.payflow.transaction_service.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

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

    public Transaction getTransactionById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transação não encontrada."));
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction processTransaction(Transaction transaction) {
        userClient.findById(transaction.getSenderId());
        userClient.findById(transaction.getReceiverId());
        userClient.canTransfer(transaction.getSenderId(), transaction.getAmount());

        if (!authorizeTransaction(transaction)) {
            throw new AuthorizationDeniedException("Transação não autorizada pelo serviço externo.");
        }

        transaction.setTimeStamp(LocalDateTime.now());

        Transaction savedTransaction = repository.save(transaction);
        userClient.updateBalance(transaction.getSenderId(), transaction.getReceiverId(), transaction.getAmount());

        return savedTransaction;
    }

    public boolean authorizeTransaction(Transaction transaction) {
        String url = "https://util.devi.tools/api/v2/authorize";

        try {
            ResponseEntity<AuthorizationResponseDTO> response = restTemplate
                    .getForEntity(url, AuthorizationResponseDTO.class);

            AuthorizationResponseDTO body = response.getBody();

            if (response.getStatusCode() == HttpStatus.OK && body != null) {
                return body.data().authorization();
            } else {
                return false;
            }

        } catch (HttpClientErrorException exception) {
            throw new AuthorizationDeniedException("Transação não autorizada pelo serviço externo.");
        } catch (RestClientException exception) {
            throw new TransactionProcessingException(exception.getMessage());
        }
    }
}

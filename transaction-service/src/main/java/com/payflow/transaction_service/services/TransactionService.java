package com.payflow.transaction_service.services;

import com.payflow.transaction_service.entities.Transaction;
import com.payflow.transaction_service.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    public TransactionRepository repository;

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

        // chamar UserService para
        // verificar existencia de sender e receiver
        // validar se sender pode realizar transacao

        authorizeTransaction(transaction);

        transaction.setTimeStamp(LocalDateTime.now());

        Transaction savedTransaction = repository.save(transaction);

        // atualizar saldos chamando novamente UserService

        return savedTransaction;
    }

    public Boolean authorizeTransaction(Transaction transaction) throws Exception {

        // chamada ao serviço mock de autorizacao externa
        // GET https://util.devi.tools/api/v2/authorize
        Boolean authorized = true;

        if (!authorized) {
            throw new Exception("Transação não autorizada pelo serviço externo.");
        }

        return true;
    }
}

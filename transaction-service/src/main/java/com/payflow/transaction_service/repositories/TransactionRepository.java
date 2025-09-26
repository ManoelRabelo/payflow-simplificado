package com.payflow.transaction_service.repositories;

import com.payflow.transaction_service.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

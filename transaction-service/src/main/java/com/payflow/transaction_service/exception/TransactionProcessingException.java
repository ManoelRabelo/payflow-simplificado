package com.payflow.transaction_service.exception;

public class TransactionProcessingException extends RuntimeException {

    public TransactionProcessingException(String message) {
        super(message);
    }
}
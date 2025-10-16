package com.payflow.transaction_service.exception;

import org.springframework.http.HttpStatus;

public class RemoteServiceException extends RuntimeException {
    private final HttpStatus status;

    public RemoteServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

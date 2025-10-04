package com.payflow.user_service.exceptions;

public class MerchantNotAllowedException extends RuntimeException {

    public MerchantNotAllowedException(String message) {
        super(message);
    }
}

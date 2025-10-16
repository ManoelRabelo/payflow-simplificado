package com.payflow.transaction_service.dtos;

import java.time.LocalDateTime;

public record ExceptionDTO(
        int status,
        String message,
        LocalDateTime timeStamp
) {
}

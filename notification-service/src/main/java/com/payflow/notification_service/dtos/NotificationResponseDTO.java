package com.payflow.notification_service.dtos;

import com.payflow.notification_service.entities.NotificationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NotificationResponseDTO(
        Long id,
        Long transactionId,
        Long recipientId,
        String recipientEmail,
        String message,
        BigDecimal amount,
        NotificationStatus status,
        LocalDateTime transactionTimestamp,
        LocalDateTime createdAt,
        LocalDateTime lastAttemptAt,
        Integer attemptCount
) {
}

package com.payflow.notification_service.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NotificationRequestDTO(
        Long transactionId,
        Long senderId,
        String senderEmail,
        Long receiverId,
        String receiverEmail,
        BigDecimal amount,
        LocalDateTime transactionTimestamp
) {
}

package com.payflow.notification_service.services;

import com.payflow.notification_service.dtos.NotificationRequestDTO;
import com.payflow.notification_service.dtos.NotificationResponseDTO;
import com.payflow.notification_service.entities.Notification;
import com.payflow.notification_service.entities.NotificationStatus;
import com.payflow.notification_service.respositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    public NotificationRepository repository;

    @Autowired
    public NotificationSenderService senderService;

    public NotificationResponseDTO getNotificationByID(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));

        return new NotificationResponseDTO(
                notification.getId(),
                notification.getTransactionId(),
                notification.getRecipientId(),
                notification.getRecipientEmail(),
                notification.getMessage(),
                notification.getAmount(),
                notification.getStatus(),
                notification.getTransactionTimeStamp(),
                notification.getCreatedAt(),
                notification.getLastAttemptAt()
        );
    }

    public List<NotificationResponseDTO> getAllNotifications() {
        List<Notification> notifications = repository.findAll();
        return notifications.stream()
                .map(notification -> new NotificationResponseDTO(
                        notification.getId(),
                        notification.getTransactionId(),
                        notification.getRecipientId(),
                        notification.getRecipientEmail(),
                        notification.getMessage(),
                        notification.getAmount(),
                        notification.getStatus(),
                        notification.getTransactionTimeStamp(),
                        notification.getCreatedAt(),
                        notification.getLastAttemptAt()))
                .toList();
    }

    public void sendNotifications(NotificationRequestDTO request) {
        LocalDateTime now = LocalDateTime.now();

        Notification senderNotification = Notification.builder()
                .transactionId(request.transactionId())
                .recipientId(request.senderId())
                .recipientEmail(request.senderEmail())
                .message(buildSenderMessage(request.amount()))
                .amount(request.amount())
                .status(NotificationStatus.PENDING)
                .transactionTimeStamp(request.transactionTimestamp())
                .createdAt(now)
                .lastAttemptAt(null)
                .build();

        Notification receiverNotification = Notification.builder()
                .transactionId(request.transactionId())
                .recipientId(request.receiverId())
                .recipientEmail(request.receiverEmail())
                .message(buildReceiverMessage(request.amount()))
                .amount(request.amount())
                .status(NotificationStatus.PENDING)
                .transactionTimeStamp(request.transactionTimestamp())
                .createdAt(now)
                .lastAttemptAt(null)
                .build();

        repository.save(senderNotification);
        repository.save(receiverNotification);

        senderService.sendAsync(senderNotification);
        senderService.sendAsync(receiverNotification);
    }

    private String buildSenderMessage(BigDecimal amount) {
        return String.format("Você enviou uma transferência de R$ %.2f com sucesso!", amount);
    }

    private String buildReceiverMessage(BigDecimal amount) {
        return String.format("Você recebeu uma transferência de R$ %.2f em sua conta!", amount);
    }
}

package com.payflow.notification_service.services;

import com.payflow.notification_service.dtos.NotificationMessageDTO;
import com.payflow.notification_service.entities.Notification;
import com.payflow.notification_service.entities.NotificationStatus;
import com.payflow.notification_service.respositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class NotificationSenderService {

    @Autowired
    public NotificationRepository repository;

    @Autowired
    public RestTemplate restTemplate;

    @Async
    public void sendAsync(Notification notification) {
        try {
            send(notification);
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private void send(Notification notification) {
        String url = "https://util.devi.tools/api/v1/notify";

        notification.setLastAttemptAt(LocalDateTime.now());

        NotificationMessageDTO payload = new NotificationMessageDTO(
                notification.getRecipientEmail(),
                notification.getMessage()
        );

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(url, payload, Void.class);

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                notification.setStatus(NotificationStatus.SUCCESS);
            } else {
                notification.setStatus(NotificationStatus.FAILED);
            }
        } catch (RestClientException exception) {
            notification.setStatus(NotificationStatus.FAILED);
            throw new RuntimeException("Error: " + exception.getMessage());
        }

        repository.save(notification);
    }
}

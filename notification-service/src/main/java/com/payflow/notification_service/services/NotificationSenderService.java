package com.payflow.notification_service.services;

import com.payflow.notification_service.dtos.NotificationMessageDTO;
import com.payflow.notification_service.entities.Notification;
import com.payflow.notification_service.entities.NotificationStatus;
import com.payflow.notification_service.respositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Async("notificationExecutor")
    public void sendAsync(Notification notification) {
        send(notification);
    }

    @Async("notificationExecutor")
    public void retryAsync(Notification notification) {
        send(notification);
    }

    private void send(Notification notification) {
        notification.setLastAttemptAt(LocalDateTime.now());
        notification.setAttemptCount(notification.getAttemptCount() + 1);

        try {
            String url = "https://util.devi.tools/api/v1/notify";
            NotificationMessageDTO payload = new NotificationMessageDTO(
                    notification.getRecipientEmail(),
                    notification.getMessage()
            );

            restTemplate.postForEntity(url, payload, Void.class);

            notification.setStatus(NotificationStatus.SUCCESS);
            System.out.printf("Id: %d - Tentativas: %d - Mensagem enviada com sucesso.%n",
                    notification.getId(), notification.getAttemptCount());
        } catch (RestClientException exception) {
            if (notification.getAttemptCount() >= 3) {
                notification.setStatus(NotificationStatus.FAILED);
                System.out.printf("Id: %d - Tentativas: %d - Limite excedido.%n",
                        notification.getId(), notification.getAttemptCount());
            } else {
                notification.setStatus(NotificationStatus.PENDING);
                System.out.printf("Id: %d - Tentativas: %d - Erro no envio. Tentará novamente.%n",
                        notification.getId(), notification.getAttemptCount());
            }
        } finally {
            repository.save(notification);
        }
    }
}

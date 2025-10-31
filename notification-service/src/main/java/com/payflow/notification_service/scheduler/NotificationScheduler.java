package com.payflow.notification_service.scheduler;

import com.payflow.notification_service.entities.Notification;
import com.payflow.notification_service.entities.NotificationStatus;
import com.payflow.notification_service.respositories.NotificationRepository;
import com.payflow.notification_service.services.NotificationSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationScheduler {

    @Autowired
    public NotificationRepository repository;

    @Autowired
    public NotificationSenderService senderService;

    @Scheduled(fixedDelay = 30000)
    public void retryPendingNotification() {
        List<Notification> notifications = repository.findByStatusIn(List.of(
                NotificationStatus.PENDING, NotificationStatus.FAILED)
        );

        notifications.stream()
                .filter(notification -> notification.getAttemptCount() < 3)
                .forEach(senderService::retryAsync);
    }

}

package com.payflow.notification_service.respositories;

import com.payflow.notification_service.entities.Notification;
import com.payflow.notification_service.entities.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatusIn(List<NotificationStatus> statuses);
}

package com.edumanager.communication.domain.notification.repository;

import com.edumanager.communication.domain.notification.entity.Notification;
import com.edumanager.communication.domain.notification.enums.NotificationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: NotificationRepository
 * Defines the contract for notification persistence operations.
 */
public interface NotificationRepository {

    Notification save(Notification notification);

    Optional<Notification> findById(UUID id);

    List<Notification> findByRecipientId(UUID recipientId);

    List<Notification> findByTenantId(UUID tenantId);

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByRecipientIdAndStatus(UUID recipientId, NotificationStatus status);

    List<Notification> findPendingNotificationsBefore(LocalDateTime dateTime);

    void deleteById(UUID id);
}

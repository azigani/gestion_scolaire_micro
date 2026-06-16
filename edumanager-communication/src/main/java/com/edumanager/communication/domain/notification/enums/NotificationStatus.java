package com.edumanager.communication.domain.notification.enums;

/**
 * Enum: NotificationStatus
 * Represents the status of a notification.
 */
public enum NotificationStatus {
    /**
     * Notification is pending to be sent
     */
    PENDING,
    
    /**
     * Notification has been sent successfully
     */
    SENT,
    
    /**
     * Notification failed to send
     */
    FAILED,
    
    /**
     * Notification has been delivered
     */
    DELIVERED,
    
    /**
     * Notification has been read
     */
    READ
}

package com.edumanager.communication.domain.notification.entity;

import com.edumanager.communication.domain.notification.enums.NotificationStatus;
import com.edumanager.communication.domain.shared.AggregateRoot;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Notification
 * 
 * Represents a notification sent to a recipient.
 * Contains business rules for notification management.
 * 
 * Business Rules:
 * - RG-NOT01: Notification must have a valid recipient
 * - RG-NOT02: Notification must have a valid channel
 * - RG-NOT03: Notification status transitions must follow valid state machine
 * - RG-NOT04: Failed notifications can be retried
 */
public class Notification extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    
    private UUID recipientId;
    private String recipientType;
    private UUID channelId;
    private UUID templateId;
    
    private String subject;
    private String message;
    private NotificationStatus status;
    
    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
    private String errorMessage;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Notification(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.recipientId = builder.recipientId;
        this.recipientType = builder.recipientType;
        this.channelId = builder.channelId;
        this.templateId = builder.templateId;
        this.subject = builder.subject;
        this.message = builder.message;
        this.status = builder.status != null ? builder.status : NotificationStatus.PENDING;
        this.scheduledAt = builder.scheduledAt;
        this.sentAt = builder.sentAt;
        this.errorMessage = builder.errorMessage;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Mark notification as sent
     */
    public void markAsSent() {
        if (this.status == NotificationStatus.SENT || this.status == NotificationStatus.DELIVERED) {
            throw new IllegalStateException("Notification is already sent");
        }
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Mark notification as failed
     */
    public void markAsFailed(String errorMessage) {
        if (this.status == NotificationStatus.SENT || this.status == NotificationStatus.DELIVERED) {
            throw new IllegalStateException("Cannot mark sent notification as failed");
        }
        this.status = NotificationStatus.FAILED;
        this.errorMessage = errorMessage;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Mark notification as delivered
     */
    public void markAsDelivered() {
        if (this.status != NotificationStatus.SENT) {
            throw new IllegalStateException("Notification must be sent before marking as delivered");
        }
        this.status = NotificationStatus.DELIVERED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Mark notification as read
     */
    public void markAsRead() {
        if (this.status != NotificationStatus.DELIVERED) {
            throw new IllegalStateException("Notification must be delivered before marking as read");
        }
        this.status = NotificationStatus.READ;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Retry failed notification
     */
    public void retry() {
        if (this.status != NotificationStatus.FAILED) {
            throw new IllegalStateException("Only failed notifications can be retried");
        }
        this.status = NotificationStatus.PENDING;
        this.errorMessage = null;
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public UUID getRecipientId() { return recipientId; }
    public String getRecipientType() { return recipientType; }
    public UUID getChannelId() { return channelId; }
    public UUID getTemplateId() { return templateId; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
    public NotificationStatus getStatus() { return status; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public LocalDateTime getSentAt() { return sentAt; }
    public String getErrorMessage() { return errorMessage; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private UUID recipientId;
        private String recipientType;
        private UUID channelId;
        private UUID templateId;
        private String subject;
        private String message;
        private NotificationStatus status;
        private LocalDateTime scheduledAt;
        private LocalDateTime sentAt;
        private String errorMessage;

        public Builder(UUID tenantId, UUID recipientId, String recipientType, 
                     UUID channelId, String message) {
            this.tenantId = tenantId;
            this.recipientId = recipientId;
            this.recipientType = recipientType;
            this.channelId = channelId;
            this.message = message;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder templateId(UUID templateId) {
            this.templateId = templateId;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder status(NotificationStatus status) {
            this.status = status;
            return this;
        }

        public Builder scheduledAt(LocalDateTime scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public Builder sentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Notification build() {
            validate();
            return new Notification(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (recipientId == null) throw new IllegalArgumentException("recipientId is required");
            if (recipientType == null || recipientType.isBlank()) throw new IllegalArgumentException("recipientType is required");
            if (channelId == null) throw new IllegalArgumentException("channelId is required");
            if (message == null || message.isBlank()) throw new IllegalArgumentException("message is required");
        }
    }
}

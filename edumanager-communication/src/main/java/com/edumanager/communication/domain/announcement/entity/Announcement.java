package com.edumanager.communication.domain.announcement.entity;

import com.edumanager.communication.domain.announcement.enums.AnnouncementPriority;
import com.edumanager.communication.domain.shared.AggregateRoot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Announcement
 * 
 * Represents an announcement sent to a target audience.
 * Contains business rules for announcement management.
 * 
 * Business Rules:
 * - RG-ANN01: Announcement must have a valid date range
 * - RG-ANN02: End date cannot be before start date
 * - RG-ANN03: Published announcements cannot be modified
 * - RG-ANN04: Only published announcements can be unpublished
 */
public class Announcement extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    
    private String title;
    private String content;
    private String announcementType;
    private AnnouncementPriority priority;
    
    private String targetAudience;
    private LocalDate startDate;
    private LocalDate endDate;
    
    private boolean isPublished;
    private LocalDateTime publishedAt;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Announcement(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.title = builder.title;
        this.content = builder.content;
        this.announcementType = builder.announcementType;
        this.priority = builder.priority != null ? builder.priority : AnnouncementPriority.NORMAL;
        this.targetAudience = builder.targetAudience;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.isPublished = builder.isPublished != null ? builder.isPublished : false;
        this.publishedAt = builder.publishedAt;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Publish announcement
     */
    public void publish() {
        if (this.isPublished) {
            throw new IllegalStateException("Announcement is already published");
        }
        if (this.startDate != null && this.startDate.isAfter(LocalDate.now())) {
            throw new IllegalStateException("Cannot publish announcement with future start date");
        }
        this.isPublished = true;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Unpublish announcement
     */
    public void unpublish() {
        if (!this.isPublished) {
            throw new IllegalStateException("Announcement is not published");
        }
        this.isPublished = false;
        this.publishedAt = null;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update announcement content
     */
    public void updateContent(String title, String content) {
        if (this.isPublished) {
            throw new IllegalStateException("Cannot update published announcement");
        }
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update announcement date range
     */
    public void updateDateRange(LocalDate startDate, LocalDate endDate) {
        if (this.isPublished) {
            throw new IllegalStateException("Cannot update published announcement");
        }
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Check if announcement is currently active
     */
    public boolean isActive() {
        if (!this.isPublished) {
            return false;
        }
        LocalDate now = LocalDate.now();
        if (this.startDate != null && now.isBefore(this.startDate)) {
            return false;
        }
        if (this.endDate != null && now.isAfter(this.endDate)) {
            return false;
        }
        return true;
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAnnouncementType() { return announcementType; }
    public AnnouncementPriority getPriority() { return priority; }
    public String getTargetAudience() { return targetAudience; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public boolean isPublished() { return isPublished; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private String title;
        private String content;
        private String announcementType;
        private AnnouncementPriority priority;
        private String targetAudience;
        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean isPublished;
        private LocalDateTime publishedAt;

        public Builder(UUID tenantId, String title, String content, String announcementType) {
            this.tenantId = tenantId;
            this.title = title;
            this.content = content;
            this.announcementType = announcementType;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder priority(AnnouncementPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder targetAudience(String targetAudience) {
            this.targetAudience = targetAudience;
            return this;
        }

        public Builder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder isPublished(Boolean isPublished) {
            this.isPublished = isPublished;
            return this;
        }

        public Builder publishedAt(LocalDateTime publishedAt) {
            this.publishedAt = publishedAt;
            return this;
        }

        public Announcement build() {
            validate();
            return new Announcement(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (title == null || title.isBlank()) throw new IllegalArgumentException("title is required");
            if (content == null || content.isBlank()) throw new IllegalArgumentException("content is required");
            if (announcementType == null || announcementType.isBlank()) throw new IllegalArgumentException("announcementType is required");
            if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
                throw new IllegalArgumentException("End date cannot be before start date");
            }
        }
    }
}

package com.edumanager.academic.domain.subject.entity;

import com.edumanager.academic.domain.shared.AggregateRoot;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Subject
 * 
 * Represents a subject taught in the school.
 * Contains business rules for subject management.
 * 
 * Business Rules:
 * - RG-SUB01: Subject code must be unique within tenant
 * - RG-SUB02: Coefficient must be positive
 * - RG-SUB03: Core subjects cannot be deleted if used in grades
 */
public class Subject extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    
    private String code;
    private String name;
    private String description;
    private int coefficient;
    private boolean isCore;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Subject(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.code = builder.code;
        this.name = builder.name;
        this.description = builder.description;
        this.coefficient = builder.coefficient;
        this.isCore = builder.isCore;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Update subject information
     */
    public void updateInfo(String name, String description, int coefficient, boolean isCore) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
        if (coefficient > 0) {
            this.coefficient = coefficient;
        }
        this.isCore = isCore;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Mark as core subject
     */
    public void markAsCore() {
        this.isCore = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Mark as elective subject
     */
    public void markAsElective() {
        this.isCore = false;
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCoefficient() { return coefficient; }
    public boolean isCore() { return isCore; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private String code;
        private String name;
        private String description;
        private int coefficient;
        private boolean isCore;

        public Builder(UUID tenantId, String code, String name, int coefficient) {
            this.tenantId = tenantId;
            this.code = code;
            this.name = name;
            this.coefficient = coefficient;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder isCore(boolean isCore) {
            this.isCore = isCore;
            return this;
        }

        public Subject build() {
            validate();
            return new Subject(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (code == null || code.isBlank()) throw new IllegalArgumentException("code is required");
            if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
            if (coefficient <= 0) throw new IllegalArgumentException("coefficient must be positive");
        }
    }
}

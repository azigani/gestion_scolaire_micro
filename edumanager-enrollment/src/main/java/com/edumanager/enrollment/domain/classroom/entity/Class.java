package com.edumanager.enrollment.domain.classroom.entity;

import com.edumanager.enrollment.domain.shared.AggregateRoot;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Class
 * 
 * Represents a class in the school.
 * Contains business rules for class management.
 * 
 * Business Rules:
 * - RG-C01: Class name must be unique within tenant and academic year
 * - RG-C02: Class capacity cannot be exceeded
 * - RG-C03: Main teacher must be assigned to only one class per academic year
 */
public class Class extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    private final UUID academicYearId;
    
    private String name;
    private String level;
    private int capacity;
    private String roomNumber;
    private UUID mainTeacherId;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Class(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.academicYearId = builder.academicYearId;
        this.name = builder.name;
        this.level = builder.level;
        this.capacity = builder.capacity;
        this.roomNumber = builder.roomNumber;
        this.mainTeacherId = builder.mainTeacherId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Check if class has reached capacity
     */
    public boolean isFull(int currentEnrollment) {
        return currentEnrollment >= capacity;
    }

    /**
     * Update class information
     */
    public void updateInfo(String name, String level, int capacity, String roomNumber) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (level != null && !level.isBlank()) {
            this.level = level;
        }
        if (capacity > 0) {
            this.capacity = capacity;
        }
        if (roomNumber != null) {
            this.roomNumber = roomNumber;
        }
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Assign main teacher
     */
    public void assignMainTeacher(UUID teacherId) {
        this.mainTeacherId = teacherId;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Remove main teacher
     */
    public void removeMainTeacher() {
        this.mainTeacherId = null;
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public UUID getAcademicYearId() { return academicYearId; }
    public String getName() { return name; }
    public String getLevel() { return level; }
    public int getCapacity() { return capacity; }
    public String getRoomNumber() { return roomNumber; }
    public UUID getMainTeacherId() { return mainTeacherId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private UUID academicYearId;
        private String name;
        private String level;
        private int capacity;
        private String roomNumber;
        private UUID mainTeacherId;

        public Builder(UUID tenantId, UUID academicYearId, String name, String level, int capacity) {
            this.tenantId = tenantId;
            this.academicYearId = academicYearId;
            this.name = name;
            this.level = level;
            this.capacity = capacity;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder roomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder mainTeacherId(UUID mainTeacherId) {
            this.mainTeacherId = mainTeacherId;
            return this;
        }

        public Class build() {
            validate();
            return new Class(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (academicYearId == null) throw new IllegalArgumentException("academicYearId is required");
            if (name == null || name.isBlank()) throw new IllegalArgumentException("name is required");
            if (level == null || level.isBlank()) throw new IllegalArgumentException("level is required");
            if (capacity <= 0) throw new IllegalArgumentException("capacity must be positive");
        }
    }
}

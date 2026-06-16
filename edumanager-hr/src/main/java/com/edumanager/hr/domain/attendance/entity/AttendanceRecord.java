package com.edumanager.hr.domain.attendance.entity;

import com.edumanager.hr.domain.attendance.enums.AttendanceStatus;
import com.edumanager.hr.domain.shared.AggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Aggregate Root: AttendanceRecord
 * 
 * Represents an attendance record for an employee.
 * Contains business rules for attendance tracking.
 * 
 * Business Rules:
 * - RG-ATT01: Only one attendance record per employee per day
 * - RG-ATT02: Check-out time must be after check-in time
 * - RG-ATT03: Work hours are calculated from check-in and check-out times
 */
public class AttendanceRecord extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    
    private UUID employeeId;
    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private BigDecimal workHours;
    private AttendanceStatus status;
    private String notes;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AttendanceRecord(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.employeeId = builder.employeeId;
        this.date = builder.date;
        this.checkInTime = builder.checkInTime;
        this.checkOutTime = builder.checkOutTime;
        this.workHours = builder.workHours;
        this.status = builder.status != null ? builder.status : AttendanceStatus.PRESENT;
        this.notes = builder.notes;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Check in employee
     */
    public void checkIn(LocalDateTime checkInTime) {
        if (this.checkInTime != null) {
            throw new IllegalStateException("Employee already checked in");
        }
        this.checkInTime = checkInTime != null ? checkInTime : LocalDateTime.now();
        this.status = AttendanceStatus.PRESENT;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Check out employee
     */
    public void checkOut(LocalDateTime checkOutTime) {
        if (this.checkInTime == null) {
            throw new IllegalStateException("Employee has not checked in");
        }
        if (this.checkOutTime != null) {
            throw new IllegalStateException("Employee already checked out");
        }
        this.checkOutTime = checkOutTime != null ? checkOutTime : LocalDateTime.now();
        if (this.checkOutTime.isBefore(this.checkInTime)) {
            throw new IllegalArgumentException("Check-out time must be after check-in time");
        }
        calculateWorkHours();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Mark as absent
     */
    public void markAsAbsent(String reason) {
        this.status = AttendanceStatus.ABSENT;
        this.notes = reason;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Mark as on leave
     */
    public void markAsOnLeave(String reason) {
        this.status = AttendanceStatus.ON_LEAVE;
        this.notes = reason;
        this.updatedAt = LocalDateTime.now();
    }

    private void calculateWorkHours() {
        if (this.checkInTime != null && this.checkOutTime != null) {
            long minutes = java.time.Duration.between(this.checkInTime, this.checkOutTime).toMinutes();
            this.workHours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, BigDecimal.ROUND_HALF_UP);
        }
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public UUID getEmployeeId() { return employeeId; }
    public LocalDate getDate() { return date; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public LocalDateTime getCheckOutTime() { return checkOutTime; }
    public BigDecimal getWorkHours() { return workHours; }
    public AttendanceStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private UUID employeeId;
        private LocalDate date;
        private LocalDateTime checkInTime;
        private LocalDateTime checkOutTime;
        private BigDecimal workHours;
        private AttendanceStatus status;
        private String notes;

        public Builder(UUID tenantId, UUID employeeId, LocalDate date) {
            this.tenantId = tenantId;
            this.employeeId = employeeId;
            this.date = date;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder checkInTime(LocalDateTime checkInTime) {
            this.checkInTime = checkInTime;
            return this;
        }

        public Builder checkOutTime(LocalDateTime checkOutTime) {
            this.checkOutTime = checkOutTime;
            return this;
        }

        public Builder workHours(BigDecimal workHours) {
            this.workHours = workHours;
            return this;
        }

        public Builder status(AttendanceStatus status) {
            this.status = status;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public AttendanceRecord build() {
            validate();
            return new AttendanceRecord(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (employeeId == null) throw new IllegalArgumentException("employeeId is required");
            if (date == null) throw new IllegalArgumentException("date is required");
        }
    }
}

package com.edumanager.hr.domain.attendance.enums;

/**
 * Enum: AttendanceStatus
 * Represents the status of an attendance record.
 */
public enum AttendanceStatus {
    /**
     * Employee was present
     */
    PRESENT,
    
    /**
     * Employee was absent
     */
    ABSENT,
    
    /**
     * Employee was late
     */
    LATE,
    
    /**
     * Employee was on leave
     */
    ON_LEAVE,
    
    /**
     * Employee was on a business trip
     */
    BUSINESS_TRIP
}

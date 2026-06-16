package com.edumanager.enrollment.domain.student.enums;

/**
 * Enum: StudentStatus
 * Represents the enrollment status of a student.
 */
public enum StudentStatus {
    /**
     * Student is currently enrolled and active
     */
    ACTIVE,
    
    /**
     * Student has been temporarily suspended
     */
    SUSPENDED,
    
    /**
     * Student has graduated
     */
    GRADUATED,
    
    /**
     * Student has withdrawn from the school
     */
    WITHDRAWN,
    
    /**
     * Student has transferred to another school
     */
    TRANSFERRED
}

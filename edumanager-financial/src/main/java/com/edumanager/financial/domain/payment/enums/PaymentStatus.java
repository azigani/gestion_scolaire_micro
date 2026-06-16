package com.edumanager.financial.domain.payment.enums;

/**
 * Enum: PaymentStatus
 * Represents the status of a payment.
 */
public enum PaymentStatus {
    /**
     * Payment has been initiated but not completed
     */
    PENDING,
    
    /**
     * Payment has been completed successfully
     */
    COMPLETED,
    
    /**
     * Payment has failed
     */
    FAILED,
    
    /**
     * Payment has been refunded
     */
    REFUNDED,
    
    /**
     * Payment has been cancelled
     */
    CANCELLED
}

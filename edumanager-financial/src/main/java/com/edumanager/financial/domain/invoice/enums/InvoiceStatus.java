package com.edumanager.financial.domain.invoice.enums;

/**
 * Enum: InvoiceStatus
 * Represents the status of an invoice.
 */
public enum InvoiceStatus {
    /**
     * Invoice has been created but not yet paid
     */
    PENDING,
    
    /**
     * Invoice has been partially paid
     */
    PARTIALLY_PAID,
    
    /**
     * Invoice has been fully paid
     */
    PAID,
    
    /**
     * Invoice is overdue
     */
    OVERDUE,
    
    /**
     * Invoice has been cancelled
     */
    CANCELLED,
    
    /**
     * Invoice has been written off
     */
    WRITTEN_OFF
}

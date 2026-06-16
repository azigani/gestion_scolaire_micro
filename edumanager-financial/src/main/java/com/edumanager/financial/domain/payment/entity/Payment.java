package com.edumanager.financial.domain.payment.entity;

import com.edumanager.financial.domain.payment.enums.PaymentStatus;
import com.edumanager.financial.domain.shared.AggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Payment
 * 
 * Represents a payment made for an invoice.
 * Contains business rules for payment management.
 * 
 * Business Rules:
 * - RG-PAY01: Payment number must be unique within tenant
 * - RG-PAY02: Payment amount must be positive
 * - RG-PAY03: Payment cannot exceed invoice balance
 * - RG-PAY04: Completed payments cannot be modified
 */
public class Payment extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    
    private String paymentNumber;
    private UUID invoiceId;
    private UUID studentId;
    
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDate paymentDate;
    private String referenceNumber;
    private String notes;
    
    private PaymentStatus status;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Payment(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.paymentNumber = builder.paymentNumber;
        this.invoiceId = builder.invoiceId;
        this.studentId = builder.studentId;
        this.amount = builder.amount;
        this.paymentMethod = builder.paymentMethod;
        this.paymentDate = builder.paymentDate != null ? builder.paymentDate : LocalDate.now();
        this.referenceNumber = builder.referenceNumber;
        this.notes = builder.notes;
        this.status = builder.status != null ? builder.status : PaymentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Complete payment
     */
    public void complete() {
        if (this.status == PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Payment is already completed");
        }
        this.status = PaymentStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Fail payment
     */
    public void fail(String reason) {
        if (this.status == PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot fail a completed payment");
        }
        this.status = PaymentStatus.FAILED;
        this.notes = reason;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Cancel payment
     */
    public void cancel() {
        if (this.status == PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed payment");
        }
        this.status = PaymentStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Refund payment
     */
    public void refund(String reason) {
        if (this.status != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Can only refund completed payments");
        }
        this.status = PaymentStatus.REFUNDED;
        this.notes = reason;
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public String getPaymentNumber() { return paymentNumber; }
    public UUID getInvoiceId() { return invoiceId; }
    public UUID getStudentId() { return studentId; }
    public BigDecimal getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public String getReferenceNumber() { return referenceNumber; }
    public String getNotes() { return notes; }
    public PaymentStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private String paymentNumber;
        private UUID invoiceId;
        private UUID studentId;
        private BigDecimal amount;
        private String paymentMethod;
        private LocalDate paymentDate;
        private String referenceNumber;
        private String notes;
        private PaymentStatus status;

        public Builder(UUID tenantId, String paymentNumber, UUID invoiceId, 
                     UUID studentId, BigDecimal amount, String paymentMethod) {
            this.tenantId = tenantId;
            this.paymentNumber = paymentNumber;
            this.invoiceId = invoiceId;
            this.studentId = studentId;
            this.amount = amount;
            this.paymentMethod = paymentMethod;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder paymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder referenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public Builder status(PaymentStatus status) {
            this.status = status;
            return this;
        }

        public Payment build() {
            validate();
            return new Payment(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (paymentNumber == null || paymentNumber.isBlank()) throw new IllegalArgumentException("paymentNumber is required");
            if (invoiceId == null) throw new IllegalArgumentException("invoiceId is required");
            if (studentId == null) throw new IllegalArgumentException("studentId is required");
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("amount must be positive");
            if (paymentMethod == null || paymentMethod.isBlank()) throw new IllegalArgumentException("paymentMethod is required");
        }
    }
}

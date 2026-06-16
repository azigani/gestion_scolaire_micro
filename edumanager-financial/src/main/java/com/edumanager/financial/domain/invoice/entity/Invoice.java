package com.edumanager.financial.domain.invoice.entity;

import com.edumanager.financial.domain.invoice.enums.InvoiceStatus;
import com.edumanager.financial.domain.shared.AggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Invoice
 * 
 * Represents an invoice for a student.
 * Contains business rules for invoice management.
 * 
 * Business Rules:
 * - RG-INV01: Invoice number must be unique within tenant
 * - RG-INV02: Total amount must equal subtotal + tax - discount
 * - RG-INV03: Balance cannot be negative
 * - RG-INV04: Invoice status transitions must follow valid state machine
 */
public class Invoice extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    
    private String invoiceNumber;
    private UUID studentId;
    private UUID academicYearId;
    private String term;
    
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal balanceAmount;
    
    private InvoiceStatus status;
    private LocalDate dueDate;
    private LocalDate issuedDate;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Invoice(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.invoiceNumber = builder.invoiceNumber;
        this.studentId = builder.studentId;
        this.academicYearId = builder.academicYearId;
        this.term = builder.term;
        this.subtotal = builder.subtotal;
        this.taxAmount = builder.taxAmount != null ? builder.taxAmount : BigDecimal.ZERO;
        this.discountAmount = builder.discountAmount != null ? builder.discountAmount : BigDecimal.ZERO;
        this.totalAmount = builder.totalAmount;
        this.paidAmount = builder.paidAmount != null ? builder.paidAmount : BigDecimal.ZERO;
        this.balanceAmount = builder.balanceAmount != null ? builder.balanceAmount : builder.totalAmount;
        this.status = builder.status != null ? builder.status : InvoiceStatus.PENDING;
        this.dueDate = builder.dueDate;
        this.issuedDate = builder.issuedDate != null ? builder.issuedDate : LocalDate.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Record a payment
     */
    public void recordPayment(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        if (amount.compareTo(balanceAmount) > 0) {
            throw new IllegalArgumentException("Payment amount cannot exceed balance");
        }
        
        this.paidAmount = this.paidAmount.add(amount);
        this.balanceAmount = this.balanceAmount.subtract(amount);
        this.updatedAt = LocalDateTime.now();
        
        updateStatus();
    }

    /**
     * Apply discount
     */
    public void applyDiscount(BigDecimal discountAmount) {
        if (discountAmount == null || discountAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Discount amount must be positive");
        }
        if (discountAmount.compareTo(this.totalAmount.subtract(this.paidAmount)) > 0) {
            throw new IllegalArgumentException("Discount cannot exceed remaining balance");
        }
        
        this.discountAmount = this.discountAmount.add(discountAmount);
        this.totalAmount = this.totalAmount.subtract(discountAmount);
        this.balanceAmount = this.balanceAmount.subtract(discountAmount);
        this.updatedAt = LocalDateTime.now();
        
        updateStatus();
    }

    /**
     * Mark as overdue
     */
    public void markAsOverdue() {
        if (this.status == InvoiceStatus.PAID || this.status == InvoiceStatus.CANCELLED) {
            throw new IllegalStateException("Cannot mark paid or cancelled invoice as overdue");
        }
        this.status = InvoiceStatus.OVERDUE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Cancel invoice
     */
    public void cancel() {
        if (this.status == InvoiceStatus.PAID) {
            throw new IllegalStateException("Cannot cancel a paid invoice");
        }
        this.status = InvoiceStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Write off invoice
     */
    public void writeOff() {
        if (this.status == InvoiceStatus.PAID || this.status == InvoiceStatus.CANCELLED) {
            throw new IllegalStateException("Cannot write off a paid or cancelled invoice");
        }
        this.status = InvoiceStatus.WRITTEN_OFF;
        this.balanceAmount = BigDecimal.ZERO;
        this.updatedAt = LocalDateTime.now();
    }

    private void updateStatus() {
        if (this.balanceAmount.compareTo(BigDecimal.ZERO) == 0) {
            this.status = InvoiceStatus.PAID;
        } else if (this.paidAmount.compareTo(BigDecimal.ZERO) > 0) {
            this.status = InvoiceStatus.PARTIALLY_PAID;
        } else {
            this.status = InvoiceStatus.PENDING;
        }
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public UUID getStudentId() { return studentId; }
    public UUID getAcademicYearId() { return academicYearId; }
    public String getTerm() { return term; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public BigDecimal getPaidAmount() { return paidAmount; }
    public BigDecimal getBalanceAmount() { return balanceAmount; }
    public InvoiceStatus getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private String invoiceNumber;
        private UUID studentId;
        private UUID academicYearId;
        private String term;
        private BigDecimal subtotal;
        private BigDecimal taxAmount;
        private BigDecimal discountAmount;
        private BigDecimal totalAmount;
        private BigDecimal paidAmount;
        private BigDecimal balanceAmount;
        private InvoiceStatus status;
        private LocalDate dueDate;
        private LocalDate issuedDate;

        public Builder(UUID tenantId, String invoiceNumber, UUID studentId, 
                     UUID academicYearId, BigDecimal totalAmount, LocalDate dueDate) {
            this.tenantId = tenantId;
            this.invoiceNumber = invoiceNumber;
            this.studentId = studentId;
            this.academicYearId = academicYearId;
            this.totalAmount = totalAmount;
            this.dueDate = dueDate;
            this.subtotal = totalAmount;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder term(String term) {
            this.term = term;
            return this;
        }

        public Builder taxAmount(BigDecimal taxAmount) {
            this.taxAmount = taxAmount;
            return this;
        }

        public Builder discountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public Builder paidAmount(BigDecimal paidAmount) {
            this.paidAmount = paidAmount;
            return this;
        }

        public Builder status(InvoiceStatus status) {
            this.status = status;
            return this;
        }

        public Builder issuedDate(LocalDate issuedDate) {
            this.issuedDate = issuedDate;
            return this;
        }

        public Invoice build() {
            validate();
            return new Invoice(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (invoiceNumber == null || invoiceNumber.isBlank()) throw new IllegalArgumentException("invoiceNumber is required");
            if (studentId == null) throw new IllegalArgumentException("studentId is required");
            if (academicYearId == null) throw new IllegalArgumentException("academicYearId is required");
            if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("totalAmount must be positive");
            if (dueDate == null) throw new IllegalArgumentException("dueDate is required");
        }
    }
}

package com.edumanager.financial.domain.invoice.repository;

import com.edumanager.financial.domain.invoice.entity.Invoice;
import com.edumanager.financial.domain.invoice.enums.InvoiceStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: InvoiceRepository
 * Defines the contract for invoice persistence operations.
 */
public interface InvoiceRepository {

    Invoice save(Invoice invoice);

    Optional<Invoice> findById(UUID id);

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findByStudentId(UUID studentId);

    List<Invoice> findByTenantId(UUID tenantId);

    List<Invoice> findByStatus(InvoiceStatus status);

    List<Invoice> findByStudentIdAndStatus(UUID studentId, InvoiceStatus status);

    boolean existsByInvoiceNumber(String invoiceNumber);

    void deleteById(UUID id);
}

package com.edumanager.financial.domain.payment.repository;

import com.edumanager.financial.domain.payment.entity.Payment;
import com.edumanager.financial.domain.payment.enums.PaymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: PaymentRepository
 * Defines the contract for payment persistence operations.
 */
public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(UUID id);

    Optional<Payment> findByPaymentNumber(String paymentNumber);

    List<Payment> findByInvoiceId(UUID invoiceId);

    List<Payment> findByStudentId(UUID studentId);

    List<Payment> findByStatus(PaymentStatus status);

    boolean existsByPaymentNumber(String paymentNumber);

    void deleteById(UUID id);
}

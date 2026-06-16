package com.edumanager.academic.domain.subject.repository;

import com.edumanager.academic.domain.subject.entity.Subject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: SubjectRepository
 * Defines the contract for subject persistence operations.
 */
public interface SubjectRepository {

    Subject save(Subject subject);

    Optional<Subject> findById(UUID id);

    Optional<Subject> findByTenantIdAndCode(UUID tenantId, String code);

    List<Subject> findByTenantId(UUID tenantId);

    List<Subject> findByTenantIdAndIsCore(UUID tenantId, boolean isCore);

    boolean existsByTenantIdAndCode(UUID tenantId, String code);

    void deleteById(UUID id);
}

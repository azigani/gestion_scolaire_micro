package com.edumanager.enrollment.domain.classroom.repository;

import com.edumanager.enrollment.domain.classroom.entity.Class;
import com.edumanager.enrollment.domain.classroom.valueobject.ClassId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: ClassRepository
 * Defines the contract for class persistence operations.
 */
public interface ClassRepository {

    Class save(Class classEntity);

    Optional<Class> findById(ClassId id);

    List<Class> findByTenantId(UUID tenantId);

    List<Class> findByTenantIdAndAcademicYearId(UUID tenantId, UUID academicYearId);

    void deleteById(ClassId id);
}

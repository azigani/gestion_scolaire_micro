package com.edumanager.enrollment.domain.student.repository;

import com.edumanager.enrollment.domain.student.entity.Student;
import com.edumanager.enrollment.domain.student.enums.StudentStatus;
import com.edumanager.enrollment.domain.student.valueobject.StudentId;
import com.edumanager.enrollment.domain.student.valueobject.StudentNumber;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: StudentRepository
 * Defines the contract for student persistence operations.
 */
public interface StudentRepository {

    Student save(Student student);

    Optional<Student> findById(StudentId id);

    Optional<Student> findByStudentNumber(StudentNumber studentNumber);

    List<Student> findByTenantId(UUID tenantId);

    List<Student> findByTenantIdAndStatus(UUID tenantId, StudentStatus status);

    boolean existsByStudentNumber(StudentNumber studentNumber);

    void deleteById(StudentId id);
}

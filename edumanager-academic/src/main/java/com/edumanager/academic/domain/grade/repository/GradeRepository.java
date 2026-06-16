package com.edumanager.academic.domain.grade.repository;

import com.edumanager.academic.domain.grade.entity.Grade;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: GradeRepository
 * Defines the contract for grade persistence operations.
 */
public interface GradeRepository {

    Grade save(Grade grade);

    Optional<Grade> findById(UUID id);

    List<Grade> findByStudentId(UUID studentId);

    List<Grade> findBySubjectId(UUID subjectId);

    List<Grade> findByClassId(UUID classId);

    List<Grade> findByAcademicYearId(UUID academicYearId);

    List<Grade> findByStudentIdAndSubjectId(UUID studentId, UUID subjectId);

    void deleteById(UUID id);
}

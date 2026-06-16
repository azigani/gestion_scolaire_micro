package com.edumanager.academic.domain.grade.entity;

import com.edumanager.academic.domain.shared.AggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Grade
 * 
 * Represents a grade/score for a student in a subject.
 * Contains business rules for grade management.
 * 
 * Business Rules:
 * - RG-GRD01: Grade cannot exceed max score
 * - RG-GRD02: Grade cannot be negative
 * - RG-GRD03: Grades can only be modified by authorized teachers
 */
public class Grade extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    
    private UUID studentId;
    private UUID subjectId;
    private UUID gradeTypeId;
    private UUID classId;
    private UUID academicYearId;
    
    private BigDecimal score;
    private BigDecimal maxScore;
    private String comments;
    
    private LocalDateTime gradedAt;
    private UUID gradedBy;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Grade(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.studentId = builder.studentId;
        this.subjectId = builder.subjectId;
        this.gradeTypeId = builder.gradeTypeId;
        this.classId = builder.classId;
        this.academicYearId = builder.academicYearId;
        this.score = builder.score;
        this.maxScore = builder.maxScore;
        this.comments = builder.comments;
        this.gradedAt = builder.gradedAt != null ? builder.gradedAt : LocalDateTime.now();
        this.gradedBy = builder.gradedBy;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Update grade score
     */
    public void updateScore(BigDecimal newScore, UUID gradedBy) {
        if (newScore == null) {
            throw new IllegalArgumentException("Score cannot be null");
        }
        if (newScore.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
        if (newScore.compareTo(maxScore) > 0) {
            throw new IllegalArgumentException("Score cannot exceed max score");
        }
        this.score = newScore;
        this.gradedBy = gradedBy;
        this.gradedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update comments
     */
    public void updateComments(String comments) {
        this.comments = comments;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Calculate percentage
     */
    public BigDecimal calculatePercentage() {
        if (maxScore.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return score.multiply(new BigDecimal("100"))
            .divide(maxScore, 2, java.math.RoundingMode.HALF_UP);
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public UUID getStudentId() { return studentId; }
    public UUID getSubjectId() { return subjectId; }
    public UUID getGradeTypeId() { return gradeTypeId; }
    public UUID getClassId() { return classId; }
    public UUID getAcademicYearId() { return academicYearId; }
    public BigDecimal getScore() { return score; }
    public BigDecimal getMaxScore() { return maxScore; }
    public String getComments() { return comments; }
    public LocalDateTime getGradedAt() { return gradedAt; }
    public UUID getGradedBy() { return gradedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private UUID studentId;
        private UUID subjectId;
        private UUID gradeTypeId;
        private UUID classId;
        private UUID academicYearId;
        private BigDecimal score;
        private BigDecimal maxScore;
        private String comments;
        private LocalDateTime gradedAt;
        private UUID gradedBy;

        public Builder(UUID tenantId, UUID studentId, UUID subjectId, UUID gradeTypeId,
                      UUID classId, UUID academicYearId, BigDecimal score, BigDecimal maxScore) {
            this.tenantId = tenantId;
            this.studentId = studentId;
            this.subjectId = subjectId;
            this.gradeTypeId = gradeTypeId;
            this.classId = classId;
            this.academicYearId = academicYearId;
            this.score = score;
            this.maxScore = maxScore;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder comments(String comments) {
            this.comments = comments;
            return this;
        }

        public Builder gradedAt(LocalDateTime gradedAt) {
            this.gradedAt = gradedAt;
            return this;
        }

        public Builder gradedBy(UUID gradedBy) {
            this.gradedBy = gradedBy;
            return this;
        }

        public Grade build() {
            validate();
            return new Grade(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (studentId == null) throw new IllegalArgumentException("studentId is required");
            if (subjectId == null) throw new IllegalArgumentException("subjectId is required");
            if (gradeTypeId == null) throw new IllegalArgumentException("gradeTypeId is required");
            if (classId == null) throw new IllegalArgumentException("classId is required");
            if (academicYearId == null) throw new IllegalArgumentException("academicYearId is required");
            if (score == null) throw new IllegalArgumentException("score is required");
            if (maxScore == null) throw new IllegalArgumentException("maxScore is required");
            if (score.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("score cannot be negative");
            if (score.compareTo(maxScore) > 0) throw new IllegalArgumentException("score cannot exceed max score");
        }
    }
}

package com.edumanager.enrollment.domain.student.entity;

import com.edumanager.enrollment.domain.shared.AggregateRoot;
import com.edumanager.enrollment.domain.student.enums.StudentStatus;
import com.edumanager.enrollment.domain.student.valueobject.StudentId;
import com.edumanager.enrollment.domain.student.valueobject.StudentNumber;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Student
 * 
 * Represents a student enrolled in the school.
 * Contains business rules for student lifecycle.
 * 
 * Business Rules:
 * - RG-S01: Student number must be unique within tenant
 * - RG-S02: Student must have at least one guardian
 * - RG-S03: Student can only be enrolled in one class per academic year
 * - RG-S04: Student status changes are logged
 */
public class Student extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    
    private StudentNumber studentNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationalId;
    private String photoUrl;
    
    private String address;
    private String city;
    private String country;
    
    private LocalDate enrollmentDate;
    private StudentStatus status;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Student(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.studentNumber = builder.studentNumber;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.gender = builder.gender;
        this.nationalId = builder.nationalId;
        this.photoUrl = builder.photoUrl;
        this.address = builder.address;
        this.city = builder.city;
        this.country = builder.country != null ? builder.country : "Burkina Faso";
        this.enrollmentDate = builder.enrollmentDate != null ? builder.enrollmentDate : LocalDate.now();
        this.status = builder.status != null ? builder.status : StudentStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Suspend a student
     */
    public void suspend(String reason) {
        if (this.status != StudentStatus.ACTIVE) {
            throw new IllegalStateException("Only active students can be suspended");
        }
        this.status = StudentStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
        // TODO: Register StudentSuspendedEvent
    }

    /**
     * Reactivate a suspended student
     */
    public void reactivate() {
        if (this.status != StudentStatus.SUSPENDED) {
            throw new IllegalStateException("Only suspended students can be reactivated");
        }
        this.status = StudentStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
        // TODO: Register StudentReactivatedEvent
    }

    /**
     * Withdraw a student
     */
    public void withdraw(String reason) {
        if (this.status == StudentStatus.GRADUATED || this.status == StudentStatus.WITHDRAWN) {
            throw new IllegalStateException("Student is already graduated or withdrawn");
        }
        this.status = StudentStatus.WITHDRAWN;
        this.updatedAt = LocalDateTime.now();
        // TODO: Register StudentWithdrawnEvent
    }

    /**
     * Graduate a student
     */
    public void graduate() {
        if (this.status != StudentStatus.ACTIVE) {
            throw new IllegalStateException("Only active students can graduate");
        }
        this.status = StudentStatus.GRADUATED;
        this.updatedAt = LocalDateTime.now();
        // TODO: Register StudentGraduatedEvent
    }

    /**
     * Transfer a student to another school
     */
    public void transfer(String destinationSchool) {
        if (this.status == StudentStatus.GRADUATED || this.status == StudentStatus.TRANSFERRED) {
            throw new IllegalStateException("Student is already graduated or transferred");
        }
        this.status = StudentStatus.TRANSFERRED;
        this.updatedAt = LocalDateTime.now();
        // TODO: Register StudentTransferredEvent
    }

    /**
     * Update student profile
     */
    public void updateProfile(String firstName, String lastName, String address, String city, String country) {
        if (firstName != null && !firstName.isBlank()) {
            this.firstName = firstName;
        }
        if (lastName != null && !lastName.isBlank()) {
            this.lastName = lastName;
        }
        if (address != null) {
            this.address = address;
        }
        if (city != null) {
            this.city = city;
        }
        if (country != null) {
            this.country = country;
        }
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update photo
     */
    public void updatePhoto(String photoUrl) {
        this.photoUrl = photoUrl;
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public StudentNumber getStudentNumber() { return studentNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getNationalId() { return nationalId; }
    public String getPhotoUrl() { return photoUrl; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public StudentStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private StudentNumber studentNumber;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String gender;
        private String nationalId;
        private String photoUrl;
        private String address;
        private String city;
        private String country;
        private LocalDate enrollmentDate;
        private StudentStatus status;

        public Builder(UUID tenantId, StudentNumber studentNumber, String firstName, 
                      String lastName, LocalDate dateOfBirth, String gender) {
            this.tenantId = tenantId;
            this.studentNumber = studentNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder nationalId(String nationalId) {
            this.nationalId = nationalId;
            return this;
        }

        public Builder photoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder enrollmentDate(LocalDate enrollmentDate) {
            this.enrollmentDate = enrollmentDate;
            return this;
        }

        public Builder status(StudentStatus status) {
            this.status = status;
            return this;
        }

        public Student build() {
            validate();
            return new Student(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (studentNumber == null) throw new IllegalArgumentException("studentNumber is required");
            if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("firstName is required");
            if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("lastName is required");
            if (dateOfBirth == null) throw new IllegalArgumentException("dateOfBirth is required");
            if (gender == null || gender.isBlank()) throw new IllegalArgumentException("gender is required");
        }
    }
}

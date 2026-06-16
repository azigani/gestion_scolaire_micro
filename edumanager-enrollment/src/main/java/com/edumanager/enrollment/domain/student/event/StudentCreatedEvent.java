package com.edumanager.enrollment.domain.student.event;

import com.edumanager.enrollment.domain.shared.DomainEvent;

import java.util.UUID;

/**
 * Domain Event: StudentCreated
 * Published when a new student is enrolled.
 */
public class StudentCreatedEvent extends DomainEvent {

    private final UUID studentId;
    private final UUID tenantId;
    private final String studentNumber;
    private final String firstName;
    private final String lastName;

    public StudentCreatedEvent(UUID studentId, UUID tenantId, String studentNumber, 
                               String firstName, String lastName) {
        super();
        this.studentId = studentId;
        this.tenantId = tenantId;
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

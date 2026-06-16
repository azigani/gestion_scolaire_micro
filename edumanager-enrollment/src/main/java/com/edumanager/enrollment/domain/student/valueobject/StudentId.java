package com.edumanager.enrollment.domain.student.valueobject;

import com.edumanager.enrollment.domain.shared.ValueObject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object: StudentId
 * Represents the unique identifier of a student.
 */
public final class StudentId extends ValueObject {

    private final UUID value;

    public StudentId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("StudentId value cannot be null");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentId)) return false;
        StudentId studentId = (StudentId) o;
        return Objects.equals(value, studentId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

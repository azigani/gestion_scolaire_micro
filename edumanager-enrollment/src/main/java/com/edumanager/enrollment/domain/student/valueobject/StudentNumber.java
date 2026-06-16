package com.edumanager.enrollment.domain.student.valueobject;

import com.edumanager.enrollment.domain.shared.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object: StudentNumber
 * Represents the unique student number within a tenant.
 */
public final class StudentNumber extends ValueObject {

    private static final Pattern STUDENT_NUMBER_PATTERN = Pattern.compile("^[A-Z]{2}\\d{6}$");

    private final String value;

    public StudentNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Student number cannot be empty");
        }
        String trimmed = value.trim().toUpperCase();
        if (!STUDENT_NUMBER_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException(
                "Student number must match pattern: XXNNNNNN (e.g., ST123456)");
        }
        this.value = trimmed;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentNumber)) return false;
        StudentNumber that = (StudentNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

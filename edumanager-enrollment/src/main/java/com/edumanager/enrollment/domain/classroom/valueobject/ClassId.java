package com.edumanager.enrollment.domain.classroom.valueobject;

import com.edumanager.enrollment.domain.shared.ValueObject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object: ClassId
 * Represents the unique identifier of a class.
 */
public final class ClassId extends ValueObject {

    private final UUID value;

    public ClassId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("ClassId value cannot be null");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassId)) return false;
        ClassId classId = (ClassId) o;
        return Objects.equals(value, classId.value);
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

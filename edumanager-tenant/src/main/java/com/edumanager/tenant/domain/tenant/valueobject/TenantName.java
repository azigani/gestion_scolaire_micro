package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.util.Objects;

/**
 * Value Object: TenantName
 * Represents the display name of a tenant (school).
 */
public final class TenantName extends ValueObject {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 200;

    private final String value;

    public TenantName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Tenant name cannot be empty");
        }
        String trimmed = value.trim();
        if (trimmed.length() < MIN_LENGTH || trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Tenant name must be between %d and %d characters", MIN_LENGTH, MAX_LENGTH));
        }
        this.value = trimmed;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TenantName)) return false;
        TenantName that = (TenantName) o;
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

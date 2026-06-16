package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object: TenantSlug
 * Represents a URL-friendly identifier for a tenant.
 * Must be lowercase, alphanumeric with hyphens only.
 */
public final class TenantSlug extends ValueObject {

    private static final Pattern SLUG_PATTERN = Pattern.compile("^[a-z0-9]+(?:-[a-z0-9]+)*$");
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 50;

    private final String value;

    public TenantSlug(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Tenant slug cannot be empty");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Tenant slug must be between %d and %d characters", MIN_LENGTH, MAX_LENGTH));
        }
        if (!SLUG_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(
                "Tenant slug must contain only lowercase letters, numbers, and hyphens");
        }
        this.value = value.toLowerCase().trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TenantSlug)) return false;
        TenantSlug that = (TenantSlug) o;
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

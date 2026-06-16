package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object: Subdomain
 * Represents a subdomain for a tenant (e.g., school.edumanager.com).
 */
public final class Subdomain extends ValueObject {

    private static final Pattern SUBDOMAIN_PATTERN = Pattern.compile("^[a-z0-9-]{1,63}$");

    private final String value;

    public Subdomain(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Subdomain cannot be empty");
        }
        String trimmed = value.trim().toLowerCase();
        if (!SUBDOMAIN_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException(
                "Subdomain must contain only lowercase letters, numbers, and hyphens");
        }
        this.value = trimmed;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subdomain)) return false;
        Subdomain that = (Subdomain) o;
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

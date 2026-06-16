package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object: Domain
 * Represents a custom domain for a tenant.
 */
public final class Domain extends ValueObject {

    private static final Pattern DOMAIN_PATTERN = Pattern.compile(
        "^(?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.[A-Za-z]{2,}$"
    );

    private final String value;

    public Domain(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Domain cannot be empty");
        }
        String trimmed = value.trim().toLowerCase();
        if (!DOMAIN_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Invalid domain format");
        }
        this.value = trimmed;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Domain)) return false;
        Domain domain = (Domain) o;
        return Objects.equals(value, domain.value);
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

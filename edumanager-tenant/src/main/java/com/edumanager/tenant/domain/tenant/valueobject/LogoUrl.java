package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object: LogoUrl
 * Represents the URL of a tenant's logo.
 */
public final class LogoUrl extends ValueObject {

    private static final Pattern URL_PATTERN = Pattern.compile(
        "^https?://[\\w\\-]+(\\.[\\w\\-]+)+[/#?]?.*$"
    );
    private static final int MAX_LENGTH = 500;

    private final String value;

    public LogoUrl(String value) {
        if (value == null || value.isBlank()) {
            this.value = null;
            return;
        }
        String trimmed = value.trim();
        if (trimmed.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Logo URL is too long (max " + MAX_LENGTH + " characters)");
        }
        if (!URL_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("Invalid URL format");
        }
        this.value = trimmed;
    }

    public String getValue() {
        return value;
    }

    public boolean hasValue() {
        return value != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogoUrl)) return false;
        LogoUrl logoUrl = (LogoUrl) o;
        return Objects.equals(value, logoUrl.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object: Theme
 * Represents visual theme configuration for a tenant.
 */
public final class Theme extends ValueObject {

    private static final Pattern COLOR_PATTERN = Pattern.compile("^#[0-9A-Fa-f]{6}$");

    private final String primaryColor;
    private final String secondaryColor;

    public Theme(String primaryColor, String secondaryColor) {
        this.primaryColor = primaryColor != null ? validateColor(primaryColor) : "#1976D2";
        this.secondaryColor = secondaryColor != null ? validateColor(secondaryColor) : "#42A5F5";
    }

    private String validateColor(String color) {
        if (!COLOR_PATTERN.matcher(color).matches()) {
            throw new IllegalArgumentException("Invalid color format. Use hex format: #RRGGBB");
        }
        return color.toUpperCase();
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Theme)) return false;
        Theme theme = (Theme) o;
        return Objects.equals(primaryColor, theme.primaryColor) &&
               Objects.equals(secondaryColor, theme.secondaryColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primaryColor, secondaryColor);
    }
}

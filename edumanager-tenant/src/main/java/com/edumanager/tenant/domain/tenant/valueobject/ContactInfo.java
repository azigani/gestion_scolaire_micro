package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object: ContactInfo
 * Represents contact information for a tenant.
 */
public final class ContactInfo extends ValueObject {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,20}$");

    private final String email;
    private final String phone;
    private final String address;
    private final String city;
    private final String country;

    public ContactInfo(String email, String phone, String address, String city, String country) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        this.email = email.trim().toLowerCase();
        this.phone = phone != null ? phone.trim() : null;
        this.address = address != null ? address.trim() : null;
        this.city = city != null ? city.trim() : null;
        this.country = country != null ? country.trim() : "Burkina Faso";
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactInfo)) return false;
        ContactInfo that = (ContactInfo) o;
        return Objects.equals(email, that.email) &&
               Objects.equals(phone, that.phone) &&
               Objects.equals(address, that.address) &&
               Objects.equals(city, that.city) &&
               Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phone, address, city, country);
    }
}

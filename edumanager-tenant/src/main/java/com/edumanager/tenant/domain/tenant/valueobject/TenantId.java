package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object: TenantId
 * Represents the unique identifier of a tenant.
 */
public final class TenantId extends ValueObject {

    private final UUID value;

    public TenantId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("TenantId value cannot be null");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TenantId)) return false;
        TenantId tenantId = (TenantId) o;
        return Objects.equals(value, tenantId.value);
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

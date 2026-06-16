package com.edumanager.tenant.domain.tenant.event;

import com.edumanager.tenant.domain.shared.DomainEvent;

import java.util.UUID;

/**
 * Domain Event: TenantSuspended
 * Published when a tenant is suspended.
 */
public class TenantSuspendedEvent extends DomainEvent {

    private final UUID tenantId;
    private final String tenantSlug;
    private final String reason;

    public TenantSuspendedEvent(UUID tenantId, String tenantSlug, String reason) {
        super();
        this.tenantId = tenantId;
        this.tenantSlug = tenantSlug;
        this.reason = reason;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public String getTenantSlug() {
        return tenantSlug;
    }

    public String getReason() {
        return reason;
    }
}

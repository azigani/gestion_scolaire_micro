package com.edumanager.tenant.domain.tenant.event;

import com.edumanager.tenant.domain.shared.DomainEvent;

import java.util.UUID;

/**
 * Domain Event: TenantActivated
 * Published when a tenant is activated.
 */
public class TenantActivatedEvent extends DomainEvent {

    private final UUID tenantId;
    private final String tenantSlug;

    public TenantActivatedEvent(UUID tenantId, String tenantSlug) {
        super();
        this.tenantId = tenantId;
        this.tenantSlug = tenantSlug;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public String getTenantSlug() {
        return tenantSlug;
    }
}

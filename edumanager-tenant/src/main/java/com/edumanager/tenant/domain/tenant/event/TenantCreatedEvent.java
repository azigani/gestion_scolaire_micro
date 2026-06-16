package com.edumanager.tenant.domain.tenant.event;

import com.edumanager.tenant.domain.shared.DomainEvent;

import java.util.UUID;

/**
 * Domain Event: TenantCreated
 * Published when a new tenant is created.
 */
public class TenantCreatedEvent extends DomainEvent {

    private final UUID tenantId;
    private final String tenantSlug;

    public TenantCreatedEvent(UUID tenantId, String tenantSlug) {
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

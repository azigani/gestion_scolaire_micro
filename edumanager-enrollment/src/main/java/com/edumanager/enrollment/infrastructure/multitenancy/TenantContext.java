package com.edumanager.enrollment.infrastructure.multitenancy;

import java.util.Optional;
import java.util.UUID;

/**
 * TenantContext - Thread-local storage for tenant information.
 * 
 * This class holds the current tenant context for the duration of a request.
 * It uses ThreadLocal to ensure tenant isolation across concurrent requests.
 */
public class TenantContext {

    private static final ThreadLocal<TenantInfo> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {
        // Private constructor to prevent instantiation
    }

    /**
     * Set the current tenant context
     */
    public static void setTenant(TenantInfo tenantInfo) {
        CURRENT_TENANT.set(tenantInfo);
    }

    /**
     * Get the current tenant ID
     */
    public static Optional<UUID> getTenantId() {
        return Optional.ofNullable(CURRENT_TENANT.get())
            .map(TenantInfo::getTenantId);
    }

    /**
     * Get the current tenant schema name
     */
    public static Optional<String> getSchemaName() {
        return Optional.ofNullable(CURRENT_TENANT.get())
            .map(TenantInfo::getSchemaName);
    }

    /**
     * Get the current tenant info
     */
    public static Optional<TenantInfo> getTenantInfo() {
        return Optional.ofNullable(CURRENT_TENANT.get());
    }

    /**
     * Check if a tenant context is set
     */
    public static boolean hasTenant() {
        return CURRENT_TENANT.get() != null;
    }

    /**
     * Clear the current tenant context
     */
    public static void clear() {
        CURRENT_TENANT.remove();
    }

    /**
     * Tenant information holder
     */
    public static class TenantInfo {
        private final UUID tenantId;
        private final String schemaName;

        public TenantInfo(UUID tenantId, String schemaName) {
            this.tenantId = tenantId;
            this.schemaName = schemaName;
        }

        public UUID getTenantId() {
            return tenantId;
        }

        public String getSchemaName() {
            return schemaName;
        }
    }
}

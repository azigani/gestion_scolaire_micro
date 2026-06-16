package com.edumanager.tenant.infrastructure.multitenancy;

import java.util.Optional;
import java.util.UUID;

/**
 * TenantContext - Thread-local storage for tenant information.
 * 
 * This class holds the current tenant context for the duration of a request.
 * It uses ThreadLocal to ensure tenant isolation across concurrent requests.
 * 
 * IMPORTANT: Always clear the context after request processing to prevent memory leaks.
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
     * Get the current tenant slug
     */
    public static Optional<String> getTenantSlug() {
        return Optional.ofNullable(CURRENT_TENANT.get())
            .map(TenantInfo::getTenantSlug);
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
     * IMPORTANT: Call this after request processing
     */
    public static void clear() {
        CURRENT_TENANT.remove();
    }

    /**
     * Tenant information holder
     */
    public static class TenantInfo {
        private final UUID tenantId;
        private final String tenantSlug;
        private final String schemaName;

        public TenantInfo(UUID tenantId, String tenantSlug, String schemaName) {
            this.tenantId = tenantId;
            this.tenantSlug = tenantSlug;
            this.schemaName = schemaName;
        }

        public UUID getTenantId() {
            return tenantId;
        }

        public String getTenantSlug() {
            return tenantSlug;
        }

        public String getSchemaName() {
            return schemaName;
        }
    }
}

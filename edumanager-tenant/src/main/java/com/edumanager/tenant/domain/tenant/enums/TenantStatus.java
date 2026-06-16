package com.edumanager.tenant.domain.tenant.enums;

/**
 * Enum: TenantStatus
 * Represents the lifecycle status of a tenant.
 */
public enum TenantStatus {
    /**
     * Tenant has been created but not yet activated
     */
    PENDING,
    
    /**
     * Tenant is active and fully operational
     */
    ACTIVE,
    
    /**
     * Tenant has been temporarily suspended (e.g., for payment issues)
     */
    SUSPENDED,
    
    /**
     * Tenant has been permanently terminated (cannot be reactivated)
     */
    TERMINATED
}

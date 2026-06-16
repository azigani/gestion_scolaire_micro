package com.edumanager.tenant.domain.tenant.enums;

/**
 * Enum: UserRole
 * Represents the role of a user within a tenant.
 */
public enum UserRole {
    /**
     * Tenant administrator - full access to tenant configuration
     */
    TENANT_ADMIN,
    
    /**
     * Regular tenant user - limited access based on permissions
     */
    TENANT_USER
}

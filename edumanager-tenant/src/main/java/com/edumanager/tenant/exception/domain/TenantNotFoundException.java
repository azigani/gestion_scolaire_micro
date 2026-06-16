package com.edumanager.tenant.exception.domain;

import com.edumanager.tenant.constants.ErrorCodes;

/**
 * Exception thrown when a tenant is not found.
 */
public class TenantNotFoundException extends BusinessException {

    public TenantNotFoundException(String message) {
        super(ErrorCodes.TENANT_NOT_FOUND, message);
    }

    public TenantNotFoundException(String message, Throwable cause) {
        super(ErrorCodes.TENANT_NOT_FOUND, message, cause);
    }
}

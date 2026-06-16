package com.edumanager.tenant.constants;

/**
 * Centralized error codes for the tenant service.
 * All error codes follow the pattern: TNN_XXX where NN is the module and XXX is the error number.
 */
public final class ErrorCodes {
    private ErrorCodes() {}

    // Tenant errors (TEN_XXX)
    public static final String TENANT_NOT_FOUND = "TEN_001";
    public static final String TENANT_NOT_PENDING = "TEN_002";
    public static final String TENANT_NOT_ACTIVE = "TEN_003";
    public static final String TENANT_NOT_SUSPENDED = "TEN_004";
    public static final String TENANT_ALREADY_TERMINATED = "TEN_005";
    public static final String TENANT_TERMINATED = "TEN_006";
    public static final String TENANT_SLUG_DUPLICATE = "TEN_007";
    public static final String TENANT_DOMAIN_DUPLICATE = "TEN_008";
    public static final String TENANT_SUBDOMAIN_DUPLICATE = "TEN_009";
    public static final String SUSPENSION_REASON_REQUIRED = "TEN_010";
    public static final String TERMINATION_REASON_REQUIRED = "TEN_011";
    public static final String INVALID_SUBSCRIPTION_DATE = "TEN_012";
    public static final String INVALID_PLAN = "TEN_013";
    public static final String CONTACT_INFO_REQUIRED = "TEN_014";

    // Schema errors (SCH_XXX)
    public static final String SCHEMA_CREATION_FAILED = "SCH_001";
    public static final String SCHEMA_ALREADY_EXISTS = "SCH_002";
    public static final String SCHEMA_NOT_FOUND = "SCH_003";

    // Configuration errors (CFG_XXX)
    public static final String CONFIG_NOT_FOUND = "CFG_001";
    public static final String CONFIG_KEY_INVALID = "CFG_002";

    // User errors (USR_XXX)
    public static final String USER_NOT_FOUND = "USR_001";
    public static final String USER_EMAIL_DUPLICATE = "USR_002";
    public static final String USER_USERNAME_DUPLICATE = "USR_003";
    public static final String USER_INVALID_CREDENTIALS = "USR_004";
    public static final String USER_ACCOUNT_LOCKED = "USR_005";
    public static final String USER_PASSWORD_EXPIRED = "USR_006";

    // Invitation errors (INV_XXX)
    public static final String INVITATION_NOT_FOUND = "INV_001";
    public static final String INVITATION_EXPIRED = "INV_002";
    public static final String INVITATION_ALREADY_ACCEPTED = "INV_003";

    // Validation errors (VAL_XXX)
    public static final String VALIDATION_ERROR = "VAL_001";
    public static final String INVALID_EMAIL_FORMAT = "VAL_002";
    public static final String INVALID_PHONE_FORMAT = "VAL_003";
    public static final String INVALID_SLUG_FORMAT = "VAL_004";
    public static final String INVALID_DOMAIN_FORMAT = "VAL_005";
}

package com.edumanager.communication.constants;

/**
 * Centralized error codes for the communication service.
 */
public final class ErrorCodes {
    private ErrorCodes() {}

    // Notification errors (NOT_XXX)
    public static final String NOTIFICATION_NOT_FOUND = "NOT_001";
    public static final String NOTIFICATION_INVALID_RECIPIENT = "NOT_002";
    public static final String NOTIFICATION_INVALID_CHANNEL = "NOT_003";
    public static final String NOTIFICATION_INVALID_STATUS_TRANSITION = "NOT_004";
    public static final String NOTIFICATION_SEND_FAILED = "NOT_005";

    // Announcement errors (ANN_XXX)
    public static final String ANNOUNCEMENT_NOT_FOUND = "ANN_001";
    public static final String ANNOUNCEMENT_INVALID_DATE_RANGE = "ANN_002";
    public static final String ANNOUNCEMENT_ALREADY_PUBLISHED = "ANN_003";
    public static final String ANNOUNCEMENT_CANNOT_MODIFY_PUBLISHED = "ANN_004";

    // Template errors (TPL_XXX)
    public static final String TEMPLATE_NOT_FOUND = "TPL_001";
    public static final String TEMPLATE_CODE_DUPLICATE = "TPL_002";
    public static final String TEMPLATE_IN_USE = "TPL_003";

    // Validation errors (VAL_XXX)
    public static final String VALIDATION_ERROR = "VAL_001";
}

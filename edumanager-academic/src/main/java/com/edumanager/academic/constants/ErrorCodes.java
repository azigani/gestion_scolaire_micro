package com.edumanager.academic.constants;

/**
 * Centralized error codes for the academic service.
 */
public final class ErrorCodes {
    private ErrorCodes() {}

    // Subject errors (SUB_XXX)
    public static final String SUBJECT_NOT_FOUND = "SUB_001";
    public static final String SUBJECT_CODE_DUPLICATE = "SUB_002";
    public static final String SUBJECT_IN_USE = "SUB_003";

    // Grade errors (GRD_XXX)
    public static final String GRADE_NOT_FOUND = "GRD_001";
    public static final String GRADE_INVALID_SCORE = "GRD_002";
    public static final String GRADE_EXCEEDS_MAX = "GRD_003";
    public static final String GRADE_TYPE_NOT_FOUND = "GRD_004";

    // Schedule errors (SCH_XXX)
    public static final String SCHEDULE_CONFLICT = "SCH_001";
    public static final String SCHEDULE_NOT_FOUND = "SCH_002";

    // Report card errors (RPT_XXX)
    public static final String REPORT_CARD_NOT_FOUND = "RPT_001";
    public static final String REPORT_CARD_ALREADY_GENERATED = "RPT_002";

    // Validation errors (VAL_XXX)
    public static final String VALIDATION_ERROR = "VAL_001";
}

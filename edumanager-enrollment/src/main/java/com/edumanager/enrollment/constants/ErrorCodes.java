package com.edumanager.enrollment.constants;

/**
 * Centralized error codes for the enrollment service.
 */
public final class ErrorCodes {
    private ErrorCodes() {}

    // Student errors (STU_XXX)
    public static final String STUDENT_NOT_FOUND = "STU_001";
    public static final String STUDENT_NUMBER_DUPLICATE = "STU_002";
    public static final String STUDENT_ALREADY_ENROLLED = "STU_003";
    public static final String STUDENT_NOT_ACTIVE = "STU_004";
    public static final String STUDENT_CANNOT_SUSPEND = "STU_005";
    public static final String STUDENT_CANNOT_WITHDRAW = "STU_006";

    // Class errors (CLS_XXX)
    public static final String CLASS_NOT_FOUND = "CLS_001";
    public static final String CLASS_NAME_DUPLICATE = "CLS_002";
    public static final String CLASS_CAPACITY_EXCEEDED = "CLS_003";
    public static final String CLASS_FULL = "CLS_004";

    // Academic Year errors (ACY_XXX)
    public static final String ACADEMIC_YEAR_NOT_FOUND = "ACY_001";
    public static final String ACADEMIC_YEAR_DUPLICATE = "ACY_002";
    public static final String ACADEMIC_YEAR_CONFLICT = "ACY_003";

    // Guardian errors (GRD_XXX)
    public static final String GUARDIAN_NOT_FOUND = "GRD_001";
    public static final String GUARDIAN_EMAIL_DUPLICATE = "GRD_002";

    // Validation errors (VAL_XXX)
    public static final String VALIDATION_ERROR = "VAL_001";
    public static final String INVALID_DATE_FORMAT = "VAL_002";
    public static final String INVALID_EMAIL_FORMAT = "VAL_003";
}

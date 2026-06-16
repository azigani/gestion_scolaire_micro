package com.edumanager.hr.constants;

/**
 * Centralized error codes for the HR service.
 */
public final class ErrorCodes {
    private ErrorCodes() {}

    // Employee errors (EMP_XXX)
    public static final String EMPLOYEE_NOT_FOUND = "EMP_001";
    public static final String EMPLOYEE_NUMBER_DUPLICATE = "EMP_002";
    public static final String EMPLOYEE_INVALID_HIRE_DATE = "EMP_003";
    public static final String EMPLOYEE_INVALID_TERMINATION_DATE = "EMP_004";
    public static final String EMPLOYEE_INVALID_STATUS_TRANSITION = "EMP_005";

    // Attendance errors (ATT_XXX)
    public static final String ATTENDANCE_NOT_FOUND = "ATT_001";
    public static final String ATTENDANCE_ALREADY_CHECKED_IN = "ATT_002";
    public static final String ATTENDANCE_ALREADY_CHECKED_OUT = "ATT_003";
    public static final String ATTENDANCE_INVALID_CHECKOUT_TIME = "ATT_004";

    // Leave errors (LEA_XXX)
    public static final String LEAVE_REQUEST_NOT_FOUND = "LEA_001";
    public static final String LEAVE_INVALID_DATE_RANGE = "LEA_002";
    public static final String LEAVE_INSUFFICIENT_BALANCE = "LEA_003";

    // Validation errors (VAL_XXX)
    public static final String VALIDATION_ERROR = "VAL_001";
}

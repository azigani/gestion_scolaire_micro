package com.edumanager.financial.constants;

/**
 * Centralized error codes for the financial service.
 */
public final class ErrorCodes {
    private ErrorCodes() {}

    // Invoice errors (INV_XXX)
    public static final String INVOICE_NOT_FOUND = "INV_001";
    public static final String INVOICE_NUMBER_DUPLICATE = "INV_002";
    public static final String INVOICE_ALREADY_PAID = "INV_003";
    public static final String INVOICE_CANNOT_CANCEL = "INV_004";
    public static final String INVOICE_INVALID_AMOUNT = "INV_005";

    // Payment errors (PAY_XXX)
    public static final String PAYMENT_NOT_FOUND = "PAY_001";
    public static final String PAYMENT_NUMBER_DUPLICATE = "PAY_002";
    public static final String PAYMENT_EXCEEDS_BALANCE = "PAY_003";
    public static final String PAYMENT_INVALID_STATUS = "PAY_004";

    // Fee type errors (FEE_XXX)
    public static final String FEE_TYPE_NOT_FOUND = "FEE_001";
    public static final String FEE_TYPE_CODE_DUPLICATE = "FEE_002";
    public static final String FEE_TYPE_IN_USE = "FEE_003";

    // Validation errors (VAL_XXX)
    public static final String VALIDATION_ERROR = "VAL_001";
}

package com.edumanager.financial.presentation.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Standard API response format for all endpoints.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
    boolean success,
    String code,
    String message,
    T data,
    List<ErrorDetail> errors,
    Instant timestamp
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "SUCCESS", null, data, null, Instant.now());
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, "SUCCESS", message, data, null, Instant.now());
    }

    public static <T> ApiResponse<T> error(String code, String message, List<ErrorDetail> errors) {
        return new ApiResponse<>(false, code, message, null, errors, Instant.now());
    }

    public record ErrorDetail(
        String field,
        String message,
        Object rejectedValue
    ) {}
}

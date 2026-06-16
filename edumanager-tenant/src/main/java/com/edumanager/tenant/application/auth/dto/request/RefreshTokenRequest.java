package com.edumanager.tenant.application.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for refresh token request.
 */
public record RefreshTokenRequest(

    @NotBlank(message = "Refresh token is required")
    String refreshToken

) {}

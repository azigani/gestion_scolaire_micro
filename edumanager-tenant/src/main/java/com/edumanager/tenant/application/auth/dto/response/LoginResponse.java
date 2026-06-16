package com.edumanager.tenant.application.auth.dto.response;

/**
 * DTO for login response.
 */
public record LoginResponse(

    String accessToken,
    String refreshToken,
    String tokenType,
    Long expiresIn,
    UserResponse user

) {

    public record UserResponse(
        String id,
        String email,
        String firstName,
        String lastName,
        String role,
        String tenantId,
        String tenantSlug,
        boolean mustChangePassword
    ) {}
}

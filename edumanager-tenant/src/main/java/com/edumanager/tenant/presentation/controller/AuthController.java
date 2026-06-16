package com.edumanager.tenant.presentation.controller;

import com.edumanager.tenant.application.auth.dto.request.ChangePasswordRequest;
import com.edumanager.tenant.application.auth.dto.request.LoginRequest;
import com.edumanager.tenant.application.auth.dto.request.RefreshTokenRequest;
import com.edumanager.tenant.application.auth.dto.response.LoginResponse;
import com.edumanager.tenant.application.auth.usecase.ChangePasswordUseCase;
import com.edumanager.tenant.application.auth.usecase.LoginUseCase;
import com.edumanager.tenant.application.auth.usecase.RefreshTokenUseCase;
import com.edumanager.tenant.presentation.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for Authentication.
 */
@Tag(name = "Authentication", description = "Authentication endpoints")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @Operation(summary = "Login to the platform")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {
        
        LoginResponse response = loginUseCase.execute(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }

    @Operation(summary = "Refresh access token")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        
        LoginResponse response = refreshTokenUseCase.execute(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Token refreshed"));
    }

    @Operation(summary = "Change password")
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody ChangePasswordRequest request) {
        
        changePasswordUseCase.execute(userId, request);
        return ResponseEntity.ok(ApiResponse.success(null, "Password changed successfully"));
    }
}

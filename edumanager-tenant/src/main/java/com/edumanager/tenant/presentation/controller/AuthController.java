package com.edumanager.tenant.presentation.controller;

import com.edumanager.tenant.application.auth.dto.request.LoginRequest;
import com.edumanager.tenant.application.auth.dto.request.RefreshTokenRequest;
import com.edumanager.tenant.application.auth.dto.response.LoginResponse;
import com.edumanager.tenant.application.auth.usecase.LoginUseCase;
import com.edumanager.tenant.presentation.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Authentication.
 */
@Tag(name = "Authentication", description = "Authentication endpoints")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;

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
        
        // TODO: Implement refresh token logic
        return ResponseEntity.ok(ApiResponse.success(null, "Token refreshed"));
    }
}

package com.edumanager.tenant.application.auth.usecase;

import com.edumanager.tenant.application.auth.dto.request.RefreshTokenRequest;
import com.edumanager.tenant.application.auth.dto.response.LoginResponse;
import com.edumanager.tenant.application.auth.mapper.AuthMapper;
import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.entity.TenantUser;
import com.edumanager.tenant.domain.tenant.repository.TenantRepository;
import com.edumanager.tenant.domain.tenant.repository.TenantUserRepository;
import com.edumanager.tenant.exception.domain.BusinessException;
import com.edumanager.tenant.constants.ErrorCodes;
import com.edumanager.tenant.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use Case: Refresh Token
 * 
 * Orchestrates the token refresh process:
 * - Validates refresh token
 * - Extracts user and tenant information
 * - Generates new access token
 * - Generates new refresh token (rotation)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final TenantRepository tenantRepository;
    private final TenantUserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

    @Transactional
    public LoginResponse execute(RefreshTokenRequest request) {
        log.info("Token refresh requested");

        // 1. Validate refresh token
        if (!jwtUtil.validateToken(request.refreshToken())) {
            throw new BusinessException(ErrorCodes.USER_INVALID_CREDENTIALS, "Invalid refresh token");
        }

        // 2. Extract information from token
        UUID userId = jwtUtil.extractUserId(request.refreshToken());
        UUID tenantId = jwtUtil.extractTenantId(request.refreshToken());

        // 3. Find user and tenant
        TenantUser user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, "User not found"));

        Tenant tenant = tenantRepository.findById(user.getTenantId())
            .orElseThrow(() -> new BusinessException(ErrorCodes.TENANT_NOT_FOUND, "Tenant not found"));

        // 4. Check if user is still active
        if (!user.isActive()) {
            throw new BusinessException(ErrorCodes.USER_ACCOUNT_LOCKED, "Account is deactivated");
        }

        // 5. Generate new tokens
        String accessToken = jwtUtil.generateToken(user.getId(), tenant.getId().getValue(), 
            user.getEmail(), user.getRole().name());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getId(), tenant.getId().getValue());

        log.info("Token refreshed successfully for user: {}", user.getEmail());

        return new LoginResponse(
            accessToken,
            newRefreshToken,
            "Bearer",
            3600L,
            authMapper.toUserResponse(user, tenant)
        );
    }
}

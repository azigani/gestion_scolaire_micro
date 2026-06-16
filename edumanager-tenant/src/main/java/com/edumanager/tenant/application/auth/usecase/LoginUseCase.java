package com.edumanager.tenant.application.auth.usecase;

import com.edumanager.tenant.application.auth.dto.request.LoginRequest;
import com.edumanager.tenant.application.auth.dto.response.LoginResponse;
import com.edumanager.tenant.application.auth.mapper.AuthMapper;
import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.entity.TenantUser;
import com.edumanager.tenant.domain.tenant.repository.TenantRepository;
import com.edumanager.tenant.domain.tenant.repository.TenantUserRepository;
import com.edumanager.tenant.domain.tenant.valueobject.TenantSlug;
import com.edumanager.tenant.exception.domain.BusinessException;
import com.edumanager.tenant.exception.domain.TenantNotFoundException;
import com.edumanager.tenant.constants.ErrorCodes;
import com.edumanager.tenant.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use Case: Login
 * 
 * Orchestrates the login process:
 * - Validates tenant exists and is active
 * - Validates user credentials
 * - Checks account status (locked, expired password)
 * - Generates JWT tokens
 * - Records successful login
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final TenantRepository tenantRepository;
    private final TenantUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

    @Transactional
    public LoginResponse execute(LoginRequest request) {
        log.info("Login attempt for email: {} in tenant: {}", request.email(), request.tenantSlug());

        // 1. Validate tenant
        TenantSlug tenantSlug = new TenantSlug(request.tenantSlug());
        Tenant tenant = tenantRepository.findBySlug(tenantSlug)
            .orElseThrow(() -> new TenantNotFoundException("Tenant not found: " + request.tenantSlug()));

        if (!tenant.isActive()) {
            throw new BusinessException(ErrorCodes.TENANT_NOT_ACTIVE,
                "Tenant is not active. Status: " + tenant.getStatus());
        }

        // 2. Find user
        TenantUser user = userRepository.findByTenantIdAndEmail(tenant.getId(), request.email())
            .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND,
                "User not found: " + request.email()));

        // 3. Check account status
        if (!user.isActive()) {
            throw new BusinessException(ErrorCodes.USER_ACCOUNT_LOCKED, "Account is deactivated");
        }

        if (user.isAccountLocked()) {
            throw new BusinessException(ErrorCodes.USER_ACCOUNT_LOCKED, 
                "Account is locked. Try again later");
        }

        if (user.isPasswordExpired()) {
            throw new BusinessException(ErrorCodes.USER_PASSWORD_EXPIRED, 
                "Password has expired. Please change your password");
        }

        // 4. Validate password
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            user.recordFailedLogin();
            userRepository.save(user);
            throw new BusinessException(ErrorCodes.USER_INVALID_CREDENTIALS, "Invalid credentials");
        }

        // 5. Record successful login
        user.recordLogin();
        userRepository.save(user);

        // 6. Generate tokens
        String accessToken = jwtUtil.generateToken(user.getId(), tenant.getId().getValue(), 
            user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId(), tenant.getId().getValue());

        log.info("Login successful for user: {}", user.getEmail());

        return new LoginResponse(
            accessToken,
            refreshToken,
            "Bearer",
            3600L, // 1 hour
            authMapper.toUserResponse(user, tenant)
        );
    }
}

package com.edumanager.tenant.application.auth.usecase;

import com.edumanager.tenant.application.auth.dto.request.ChangePasswordRequest;
import com.edumanager.tenant.domain.tenant.entity.TenantUser;
import com.edumanager.tenant.domain.tenant.repository.TenantUserRepository;
import com.edumanager.tenant.exception.domain.BusinessException;
import com.edumanager.tenant.constants.ErrorCodes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use Case: Change Password
 * 
 * Orchestrates the password change process:
 * - Validates current password
 * - Validates new password complexity
 * - Updates password hash
 * - Resets password expiration
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final TenantUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(UUID userId, ChangePasswordRequest request) {
        log.info("Password change requested for user: {}", userId);

        // 1. Find user
        TenantUser user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND, "User not found"));

        // 2. Validate current password
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCodes.USER_INVALID_CREDENTIALS, "Current password is incorrect");
        }

        // 3. Validate new password is different
        if (passwordEncoder.matches(request.newPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCodes.VALIDATION_ERROR, 
                "New password must be different from current password");
        }

        // 4. Update password
        String newPasswordHash = passwordEncoder.encode(request.newPassword());
        user.changePassword(newPasswordHash);

        // 5. Save
        userRepository.save(user);

        log.info("Password changed successfully for user: {}", user.getEmail());
    }
}

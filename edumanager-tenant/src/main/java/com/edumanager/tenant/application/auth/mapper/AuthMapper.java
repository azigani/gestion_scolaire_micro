package com.edumanager.tenant.application.auth.mapper;

import com.edumanager.tenant.application.auth.dto.response.LoginResponse.UserResponse;
import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.entity.TenantUser;
import org.springframework.stereotype.Component;

/**
 * Mapper for authentication-related DTOs.
 */
@Component
public class AuthMapper {

    public UserResponse toUserResponse(TenantUser user, Tenant tenant) {
        return new UserResponse(
            user.getId().toString(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getRole().name(),
            user.getTenantId().getValue().toString(),
            tenant.getSlug().getValue(),
            user.mustChangePassword()
        );
    }
}

package com.edumanager.tenant.infrastructure.persistence.mapper;

import com.edumanager.tenant.domain.tenant.entity.TenantUser;
import com.edumanager.tenant.domain.tenant.enums.UserRole;
import com.edumanager.tenant.domain.tenant.valueobject.TenantId;
import com.edumanager.tenant.infrastructure.persistence.entity.TenantUserJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between Domain Entity and JPA Entity for TenantUser.
 */
@Component
public class TenantUserPersistenceMapper {

    public TenantUserJpaEntity toJpaEntity(TenantUser user) {
        return TenantUserJpaEntity.builder()
            .id(user.getId())
            .tenantId(user.getTenantId().getValue())
            .username(user.getUsername())
            .email(user.getEmail())
            .passwordHash(user.getPasswordHash())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .phone(user.getPhone())
            .role(user.getRole())
            .isActive(user.isActive())
            .isLocked(user.isLocked())
            .failedAttempts(user.getFailedAttempts())
            .lockedUntil(user.getLockedUntil())
            .passwordExpiresAt(user.getPasswordExpiresAt())
            .lastLogin(user.getLastLogin())
            .mustChangePassword(user.mustChangePassword())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .createdBy(user.getId()) // Simplified - should be actual creator
            .updatedBy(user.getId()) // Simplified
            .build();
    }

    public TenantUser toDomainEntity(TenantUserJpaEntity jpaEntity) {
        return new TenantUser.Builder(
            new TenantId(jpaEntity.getTenantId()),
            jpaEntity.getUsername(),
            jpaEntity.getEmail(),
            jpaEntity.getPasswordHash()
        )
        .id(jpaEntity.getId())
        .firstName(jpaEntity.getFirstName())
        .lastName(jpaEntity.getLastName())
        .phone(jpaEntity.getPhone())
        .role(jpaEntity.getRole())
        .mustChangePassword(jpaEntity.getMustChangePassword())
        .build();
    }
}

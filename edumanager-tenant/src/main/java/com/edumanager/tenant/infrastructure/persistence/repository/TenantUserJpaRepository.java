package com.edumanager.tenant.infrastructure.persistence.repository;

import com.edumanager.tenant.domain.tenant.enums.UserRole;
import com.edumanager.tenant.infrastructure.persistence.entity.TenantUserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for TenantUserJpaEntity.
 */
@Repository
public interface TenantUserJpaRepository extends JpaRepository<TenantUserJpaEntity, UUID> {

    Optional<TenantUserJpaEntity> findByTenantIdAndEmail(UUID tenantId, String email);

    Optional<TenantUserJpaEntity> findByTenantIdAndUsername(UUID tenantId, String username);

    boolean existsByTenantIdAndEmail(UUID tenantId, String email);

    boolean existsByTenantIdAndUsername(UUID tenantId, String username);

    Optional<TenantUserJpaEntity> findByEmail(String email);
}

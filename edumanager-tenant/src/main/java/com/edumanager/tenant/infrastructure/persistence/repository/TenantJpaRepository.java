package com.edumanager.tenant.infrastructure.persistence.repository;

import com.edumanager.tenant.domain.tenant.enums.TenantStatus;
import com.edumanager.tenant.infrastructure.persistence.entity.TenantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for TenantJpaEntity.
 */
@Repository
public interface TenantJpaRepository extends JpaRepository<TenantJpaEntity, UUID> {

    Optional<TenantJpaEntity> findBySlug(String slug);

    Optional<TenantJpaEntity> findByDomain(String domain);

    Optional<TenantJpaEntity> findBySubdomain(String subdomain);

    boolean existsBySlug(String slug);

    boolean existsByDomain(String domain);

    boolean existsBySubdomain(String subdomain);

    boolean existsByDbSchemaName(String schemaName);
}

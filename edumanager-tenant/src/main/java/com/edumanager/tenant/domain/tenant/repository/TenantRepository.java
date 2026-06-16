package com.edumanager.tenant.domain.tenant.repository;

import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.enums.TenantStatus;
import com.edumanager.tenant.domain.tenant.valueobject.TenantId;
import com.edumanager.tenant.domain.tenant.valueobject.TenantSlug;
import com.edumanager.tenant.domain.tenant.valueobject.Domain;
import com.edumanager.tenant.domain.tenant.valueobject.Subdomain;

import java.util.Optional;

/**
 * Repository Interface: TenantRepository
 * Defines the contract for tenant persistence operations.
 */
public interface TenantRepository {

    Tenant save(Tenant tenant);

    Optional<Tenant> findById(TenantId id);

    Optional<Tenant> findBySlug(TenantSlug slug);

    Optional<Tenant> findByDomain(Domain domain);

    Optional<Tenant> findBySubdomain(Subdomain subdomain);

    boolean existsBySlug(TenantSlug slug);

    boolean existsByDomain(Domain domain);

    boolean existsBySubdomain(Subdomain subdomain);

    void deleteById(TenantId id);
}

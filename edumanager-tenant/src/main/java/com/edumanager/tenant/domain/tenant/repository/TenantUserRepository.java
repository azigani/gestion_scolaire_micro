package com.edumanager.tenant.domain.tenant.repository;

import com.edumanager.tenant.domain.tenant.entity.TenantUser;
import com.edumanager.tenant.domain.tenant.valueobject.TenantId;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: TenantUserRepository
 * Defines the contract for tenant user persistence operations.
 */
public interface TenantUserRepository {

    TenantUser save(TenantUser user);

    Optional<TenantUser> findById(UUID id);

    Optional<TenantUser> findByTenantIdAndEmail(TenantId tenantId, String email);

    Optional<TenantUser> findByTenantIdAndUsername(TenantId tenantId, String username);

    boolean existsByTenantIdAndEmail(TenantId tenantId, String email);

    boolean existsByTenantIdAndUsername(TenantId tenantId, String username);

    void deleteById(UUID id);
}

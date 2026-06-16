package com.edumanager.tenant.infrastructure.persistence.repositoryimpl;

import com.edumanager.tenant.domain.tenant.entity.TenantUser;
import com.edumanager.tenant.domain.tenant.repository.TenantUserRepository;
import com.edumanager.tenant.domain.tenant.valueobject.TenantId;
import com.edumanager.tenant.infrastructure.persistence.entity.TenantUserJpaEntity;
import com.edumanager.tenant.infrastructure.persistence.mapper.TenantUserPersistenceMapper;
import com.edumanager.tenant.infrastructure.persistence.repository.TenantUserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of TenantUserRepository using JPA.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TenantUserRepositoryImpl implements TenantUserRepository {

    private final TenantUserJpaRepository jpaRepository;
    private final TenantUserPersistenceMapper mapper;

    @Override
    public TenantUser save(TenantUser user) {
        log.debug("Saving user: {}", user.getEmail());
        TenantUserJpaEntity jpaEntity = mapper.toJpaEntity(user);
        TenantUserJpaEntity saved = jpaRepository.save(jpaEntity);
        return mapper.toDomainEntity(saved);
    }

    @Override
    public Optional<TenantUser> findById(UUID id) {
        log.debug("Finding user by id: {}", id);
        return jpaRepository.findById(id)
            .map(mapper::toDomainEntity);
    }

    @Override
    public Optional<TenantUser> findByTenantIdAndEmail(TenantId tenantId, String email) {
        log.debug("Finding user by tenantId and email: {}", email);
        return jpaRepository.findByTenantIdAndEmail(tenantId.getValue(), email)
            .map(mapper::toDomainEntity);
    }

    @Override
    public Optional<TenantUser> findByTenantIdAndUsername(TenantId tenantId, String username) {
        log.debug("Finding user by tenantId and username: {}", username);
        return jpaRepository.findByTenantIdAndUsername(tenantId.getValue(), username)
            .map(mapper::toDomainEntity);
    }

    @Override
    public boolean existsByTenantIdAndEmail(TenantId tenantId, String email) {
        return jpaRepository.existsByTenantIdAndEmail(tenantId.getValue(), email);
    }

    @Override
    public boolean existsByTenantIdAndUsername(TenantId tenantId, String username) {
        return jpaRepository.existsByTenantIdAndUsername(tenantId.getValue(), username);
    }

    @Override
    public void deleteById(UUID id) {
        log.debug("Deleting user by id: {}", id);
        jpaRepository.deleteById(id);
    }
}

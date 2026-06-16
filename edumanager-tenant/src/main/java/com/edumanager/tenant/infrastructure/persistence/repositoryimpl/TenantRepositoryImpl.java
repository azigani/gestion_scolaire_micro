package com.edumanager.tenant.infrastructure.persistence.repositoryimpl;

import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.repository.TenantRepository;
import com.edumanager.tenant.domain.tenant.valueobject.Domain;
import com.edumanager.tenant.domain.tenant.valueobject.Subdomain;
import com.edumanager.tenant.domain.tenant.valueobject.TenantId;
import com.edumanager.tenant.domain.tenant.valueobject.TenantSlug;
import com.edumanager.tenant.infrastructure.persistence.entity.TenantJpaEntity;
import com.edumanager.tenant.infrastructure.persistence.mapper.TenantPersistenceMapper;
import com.edumanager.tenant.infrastructure.persistence.repository.TenantJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementation of TenantRepository using JPA.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TenantRepositoryImpl implements TenantRepository {

    private final TenantJpaRepository jpaRepository;
    private final TenantPersistenceMapper mapper;

    @Override
    public Tenant save(Tenant tenant) {
        log.debug("Saving tenant: {}", tenant.getSlug().getValue());
        TenantJpaEntity jpaEntity = mapper.toJpaEntity(tenant);
        TenantJpaEntity saved = jpaRepository.save(jpaEntity);
        return mapper.toDomainEntity(saved);
    }

    @Override
    public Optional<Tenant> findById(TenantId id) {
        log.debug("Finding tenant by id: {}", id.getValue());
        return jpaRepository.findById(id.getValue())
            .map(mapper::toDomainEntity);
    }

    @Override
    public Optional<Tenant> findBySlug(TenantSlug slug) {
        log.debug("Finding tenant by slug: {}", slug.getValue());
        return jpaRepository.findBySlug(slug.getValue())
            .map(mapper::toDomainEntity);
    }

    @Override
    public Optional<Tenant> findByDomain(Domain domain) {
        log.debug("Finding tenant by domain: {}", domain.getValue());
        return jpaRepository.findByDomain(domain.getValue())
            .map(mapper::toDomainEntity);
    }

    @Override
    public Optional<Tenant> findBySubdomain(Subdomain subdomain) {
        log.debug("Finding tenant by subdomain: {}", subdomain.getValue());
        return jpaRepository.findBySubdomain(subdomain.getValue())
            .map(mapper::toDomainEntity);
    }

    @Override
    public boolean existsBySlug(TenantSlug slug) {
        return jpaRepository.existsBySlug(slug.getValue());
    }

    @Override
    public boolean existsByDomain(Domain domain) {
        return jpaRepository.existsByDomain(domain.getValue());
    }

    @Override
    public boolean existsBySubdomain(Subdomain subdomain) {
        return jpaRepository.existsBySubdomain(subdomain.getValue());
    }

    @Override
    public void deleteById(TenantId id) {
        log.debug("Deleting tenant by id: {}", id.getValue());
        jpaRepository.deleteById(id.getValue());
    }
}

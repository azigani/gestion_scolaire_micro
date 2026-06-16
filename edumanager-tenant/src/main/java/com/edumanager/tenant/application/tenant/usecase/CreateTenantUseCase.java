package com.edumanager.tenant.application.tenant.usecase;

import com.edumanager.tenant.application.tenant.dto.request.CreateTenantRequest;
import com.edumanager.tenant.application.tenant.dto.response.TenantResponse;
import com.edumanager.tenant.application.tenant.mapper.TenantMapper;
import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.repository.TenantRepository;
import com.edumanager.tenant.domain.tenant.valueobject.*;
import com.edumanager.tenant.infrastructure.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use Case: Create Tenant
 * 
 * Orchestrates the creation of a new tenant including:
 * - Validation of tenant uniqueness
 * - Creation of tenant domain entity
 * - Persistence
 * - Database schema creation
 * - Event publication
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateTenantUseCase {

    private final TenantRepository tenantRepository;
    private final TenantMapper mapper;
    private final DomainEventPublisher eventPublisher;
    private final TenantSchemaService schemaService;

    @Transactional
    public TenantResponse execute(CreateTenantRequest request) {
        log.info("Creating tenant with slug: {}", request.slug());

        // 1. Validate uniqueness
        TenantSlug slug = new TenantSlug(request.slug());
        if (tenantRepository.existsBySlug(slug)) {
            throw new com.edumanager.tenant.exception.domain.BusinessException(
                com.edumanager.tenant.constants.ErrorCodes.TENANT_SLUG_DUPLICATE,
                "Tenant with slug '" + request.slug() + "' already exists"
            );
        }

        if (request.domain() != null) {
            Domain domain = new Domain(request.domain());
            if (tenantRepository.existsByDomain(domain)) {
                throw new com.edumanager.tenant.exception.domain.BusinessException(
                    com.edumanager.tenant.constants.ErrorCodes.TENANT_DOMAIN_DUPLICATE,
                    "Domain '" + request.domain() + "' is already in use"
                );
            }
        }

        if (request.subdomain() != null) {
            Subdomain subdomain = new Subdomain(request.subdomain());
            if (tenantRepository.existsBySubdomain(subdomain)) {
                throw new com.edumanager.tenant.exception.domain.BusinessException(
                    com.edumanager.tenant.constants.ErrorCodes.TENANT_SUBDOMAIN_DUPLICATE,
                    "Subdomain '" + request.subdomain() + "' is already in use"
                );
            }
        }

        // 2. Create domain entity
        ContactInfo contactInfo = new ContactInfo(
            request.contactInfo().email(),
            request.contactInfo().phone(),
            request.contactInfo().address(),
            request.contactInfo().city(),
            request.contactInfo().country()
        );

        Tenant tenant = new Tenant.Builder(
            slug,
            new TenantName(request.name()),
            contactInfo
        )
        .domain(request.domain() != null ? new Domain(request.domain()) : null)
        .subdomain(request.subdomain() != null ? new Subdomain(request.subdomain()) : null)
        .logoUrl(request.logoUrl() != null ? new LogoUrl(request.logoUrl()) : null)
        .theme(request.primaryColor() != null ? 
            new Theme(request.primaryColor(), request.secondaryColor()) : null)
        .configuration(new TenantConfiguration(
            request.timezone() != null ? java.time.ZoneId.of(request.timezone()) : null,
            request.locale() != null ? java.util.Locale.forLanguageTag(request.locale()) : null,
            request.currency()
        ))
        .build();

        // 3. Create database schema
        schemaService.createTenantSchema(tenant.getDatabaseConfig().getSchemaName());

        // 4. Persist tenant
        Tenant saved = tenantRepository.save(tenant);

        // 5. Publish domain events
        saved.pullDomainEvents().forEach(eventPublisher::publish);

        log.info("Tenant created successfully: {}", saved.getSlug().getValue());

        return mapper.toResponse(saved);
    }
}

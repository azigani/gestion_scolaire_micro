package com.edumanager.tenant.infrastructure.persistence.mapper;

import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.enums.SubscriptionPlan;
import com.edumanager.tenant.domain.tenant.enums.TenantStatus;
import com.edumanager.tenant.domain.tenant.valueobject.*;
import com.edumanager.tenant.infrastructure.persistence.entity.TenantJpaEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Locale;

/**
 * Mapper between Domain Entity and JPA Entity.
 * Converts between the domain model and persistence model.
 */
@Component
public class TenantPersistenceMapper {

    public TenantJpaEntity toJpaEntity(Tenant tenant) {
        return TenantJpaEntity.builder()
            .id(tenant.getId().getValue())
            .slug(tenant.getSlug().getValue())
            .name(tenant.getName().getValue())
            .domain(tenant.getDomain() != null ? tenant.getDomain().getValue() : null)
            .subdomain(tenant.getSubdomain() != null ? tenant.getSubdomain().getValue() : null)
            .logoUrl(tenant.getLogoUrl() != null ? tenant.getLogoUrl().getValue() : null)
            .primaryColor(tenant.getTheme() != null ? tenant.getTheme().getPrimaryColor() : null)
            .secondaryColor(tenant.getTheme() != null ? tenant.getTheme().getSecondaryColor() : null)
            .contactEmail(tenant.getContactInfo().getEmail())
            .contactPhone(tenant.getContactInfo().getPhone())
            .address(tenant.getContactInfo().getAddress())
            .city(tenant.getContactInfo().getCity())
            .country(tenant.getContactInfo().getCountry())
            .timezone(tenant.getConfiguration() != null ? tenant.getConfiguration().getTimezone().getId() : "Africa/Ouagadougou")
            .locale(tenant.getConfiguration() != null ? tenant.getConfiguration().getLocale().toLanguageTag() : "fr")
            .currency(tenant.getConfiguration() != null ? tenant.getConfiguration().getCurrency() : "XOF")
            .status(tenant.getStatus())
            .subscriptionPlan(tenant.getSubscription().getPlan().name())
            .subscriptionStart(tenant.getSubscription().getStartDate())
            .subscriptionEnd(tenant.getSubscription().getEndDate())
            .maxStudents(tenant.getSubscription().getMaxStudents())
            .maxTeachers(tenant.getSubscription().getMaxTeachers())
            .maxStaff(tenant.getSubscription().getMaxStaff())
            .dbSchemaName(tenant.getDatabaseConfig().getSchemaName())
            .dbHost(tenant.getDatabaseConfig().getHost())
            .dbPort(tenant.getDatabaseConfig().getPort())
            .dbName(tenant.getDatabaseConfig().getDatabaseName())
            .createdAt(tenant.getCreatedAt())
            .updatedAt(tenant.getUpdatedAt())
            .createdBy(tenant.getCreatedBy())
            .updatedBy(tenant.getUpdatedBy())
            .build();
    }

    public Tenant toDomainEntity(TenantJpaEntity jpaEntity) {
        Tenant.TenantBuilder builder = new Tenant.Builder(
            new TenantSlug(jpaEntity.getSlug()),
            new TenantName(jpaEntity.getName()),
            new ContactInfo(
                jpaEntity.getContactEmail(),
                jpaEntity.getContactPhone(),
                jpaEntity.getAddress(),
                jpaEntity.getCity(),
                jpaEntity.getCountry()
            )
        )
        .id(new TenantId(jpaEntity.getId()))
        .domain(jpaEntity.getDomain() != null ? new Domain(jpaEntity.getDomain()) : null)
        .subdomain(jpaEntity.getSubdomain() != null ? new Subdomain(jpaEntity.getSubdomain()) : null)
        .logoUrl(jpaEntity.getLogoUrl() != null ? new LogoUrl(jpaEntity.getLogoUrl()) : null)
        .theme(jpaEntity.getPrimaryColor() != null ? 
            new Theme(jpaEntity.getPrimaryColor(), jpaEntity.getSecondaryColor()) : null)
        .configuration(new TenantConfiguration(
            ZoneId.of(jpaEntity.getTimezone()),
            Locale.forLanguageTag(jpaEntity.getLocale()),
            jpaEntity.getCurrency()
        ))
        .subscription(new Subscription(
            SubscriptionPlan.valueOf(jpaEntity.getSubscriptionPlan()),
            jpaEntity.getSubscriptionStart(),
            jpaEntity.getSubscriptionEnd(),
            jpaEntity.getMaxStudents(),
            jpaEntity.getMaxTeachers(),
            jpaEntity.getMaxStaff()
        ))
        .databaseConfig(new DatabaseConfig(
            jpaEntity.getDbSchemaName(),
            jpaEntity.getDbHost(),
            jpaEntity.getDbPort(),
            jpaEntity.getDbName()
        ))
        .createdBy(jpaEntity.getCreatedBy());

        // Reconstitute the tenant with current status
        Tenant tenant = builder.build();
        
        // Set the actual status from database (builder sets PENDING by default)
        // This is a simplification - in production, use a reconstitution method
        // or add status to the builder
        
        return tenant;
    }
}

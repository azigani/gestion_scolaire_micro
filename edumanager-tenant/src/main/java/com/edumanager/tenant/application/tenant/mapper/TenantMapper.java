package com.edumanager.tenant.application.tenant.mapper;

import com.edumanager.tenant.application.tenant.dto.response.TenantResponse;
import com.edumanager.tenant.domain.tenant.entity.Tenant;
import org.springframework.stereotype.Component;

/**
 * Mapper between Domain Entity and DTOs.
 */
@Component
public class TenantMapper {

    public TenantResponse toResponse(Tenant tenant) {
        return new TenantResponse(
            tenant.getId().getValue().toString(),
            tenant.getSlug().getValue(),
            tenant.getName().getValue(),
            tenant.getDomain() != null ? tenant.getDomain().getValue() : null,
            tenant.getSubdomain() != null ? tenant.getSubdomain().getValue() : null,
            tenant.getLogoUrl() != null ? tenant.getLogoUrl().getValue() : null,
            tenant.getTheme() != null ? tenant.getTheme().getPrimaryColor() : null,
            tenant.getTheme() != null ? tenant.getTheme().getSecondaryColor() : null,
            new TenantResponse.ContactInfoResponse(
                tenant.getContactInfo().getEmail(),
                tenant.getContactInfo().getPhone(),
                tenant.getContactInfo().getAddress(),
                tenant.getContactInfo().getCity(),
                tenant.getContactInfo().getCountry()
            ),
            tenant.getConfiguration() != null ? tenant.getConfiguration().getTimezone().getId() : null,
            tenant.getConfiguration() != null ? tenant.getConfiguration().getLocale().toLanguageTag() : null,
            tenant.getConfiguration() != null ? tenant.getConfiguration().getCurrency() : null,
            tenant.getStatus().name(),
            new TenantResponse.SubscriptionResponse(
                tenant.getSubscription().getPlan().name(),
                tenant.getSubscription().getStartDate(),
                tenant.getSubscription().getEndDate(),
                tenant.getSubscription().getMaxStudents(),
                tenant.getSubscription().getMaxTeachers(),
                tenant.getSubscription().getMaxStaff()
            ),
            tenant.getCreatedAt(),
            tenant.getUpdatedAt()
        );
    }
}

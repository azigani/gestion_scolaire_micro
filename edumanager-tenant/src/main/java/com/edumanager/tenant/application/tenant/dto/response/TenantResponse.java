package com.edumanager.tenant.application.tenant.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for tenant response.
 */
public record TenantResponse(

    String id,
    String slug,
    String name,
    String domain,
    String subdomain,
    String logoUrl,
    String primaryColor,
    String secondaryColor,
    ContactInfoResponse contactInfo,
    String timezone,
    String locale,
    String currency,
    String status,
    SubscriptionResponse subscription,
    LocalDateTime createdAt,
    LocalDateTime updatedAt

) {

    public record ContactInfoResponse(
        String email,
        String phone,
        String address,
        String city,
        String country
    ) {}

    public record SubscriptionResponse(
        String plan,
        LocalDate startDate,
        LocalDate endDate,
        Integer maxStudents,
        Integer maxTeachers,
        Integer maxStaff
    ) {}
}

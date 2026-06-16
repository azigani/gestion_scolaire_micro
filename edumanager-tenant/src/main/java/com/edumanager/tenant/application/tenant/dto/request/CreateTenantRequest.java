package com.edumanager.tenant.application.tenant.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new tenant.
 */
public record CreateTenantRequest(

    @NotBlank(message = "Tenant slug is required")
    @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug must contain only lowercase letters, numbers, and hyphens")
    @Size(min = 3, max = 50, message = "Slug must be between 3 and 50 characters")
    String slug,

    @NotBlank(message = "Tenant name is required")
    @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters")
    String name,

    @Pattern(regexp = "^(?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.[A-Za-z]{2,}$", message = "Invalid domain format")
    String domain,

    @Pattern(regexp = "^[a-z0-9-]{1,63}$", message = "Subdomain must contain only lowercase letters, numbers, and hyphens")
    String subdomain,

    String logoUrl,

    String primaryColor,

    String secondaryColor,

    @Valid
    @NotNull(message = "Contact information is required")
    ContactInfoRequest contactInfo,

    String timezone,

    String locale,

    String currency

) {

    public record ContactInfoRequest(

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        String phone,

        String address,

        String city,

        String country

    ) {}
}

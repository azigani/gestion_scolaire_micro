package com.edumanager.tenant.domain.tenant.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tenant Entity Tests")
class TenantTest {

    @Test
    @DisplayName("Should create tenant with valid data")
    void shouldCreateTenantWithValidData() {
        Tenant tenant = Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .phone("+1234567890")
                .address("123 Test Street")
                .city("Test City")
                .country("Test Country")
                .build();

        assertNotNull(tenant);
        assertEquals("Test School", tenant.getName());
        assertEquals("testschool", tenant.getSubdomain());
        assertEquals("admin@testschool.com", tenant.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when name is null")
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Tenant.builder()
                    .name(null)
                    .subdomain("testschool")
                    .email("admin@testschool.com")
                    .build();
        });
    }

    @Test
    @DisplayName("Should throw exception when subdomain is null")
    void shouldThrowExceptionWhenSubdomainIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Tenant.builder()
                    .name("Test School")
                    .subdomain(null)
                    .email("admin@testschool.com")
                    .build();
        });
    }

    @Test
    @DisplayName("Should throw exception when email is invalid")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            Tenant.builder()
                    .name("Test School")
                    .subdomain("testschool")
                    .email("invalid-email")
                    .build();
        });
    }

    @Test
    @DisplayName("Should activate tenant successfully")
    void shouldActivateTenantSuccessfully() {
        Tenant tenant = Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.INACTIVE)
                .build();

        tenant.activate();

        assertEquals(TenantStatus.ACTIVE, tenant.getStatus());
    }

    @Test
    @DisplayName("Should deactivate tenant successfully")
    void shouldDeactivateTenantSuccessfully() {
        Tenant tenant = Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.ACTIVE)
                .build();

        tenant.deactivate();

        assertEquals(TenantStatus.INACTIVE, tenant.getStatus());
    }
}

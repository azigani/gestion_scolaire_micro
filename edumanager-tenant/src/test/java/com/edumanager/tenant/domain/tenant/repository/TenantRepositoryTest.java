package com.edumanager.tenant.domain.tenant.repository;

import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.enums.TenantStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tenant Repository Tests")
class TenantRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TenantRepository tenantRepository;

    @Test
    @DisplayName("Should find tenant by subdomain")
    void shouldFindTenantBySubdomain() {
        Tenant tenant = Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.ACTIVE)
                .build();

        entityManager.persist(tenant);
        entityManager.flush();

        Optional<Tenant> found = tenantRepository.findBySubdomain("testschool");

        assertTrue(found.isPresent());
        assertEquals("testschool", found.get().getSubdomain());
    }

    @Test
    @DisplayName("Should find tenant by email")
    void shouldFindTenantByEmail() {
        Tenant tenant = Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.ACTIVE)
                .build();

        entityManager.persist(tenant);
        entityManager.flush();

        Optional<Tenant> found = tenantRepository.findByEmail("admin@testschool.com");

        assertTrue(found.isPresent());
        assertEquals("admin@testschool.com", found.get().getEmail());
    }

    @Test
    @DisplayName("Should check if subdomain exists")
    void shouldCheckIfSubdomainExists() {
        Tenant tenant = Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.ACTIVE)
                .build();

        entityManager.persist(tenant);
        entityManager.flush();

        boolean exists = tenantRepository.existsBySubdomain("testschool");

        assertTrue(exists);
    }

    @Test
    @DisplayName("Should check if email exists")
    void shouldCheckIfEmailExists() {
        Tenant tenant = Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.ACTIVE)
                .build();

        entityManager.persist(tenant);
        entityManager.flush();

        boolean exists = tenantRepository.existsByEmail("admin@testschool.com");

        assertTrue(exists);
    }
}

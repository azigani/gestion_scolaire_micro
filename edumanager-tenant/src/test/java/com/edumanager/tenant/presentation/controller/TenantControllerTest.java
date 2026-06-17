package com.edumanager.tenant.presentation.controller;

import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.enums.TenantStatus;
import com.edumanager.tenant.domain.tenant.repository.TenantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Tenant Controller Tests")
class TenantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create tenant successfully")
    void shouldCreateTenantSuccessfully() throws Exception {
        String tenantJson = """
            {
                "name": "New School",
                "subdomain": "newschool",
                "email": "admin@newschool.com",
                "phone": "+1234567890",
                "address": "456 New Street",
                "city": "New City",
                "country": "New Country"
            }
            """;

        mockMvc.perform(post("/api/v1/tenants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tenantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New School"))
                .andExpect(jsonPath("$.subdomain").value("newschool"));
    }

    @Test
    @DisplayName("Should get tenant by id")
    void shouldGetTenantById() throws Exception {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.ACTIVE)
                .build());

        mockMvc.perform(get("/api/v1/tenants/" + tenant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test School"));
    }

    @Test
    @DisplayName("Should get all tenants")
    void shouldGetAllTenants() throws Exception {
        tenantRepository.save(Tenant.builder()
                .name("School 1")
                .subdomain("school1")
                .email("admin@school1.com")
                .status(TenantStatus.ACTIVE)
                .build());

        tenantRepository.save(Tenant.builder()
                .name("School 2")
                .subdomain("school2")
                .email("admin@school2.com")
                .status(TenantStatus.ACTIVE)
                .build());

        mockMvc.perform(get("/api/v1/tenants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("Should update tenant successfully")
    void shouldUpdateTenantSuccessfully() throws Exception {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.ACTIVE)
                .build());

        String updateJson = """
            {
                "name": "Updated School",
                "phone": "+9876543210"
            }
            """;

        mockMvc.perform(put("/api/v1/tenants/" + tenant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated School"));
    }

    @Test
    @DisplayName("Should delete tenant successfully")
    void shouldDeleteTenantSuccessfully() throws Exception {
        Tenant tenant = tenantRepository.save(Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.ACTIVE)
                .build());

        mockMvc.perform(delete("/api/v1/tenants/" + tenant.getId()))
                .andExpect(status().isNoContent());
    }
}

package com.edumanager.gateway.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tenant Header Filter Tests")
class TenantHeaderFilterTest {

    @Test
    @DisplayName("Should propagate tenant header")
    void shouldPropagateTenantHeader() {
        TenantHeaderFilter filter = new TenantHeaderFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        request.addHeader("X-Tenant-ID", "tenant-123");

        filter.doFilter(request, response, (req, res) -> {});

        assertEquals("tenant-123", request.getHeader("X-Tenant-ID"));
    }

    @Test
    @DisplayName("Should handle missing tenant header")
    void shouldHandleMissingTenantHeader() {
        TenantHeaderFilter filter = new TenantHeaderFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilter(request, response, (req, res) -> {});

        assertNull(request.getHeader("X-Tenant-ID"));
    }
}

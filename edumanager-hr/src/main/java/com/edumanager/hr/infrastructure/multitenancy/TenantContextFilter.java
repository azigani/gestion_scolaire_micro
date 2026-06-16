package com.edumanager.hr.infrastructure.multitenancy;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that resolves tenant from HTTP request and sets TenantContext.
 * 
 * Tenant resolution strategies:
 * 1. X-Tenant-ID header - Direct tenant ID
 * 2. X-Tenant-Slug header - Tenant slug (requires tenant service call)
 */
@Slf4j
@Component
public class TenantContextFilter extends OncePerRequestFilter {

    private static final String TENANT_ID_HEADER = "X-Tenant-ID";
    private static final String TENANT_SLUG_HEADER = "X-Tenant-Slug";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        try {
            TenantContext.TenantInfo tenantInfo = resolveTenant(request);
            if (tenantInfo != null) {
                TenantContext.setTenant(tenantInfo);
                log.debug("Tenant context set: {}", tenantInfo.getTenantId());
            }
        } catch (Exception e) {
            log.error("Failed to resolve tenant context", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid tenant");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private TenantContext.TenantInfo resolveTenant(HttpServletRequest request) {
        // Strategy 1: X-Tenant-ID header
        String tenantIdHeader = request.getHeader(TENANT_ID_HEADER);
        if (tenantIdHeader != null) {
            try {
                UUID id = UUID.fromString(tenantIdHeader);
                return new TenantContext.TenantInfo(id, "tenant_" + id.toString().replace("-", "_"));
            } catch (IllegalArgumentException e) {
                log.error("Invalid tenant ID format: {}", tenantIdHeader);
                return null;
            }
        }

        // Strategy 2: X-Tenant-Slug header (requires tenant service call)
        // TODO: Implement tenant service call to resolve slug to ID
        String tenantSlugHeader = request.getHeader(TENANT_SLUG_HEADER);
        if (tenantSlugHeader != null) {
            log.debug("Slug resolution not yet implemented: {}", tenantSlugHeader);
            return null;
        }

        // No tenant context for public endpoints
        return null;
    }
}

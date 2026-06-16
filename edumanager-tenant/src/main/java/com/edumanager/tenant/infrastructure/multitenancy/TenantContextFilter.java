package com.edumanager.tenant.infrastructure.multitenancy;

import com.edumanager.tenant.domain.tenant.entity.Tenant;
import com.edumanager.tenant.domain.tenant.repository.TenantRepository;
import com.edumanager.tenant.domain.tenant.valueobject.TenantId;
import com.edumanager.tenant.domain.tenant.valueobject.TenantSlug;
import com.edumanager.tenant.exception.domain.TenantNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * Filter that resolves tenant from HTTP request and sets TenantContext.
 * 
 * Tenant resolution strategies (in order of priority):
 * 1. X-Tenant-ID header - Direct tenant ID
 * 2. X-Tenant-Slug header - Tenant slug
 * 3. Subdomain - Extracted from Host header
 * 4. Custom domain - Extracted from Host header
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TenantContextFilter extends OncePerRequestFilter {

    private final TenantRepository tenantRepository;

    private static final String TENANT_ID_HEADER = "X-Tenant-ID";
    private static final String TENANT_SLUG_HEADER = "X-Tenant-Slug";
    private static final String BASE_DOMAIN = "edumanager.com";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        try {
            TenantContext.TenantInfo tenantInfo = resolveTenant(request);
            if (tenantInfo != null) {
                TenantContext.setTenant(tenantInfo);
                log.debug("Tenant context set: {} (schema: {})", tenantInfo.getTenantSlug(), tenantInfo.getSchemaName());
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
            return resolveById(tenantIdHeader);
        }

        // Strategy 2: X-Tenant-Slug header
        String tenantSlugHeader = request.getHeader(TENANT_SLUG_HEADER);
        if (tenantSlugHeader != null) {
            return resolveBySlug(tenantSlugHeader);
        }

        // Strategy 3: Subdomain
        String host = request.getServerName();
        if (host.contains(".")) {
            String subdomain = host.substring(0, host.indexOf('.'));
            if (!subdomain.equals("www") && !subdomain.equals("api")) {
                return resolveBySubdomain(subdomain);
            }
        }

        // Strategy 4: Custom domain
        if (!host.endsWith(BASE_DOMAIN)) {
            return resolveByDomain(host);
        }

        // No tenant context for public endpoints (login, tenant registration, etc.)
        return null;
    }

    private TenantContext.TenantInfo resolveById(String tenantId) {
        try {
            UUID id = UUID.fromString(tenantId);
            Tenant tenant = tenantRepository.findById(new TenantId(id))
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found: " + tenantId));
            return new TenantContext.TenantInfo(
                tenant.getId().getValue(),
                tenant.getSlug().getValue(),
                tenant.getDatabaseConfig().getSchemaName()
            );
        } catch (IllegalArgumentException e) {
            throw new TenantNotFoundException("Invalid tenant ID format: " + tenantId);
        }
    }

    private TenantContext.TenantInfo resolveBySlug(String slug) {
        Tenant tenant = tenantRepository.findBySlug(new TenantSlug(slug))
            .orElseThrow(() -> new TenantNotFoundException("Tenant not found: " + slug));
        return new TenantContext.TenantInfo(
            tenant.getId().getValue(),
            tenant.getSlug().getValue(),
            tenant.getDatabaseConfig().getSchemaName()
        );
    }

    private TenantContext.TenantInfo resolveBySubdomain(String subdomain) {
        // This would require a Subdomain value object and repository method
        // For now, skip - to be implemented
        log.debug("Subdomain resolution not yet implemented: {}", subdomain);
        return null;
    }

    private TenantContext.TenantInfo resolveByDomain(String domain) {
        // This would require a Domain value object and repository method
        // For now, skip - to be implemented
        log.debug("Custom domain resolution not yet implemented: {}", domain);
        return null;
    }
}

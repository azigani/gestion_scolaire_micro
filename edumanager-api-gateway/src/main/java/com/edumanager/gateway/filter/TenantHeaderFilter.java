package com.edumanager.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * Gateway filter that adds tenant context headers to downstream requests.
 * This ensures tenant isolation across microservices.
 */
@Slf4j
@Component
public class TenantHeaderFilter extends AbstractGatewayFilterFactory<TenantHeaderFilter.Config> {

    private static final String TENANT_ID_HEADER = "X-Tenant-ID";

    public TenantHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String tenantId = exchange.getRequest().getHeaders().getFirst(TENANT_ID_HEADER);
            
            if (tenantId != null) {
                log.debug("Forwarding tenant context: {}", tenantId);
                ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(r -> r.header(TENANT_ID_HEADER, tenantId))
                    .build();
                return chain.filter(modifiedExchange);
            }
            
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties if needed
    }
}

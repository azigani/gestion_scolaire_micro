package com.edumanager.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Gateway filter that validates JWT tokens.
 * This ensures only authenticated requests reach downstream services.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String TENANT_ID_HEADER = "X-Tenant-ID";

    private final SecretKey signingKey;

    public JwtAuthenticationFilter(@Value("${edumanager.jwt.secret}") String jwtSecret) {
        super(Config.class);
        byte[] keyBytes = Base64.getEncoder().encode(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
            
            // Skip authentication for public endpoints
            String path = exchange.getRequest().getPath().value();
            if (isPublicEndpoint(path)) {
                return chain.filter(exchange);
            }
            
            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                log.warn("Missing or invalid authorization header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            
            try {
                String token = authHeader.substring(BEARER_PREFIX.length());
                Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
                
                // Extract tenant ID from claims
                String tenantId = claims.get("tenantId", String.class);
                
                if (tenantId != null) {
                    ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(r -> r.header(TENANT_ID_HEADER, tenantId))
                        .build();
                    return chain.filter(modifiedExchange);
                }
                
                return chain.filter(exchange);
                
            } catch (Exception e) {
                log.error("JWT validation failed", e);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }

    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/api/tenant/auth") || 
               path.startsWith("/actuator") ||
               path.startsWith("/swagger") ||
               path.startsWith("/api-docs");
    }

    public static class Config {
        // Configuration properties if needed
    }
}

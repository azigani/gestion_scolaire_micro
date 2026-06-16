package com.edumanager.tenant.application.tenant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for managing tenant database schemas.
 * Creates and manages isolated schemas for each tenant.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TenantSchemaService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Create a new schema for a tenant.
     */
    public void createTenantSchema(String schemaName) {
        log.info("Creating schema: {}", schemaName);
        
        // Check if schema already exists
        Boolean exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT 1 FROM information_schema.schemata WHERE schema_name = ?)",
            Boolean.class,
            schemaName
        );

        if (Boolean.TRUE.equals(exists)) {
            log.warn("Schema already exists: {}", schemaName);
            return;
        }

        // Create schema
        jdbcTemplate.execute(String.format("CREATE SCHEMA %s", schemaName));
        
        // Grant permissions
        jdbcTemplate.execute(String.format("GRANT ALL PRIVILEGES ON SCHEMA %s TO edumanager", schemaName));
        jdbcTemplate.execute(String.format("GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA %s TO edumanager", schemaName));
        jdbcTemplate.execute(String.format("ALTER DEFAULT PRIVILEGES IN SCHEMA %s GRANT ALL ON TABLES TO edumanager", schemaName));

        log.info("Schema created successfully: {}", schemaName);
    }

    /**
     * Drop a tenant schema.
     */
    public void dropTenantSchema(String schemaName) {
        log.info("Dropping schema: {}", schemaName);
        jdbcTemplate.execute(String.format("DROP SCHEMA IF EXISTS %s CASCADE", schemaName));
        log.info("Schema dropped successfully: {}", schemaName);
    }

    /**
     * Check if a schema exists.
     */
    public boolean schemaExists(String schemaName) {
        Boolean exists = jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT 1 FROM information_schema.schemata WHERE schema_name = ?)",
            Boolean.class,
            schemaName
        );
        return Boolean.TRUE.equals(exists);
    }
}

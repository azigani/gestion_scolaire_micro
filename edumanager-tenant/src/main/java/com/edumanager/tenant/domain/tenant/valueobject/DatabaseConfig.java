package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object: DatabaseConfig
 * Represents database configuration for a tenant.
 */
public final class DatabaseConfig extends ValueObject {

    private static final Pattern SCHEMA_PATTERN = Pattern.compile("^[a-z][a-z0-9_]{0,98}[a-z0-9]$");

    private final String schemaName;
    private final String host;
    private final Integer port;
    private final String databaseName;

    public DatabaseConfig(String schemaName) {
        this(schemaName, null, null, null);
    }

    public DatabaseConfig(String schemaName, String host, Integer port, String databaseName) {
        if (schemaName == null || schemaName.isBlank()) {
            throw new IllegalArgumentException("Schema name is required");
        }
        if (!SCHEMA_PATTERN.matcher(schemaName).matches()) {
            throw new IllegalArgumentException(
                "Schema name must start with a letter, contain only lowercase letters, numbers, and underscores");
        }
        
        this.schemaName = schemaName;
        this.host = host;
        this.port = port;
        this.databaseName = databaseName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseConfig)) return false;
        DatabaseConfig that = (DatabaseConfig) o;
        return Objects.equals(schemaName, that.schemaName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schemaName);
    }
}

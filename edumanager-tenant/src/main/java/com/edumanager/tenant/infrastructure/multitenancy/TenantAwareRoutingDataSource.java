package com.edumanager.tenant.infrastructure.multitenancy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Routing DataSource that switches between tenant-specific schemas.
 * 
 * This datasource routes SQL queries to the appropriate schema based on
 * the current TenantContext. All tenants share the same physical database
 * but use different schemas for isolation.
 * 
 * Schema naming convention: tenant_<slug_lower_with_underscores>
 */
@Slf4j
public class TenantAwareRoutingDataSource extends AbstractRoutingDataSource {

    private final DataSource defaultDataSource;

    public TenantAwareRoutingDataSource(DataSource defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
        setDefaultTargetDataSource(defaultDataSource);
        setTargetDataSources(new HashMap<>());
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getSchemaName().orElse("public");
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        log.info("TenantAwareRoutingDataSource initialized");
    }
}

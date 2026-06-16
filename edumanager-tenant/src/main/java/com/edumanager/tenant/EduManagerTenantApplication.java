package com.edumanager.tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main application class for EduManager Tenant Service
 * 
 * Responsibilities:
 * - Multi-tenant management
 * - Tenant creation and configuration
 * - Tenant database schema management
 * - Tenant user authentication
 * - Tenant isolation enforcement
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableKafka
public class EduManagerTenantApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduManagerTenantApplication.class, args);
    }
}

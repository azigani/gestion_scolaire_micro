package com.edumanager.financial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main application class for EduManager Financial Service.
 * 
 * This microservice handles:
 * - Fee configuration
 * - Invoice generation
 * - Payment processing
 * - Debt tracking
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableKafka
public class EduManagerFinancialApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduManagerFinancialApplication.class, args);
    }
}

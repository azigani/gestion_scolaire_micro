package com.edumanager.academic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main application class for EduManager Academic Service.
 * 
 * This microservice handles:
 * - Subject management
 * - Grade entry and management
 * - Schedule management
 * - Report card generation
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableKafka
public class EduManagerAcademicApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduManagerAcademicApplication.class, args);
    }
}

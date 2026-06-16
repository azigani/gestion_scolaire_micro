package com.edumanager.enrollment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main application class for EduManager Enrollment Service.
 * 
 * This microservice handles:
 * - Student enrollment
 * - Class management
 * - Student records
 * - Academic year management
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableKafka
public class EduManagerEnrollmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduManagerEnrollmentApplication.class, args);
    }
}

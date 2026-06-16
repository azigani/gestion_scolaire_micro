package com.edumanager.communication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main application class for EduManager Communication Service.
 * 
 * This microservice handles:
 * - Email notifications
 * - SMS notifications
 * - In-app notifications
 * - Announcements
 * - Message templates
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableKafka
public class EduManagerCommunicationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduManagerCommunicationApplication.class, args);
    }
}

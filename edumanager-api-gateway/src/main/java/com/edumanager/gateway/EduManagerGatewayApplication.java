package com.edumanager.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main application class for EduManager API Gateway.
 * 
 * This service handles:
 * - API routing to microservices
 * - Authentication and authorization
 * - Rate limiting
 * - Request/response transformation
 * - Cross-cutting concerns (logging, metrics)
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableKafka
public class EduManagerGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduManagerGatewayApplication.class, args);
    }
}

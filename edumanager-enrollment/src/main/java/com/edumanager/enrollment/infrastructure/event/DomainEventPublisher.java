package com.edumanager.enrollment.infrastructure.event;

import com.edumanager.enrollment.domain.shared.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Publisher for domain events.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DomainEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(DomainEvent event) {
        try {
            String topic = "domain-events";
            log.debug("Publishing event {} to topic {}", event.getEventType(), topic);
            kafkaTemplate.send(topic, event.getEventType(), event);
        } catch (Exception e) {
            log.error("Failed to publish event: {}", event.getEventType(), e);
        }
    }
}

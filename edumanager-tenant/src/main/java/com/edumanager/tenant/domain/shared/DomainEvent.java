package com.edumanager.tenant.domain.shared;

import java.time.Instant;
import java.util.UUID;

/**
 * Base class for all domain events in the system.
 * Domain events represent something that happened in the domain
 * that other parts of the system may need to react to.
 */
public abstract class DomainEvent {

    private final UUID eventId;
    private final Instant occurredAt;
    private final String eventType;

    protected DomainEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredAt = Instant.now();
        this.eventType = this.getClass().getSimpleName();
    }

    public UUID getEventId() {
        return eventId;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public String getEventType() {
        return eventType;
    }
}

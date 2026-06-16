package com.edumanager.tenant.domain.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all aggregate roots in the domain layer.
 * Provides domain event management capabilities.
 */
public abstract class AggregateRoot {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * Register a domain event to be published later
     */
    protected void registerEvent(DomainEvent event) {
        if (event != null) {
            this.domainEvents.add(event);
        }
    }

    /**
     * Get all pending domain events and clear the list
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    /**
     * Check if there are pending domain events
     */
    public boolean hasDomainEvents() {
        return !this.domainEvents.isEmpty();
    }
}

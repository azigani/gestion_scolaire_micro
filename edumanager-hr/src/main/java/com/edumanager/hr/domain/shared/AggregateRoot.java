package com.edumanager.hr.domain.shared;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all aggregate roots in the domain.
 * Provides domain event management capabilities.
 */
public abstract class AggregateRoot {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * Register a domain event to be published later.
     */
    protected void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    /**
     * Pull all domain events and clear the list.
     * This should be called after persisting the aggregate.
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }

    /**
     * Get the number of pending domain events.
     */
    public int getDomainEventsCount() {
        return domainEvents.size();
    }
}

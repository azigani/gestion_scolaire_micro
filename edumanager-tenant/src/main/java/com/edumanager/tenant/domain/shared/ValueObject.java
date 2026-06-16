package com.edumanager.tenant.domain.shared;

/**
 * Base class for all Value Objects in the domain.
 * Value Objects are defined by their attributes rather than identity.
 * They should be immutable and implement equals/hashCode based on their values.
 */
public abstract class ValueObject {

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();
}

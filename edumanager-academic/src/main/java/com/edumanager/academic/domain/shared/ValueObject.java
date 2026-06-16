package com.edumanager.academic.domain.shared;

/**
 * Base class for all value objects.
 * Value objects are defined by their attributes rather than identity.
 * They are immutable and should implement equals/hashCode based on their values.
 */
public abstract class ValueObject {

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();
}

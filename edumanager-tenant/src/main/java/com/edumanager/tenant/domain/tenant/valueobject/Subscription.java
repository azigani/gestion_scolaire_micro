package com.edumanager.tenant.domain.tenant.valueobject;

import com.edumanager.tenant.domain.shared.ValueObject;
import com.edumanager.tenant.domain.tenant.enums.SubscriptionPlan;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Value Object: Subscription
 * Represents subscription details for a tenant.
 */
public final class Subscription extends ValueObject {

    private final SubscriptionPlan plan;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int maxStudents;
    private final int maxTeachers;
    private final int maxStaff;

    public Subscription(SubscriptionPlan plan, LocalDate startDate, LocalDate endDate,
                       int maxStudents, int maxTeachers, int maxStaff) {
        if (plan == null) {
            throw new IllegalArgumentException("Subscription plan is required");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Subscription start date is required");
        }
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Subscription end date must be after start date");
        }
        if (maxStudents <= 0) {
            throw new IllegalArgumentException("Max students must be positive");
        }
        if (maxTeachers <= 0) {
            throw new IllegalArgumentException("Max teachers must be positive");
        }
        if (maxStaff <= 0) {
            throw new IllegalArgumentException("Max staff must be positive");
        }

        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxStudents = maxStudents;
        this.maxTeachers = maxTeachers;
        this.maxStaff = maxStaff;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public int getMaxTeachers() {
        return maxTeachers;
    }

    public int getMaxStaff() {
        return maxStaff;
    }

    public boolean isActive() {
        return endDate == null || LocalDate.now().isBefore(endDate);
    }

    public Subscription withNewPlan(SubscriptionPlan newPlan) {
        return new Subscription(newPlan, this.startDate, this.endDate,
                              this.maxStudents, this.maxTeachers, this.maxStaff);
    }

    public Subscription withNewEndDate(LocalDate newEndDate) {
        return new Subscription(this.plan, this.startDate, newEndDate,
                              this.maxStudents, this.maxTeachers, this.maxStaff);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        Subscription that = (Subscription) o;
        return plan == that.plan &&
               Objects.equals(startDate, that.startDate) &&
               Objects.equals(endDate, that.endDate) &&
               maxStudents == that.maxStudents &&
               maxTeachers == that.maxTeachers &&
               maxStaff == that.maxStaff;
    }

    @Override
    public int hashCode() {
        return Objects.hash(plan, startDate, endDate, maxStudents, maxTeachers, maxStaff);
    }
}

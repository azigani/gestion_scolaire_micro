package com.edumanager.tenant.domain.tenant.enums;

/**
 * Enum: SubscriptionPlan
 * Represents the subscription plan for a tenant.
 */
public enum SubscriptionPlan {
    /**
     * Starter plan - Basic features, limited users
     */
    STARTER(100, 20, 10),
    
    /**
     * Professional plan - Extended features, more users
     */
    PROFESSIONAL(500, 100, 50),
    
    /**
     * Enterprise plan - Full features, unlimited users
     */
    ENTERPRISE(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    private final int maxStudents;
    private final int maxTeachers;
    private final int maxStaff;

    SubscriptionPlan(int maxStudents, int maxTeachers, int maxStaff) {
        this.maxStudents = maxStudents;
        this.maxTeachers = maxTeachers;
        this.maxStaff = maxStaff;
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
}

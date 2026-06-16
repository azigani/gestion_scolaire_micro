package com.edumanager.tenant.domain.tenant.entity;

import com.edumanager.tenant.domain.shared.AggregateRoot;
import com.edumanager.tenant.domain.tenant.enums.TenantStatus;
import com.edumanager.tenant.domain.tenant.enums.SubscriptionPlan;
import com.edumanager.tenant.domain.tenant.event.TenantCreatedEvent;
import com.edumanager.tenant.domain.tenant.event.TenantActivatedEvent;
import com.edumanager.tenant.domain.tenant.event.TenantSuspendedEvent;
import com.edumanager.tenant.domain.tenant.valueobject.*;
import com.edumanager.tenant.exception.domain.BusinessException;
import com.edumanager.tenant.constants.ErrorCodes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Tenant
 * 
 * Represents a tenant (school) in the multi-tenant SaaS system.
 * Contains all business rules related to tenant lifecycle.
 * 
 * Business Rules:
 * - RG-T01: Tenant slug must be unique and URL-safe
 * - RG-T02: Only PENDING tenants can be activated
 * - RG-T03: Only ACTIVE tenants can be suspended
 * - RG-T04: Subscription cannot be extended if tenant is TERMINATED
 * - RG-T05: Schema name must be unique and follow naming convention
 */
public class Tenant extends AggregateRoot {

    private final TenantId id;
    private TenantSlug slug;
    private TenantName name;
    private Domain domain;
    private Subdomain subdomain;
    private LogoUrl logoUrl;
    private Theme theme;
    
    private ContactInfo contactInfo;
    private TenantConfiguration configuration;
    
    private TenantStatus status;
    private Subscription subscription;
    
    private DatabaseConfig databaseConfig;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    private Tenant(Builder builder) {
        this.id = builder.id != null ? builder.id : new TenantId(UUID.randomUUID());
        this.slug = builder.slug;
        this.name = builder.name;
        this.domain = builder.domain;
        this.subdomain = builder.subdomain;
        this.logoUrl = builder.logoUrl;
        this.theme = builder.theme;
        this.contactInfo = builder.contactInfo;
        this.configuration = builder.configuration;
        this.status = TenantStatus.PENDING;
        this.subscription = builder.subscription;
        this.databaseConfig = builder.databaseConfig;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = builder.createdBy;
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * RG-T02: Activate tenant from PENDING status
     */
    public void activate() {
        if (this.status != TenantStatus.PENDING) {
            throw new BusinessException(ErrorCodes.TENANT_NOT_PENDING,
                "Only PENDING tenants can be activated. Current status: " + this.status);
        }
        
        this.status = TenantStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
        registerEvent(new TenantActivatedEvent(this.id.getValue(), this.slug.getValue()));
    }

    /**
     * RG-T03: Suspend active tenant
     */
    public void suspend(String reason) {
        if (this.status != TenantStatus.ACTIVE) {
            throw new BusinessException(ErrorCodes.TENANT_NOT_ACTIVE,
                "Only ACTIVE tenants can be suspended. Current status: " + this.status);
        }
        
        if (reason == null || reason.isBlank()) {
            throw new BusinessException(ErrorCodes.SUSPENSION_REASON_REQUIRED,
                "Suspension reason is required");
        }
        
        this.status = TenantStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
        registerEvent(new TenantSuspendedEvent(this.id.getValue(), this.slug.getValue(), reason));
    }

    /**
     * Reactivate suspended tenant
     */
    public void reactivate() {
        if (this.status != TenantStatus.SUSPENDED) {
            throw new BusinessException(ErrorCodes.TENANT_NOT_SUSPENDED,
                "Only SUSPENDED tenants can be reactivated. Current status: " + this.status);
        }
        
        this.status = TenantStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Terminate tenant (final state, cannot be reversed)
     */
    public void terminate(String reason) {
        if (this.status == TenantStatus.TERMINATED) {
            throw new BusinessException(ErrorCodes.TENANT_ALREADY_TERMINATED,
                "Tenant is already terminated");
        }
        
        if (reason == null || reason.isBlank()) {
            throw new BusinessException(ErrorCodes.TERMINATION_REASON_REQUIRED,
                "Termination reason is required");
        }
        
        this.status = TenantStatus.TERMINATED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * RG-T04: Extend subscription
     */
    public void extendSubscription(LocalDate newEndDate) {
        if (this.status == TenantStatus.TERMINATED) {
            throw new BusinessException(ErrorCodes.TENANT_TERMINATED,
                "Cannot extend subscription for terminated tenant");
        }
        
        if (newEndDate == null || newEndDate.isBefore(LocalDate.now())) {
            throw new BusinessException(ErrorCodes.INVALID_SUBSCRIPTION_DATE,
                "New subscription end date must be in the future");
        }
        
        this.subscription = this.subscription.withNewEndDate(newEndDate);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update subscription plan
     */
    public void upgradePlan(SubscriptionPlan newPlan) {
        if (this.status == TenantStatus.TERMINATED) {
            throw new BusinessException(ErrorCodes.TENANT_TERMINATED,
                "Cannot upgrade plan for terminated tenant");
        }
        
        if (newPlan == null) {
            throw new BusinessException(ErrorCodes.INVALID_PLAN,
                "New plan cannot be null");
        }
        
        this.subscription = this.subscription.withNewPlan(newPlan);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update contact information
     */
    public void updateContactInfo(ContactInfo newContactInfo) {
        if (newContactInfo == null) {
            throw new BusinessException(ErrorCodes.CONTACT_INFO_REQUIRED,
                "Contact information is required");
        }
        
        this.contactInfo = newContactInfo;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update theme
     */
    public void updateTheme(Theme newTheme) {
        if (newTheme != null) {
            this.theme = newTheme;
            this.updatedAt = LocalDateTime.now();
        }
    }

    /**
     * Update logo
     */
    public void updateLogo(LogoUrl newLogoUrl) {
        this.logoUrl = newLogoUrl;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Check if tenant can accept new students
     */
    public boolean canAcceptStudents(int currentStudentCount) {
        return this.status == TenantStatus.ACTIVE && 
               currentStudentCount < this.subscription.getMaxStudents();
    }

    /**
     * Check if tenant can accept new teachers
     */
    public boolean canAcceptTeachers(int currentTeacherCount) {
        return this.status == TenantStatus.ACTIVE && 
               currentTeacherCount < this.subscription.getMaxTeachers();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public TenantId getId() { return id; }
    public TenantSlug getSlug() { return slug; }
    public TenantName getName() { return name; }
    public Domain getDomain() { return domain; }
    public Subdomain getSubdomain() { return subdomain; }
    public LogoUrl getLogoUrl() { return logoUrl; }
    public Theme getTheme() { return theme; }
    public ContactInfo getContactInfo() { return contactInfo; }
    public TenantConfiguration getConfiguration() { return configuration; }
    public TenantStatus getStatus() { return status; }
    public Subscription getSubscription() { return subscription; }
    public DatabaseConfig getDatabaseConfig() { return databaseConfig; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public UUID getCreatedBy() { return createdBy; }
    public UUID getUpdatedBy() { return updatedBy; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private TenantId id;
        private TenantSlug slug;
        private TenantName name;
        private Domain domain;
        private Subdomain subdomain;
        private LogoUrl logoUrl;
        private Theme theme;
        private ContactInfo contactInfo;
        private TenantConfiguration configuration;
        private Subscription subscription;
        private DatabaseConfig databaseConfig;
        private UUID createdBy;

        public Builder(TenantSlug slug, TenantName name, ContactInfo contactInfo) {
            this.slug = slug;
            this.name = name;
            this.contactInfo = contactInfo;
        }

        public Builder id(TenantId id) {
            this.id = id;
            return this;
        }

        public Builder domain(Domain domain) {
            this.domain = domain;
            return this;
        }

        public Builder subdomain(Subdomain subdomain) {
            this.subdomain = subdomain;
            return this;
        }

        public Builder logoUrl(LogoUrl logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        public Builder theme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Builder configuration(TenantConfiguration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder subscription(Subscription subscription) {
            this.subscription = subscription;
            return this;
        }

        public Builder databaseConfig(DatabaseConfig databaseConfig) {
            this.databaseConfig = databaseConfig;
            return this;
        }

        public Builder createdBy(UUID createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Tenant build() {
            validate();
            Tenant tenant = new Tenant(this);
            tenant.registerEvent(new TenantCreatedEvent(tenant.id.getValue(), tenant.slug.getValue()));
            return tenant;
        }

        private void validate() {
            if (slug == null) throw new IllegalArgumentException("slug is required");
            if (name == null) throw new IllegalArgumentException("name is required");
            if (contactInfo == null) throw new IllegalArgumentException("contactInfo is required");
            if (subscription == null) {
                // Create default subscription
                this.subscription = new Subscription(
                    SubscriptionPlan.STARTER,
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    100,
                    20,
                    10
                );
            }
            if (databaseConfig == null) {
                // Generate default schema name from slug
                String schemaName = "tenant_" + slug.getValue().toLowerCase().replace("-", "_");
                this.databaseConfig = new DatabaseConfig(schemaName);
            }
        }
    }
}

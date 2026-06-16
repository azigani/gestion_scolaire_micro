package com.edumanager.tenant.domain.tenant.entity;

import com.edumanager.tenant.domain.shared.AggregateRoot;
import com.edumanager.tenant.domain.tenant.enums.UserRole;
import com.edumanager.tenant.domain.tenant.valueobject.TenantId;
import com.edumanager.tenant.exception.domain.BusinessException;
import com.edumanager.tenant.constants.ErrorCodes;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: TenantUser
 * 
 * Represents a user within a tenant context.
 * Contains authentication and profile information.
 * 
 * Business Rules:
 * - RG-U01: Email must be unique within tenant
 * - RG-U02: Username must be unique within tenant
 * - RG-U03: Password must meet complexity requirements
 * - RG-U04: Account locks after 3 failed attempts
 * - RG-U05: Password expires every 90 days
 */
public class TenantUser extends AggregateRoot {

    private final UUID id;
    private final TenantId tenantId;
    
    private String username;
    private String email;
    private String passwordHash;
    
    private String firstName;
    private String lastName;
    private String phone;
    
    private UserRole role;
    
    private boolean isActive;
    private boolean isLocked;
    private int failedAttempts;
    private LocalDateTime lockedUntil;
    private LocalDateTime passwordExpiresAt;
    private LocalDateTime lastLogin;
    private boolean mustChangePassword;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private TenantUser(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.username = builder.username;
        this.email = builder.email;
        this.passwordHash = builder.passwordHash;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phone = builder.phone;
        this.role = builder.role;
        this.isActive = true;
        this.isLocked = false;
        this.failedAttempts = 0;
        this.lockedUntil = null;
        this.passwordExpiresAt = LocalDateTime.now().plusDays(90);
        this.lastLogin = null;
        this.mustChangePassword = builder.mustChangePassword;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * RG-U04: Record failed login attempt
     */
    public void recordFailedLogin() {
        this.failedAttempts++;
        this.updatedAt = LocalDateTime.now();
        
        if (this.failedAttempts >= 3) {
            lockAccount(30);
        }
    }

    /**
     * Reset failed login attempts on successful login
     */
    public void resetFailedAttempts() {
        this.failedAttempts = 0;
        this.isLocked = false;
        this.lockedUntil = null;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Record successful login
     */
    public void recordLogin() {
        this.lastLogin = LocalDateTime.now();
        this.resetFailedAttempts();
        
        if (this.mustChangePassword) {
            // Password change required flag remains
        }
    }

    /**
     * Lock account for specified minutes
     */
    public void lockAccount(int minutes) {
        this.isLocked = true;
        this.lockedUntil = LocalDateTime.now().plusMinutes(minutes);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Check if account is locked
     */
    public boolean isAccountLocked() {
        if (!this.isLocked) {
            return false;
        }
        
        if (this.lockedUntil != null && LocalDateTime.now().isAfter(this.lockedUntil)) {
            // Auto-unlock after lock period
            this.isLocked = false;
            this.lockedUntil = null;
            this.updatedAt = LocalDateTime.now();
            return false;
        }
        
        return true;
    }

    /**
     * Check if password is expired
     */
    public boolean isPasswordExpired() {
        return LocalDateTime.now().isAfter(this.passwordExpiresAt);
    }

    /**
     * Change password
     */
    public void changePassword(String newPasswordHash) {
        if (newPasswordHash == null || newPasswordHash.isBlank()) {
            throw new BusinessException(ErrorCodes.VALIDATION_ERROR, "New password cannot be empty");
        }
        
        this.passwordHash = newPasswordHash;
        this.passwordExpiresAt = LocalDateTime.now().plusDays(90);
        this.mustChangePassword = false;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Force password change on next login
     */
    public void forcePasswordChange() {
        this.mustChangePassword = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Deactivate account
     */
    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Activate account
     */
    public void activate() {
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update profile
     */
    public void updateProfile(String firstName, String lastName, String phone) {
        if (firstName != null && !firstName.isBlank()) {
            this.firstName = firstName;
        }
        if (lastName != null && !lastName.isBlank()) {
            this.lastName = lastName;
        }
        if (phone != null) {
            this.phone = phone;
        }
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update role
     */
    public void updateRole(UserRole newRole) {
        if (newRole == null) {
            throw new BusinessException(ErrorCodes.VALIDATION_ERROR, "Role cannot be null");
        }
        this.role = newRole;
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public TenantId getTenantId() { return tenantId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public UserRole getRole() { return role; }
    public boolean isActive() { return isActive; }
    public boolean isLocked() { return isLocked; }
    public int getFailedAttempts() { return failedAttempts; }
    public LocalDateTime getLockedUntil() { return lockedUntil; }
    public LocalDateTime getPasswordExpiresAt() { return passwordExpiresAt; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public boolean mustChangePassword() { return mustChangePassword; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private TenantId tenantId;
        private String username;
        private String email;
        private String passwordHash;
        private String firstName;
        private String lastName;
        private String phone;
        private UserRole role;
        private boolean mustChangePassword = false;

        public Builder(TenantId tenantId, String username, String email, String passwordHash) {
            this.tenantId = tenantId;
            this.username = username;
            this.email = email;
            this.passwordHash = passwordHash;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder mustChangePassword(boolean mustChangePassword) {
            this.mustChangePassword = mustChangePassword;
            return this;
        }

        public TenantUser build() {
            validate();
            return new TenantUser(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (username == null || username.isBlank()) throw new IllegalArgumentException("username is required");
            if (email == null || email.isBlank()) throw new IllegalArgumentException("email is required");
            if (passwordHash == null || passwordHash.isBlank()) throw new IllegalArgumentException("passwordHash is required");
            if (role == null) this.role = UserRole.TENANT_USER;
        }
    }
}

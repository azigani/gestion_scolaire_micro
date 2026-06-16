-- Migration V3: Create tenant_users table
-- This table stores user accounts within each tenant context

CREATE TABLE tenant_users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    
    -- Authentication
    username VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    
    -- Profile
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(20),
    
    -- Role and status
    role VARCHAR(50) NOT NULL DEFAULT 'TENANT_USER',
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    is_locked BOOLEAN NOT NULL DEFAULT FALSE,
    failed_attempts INTEGER NOT NULL DEFAULT 0,
    locked_until TIMESTAMP,
    
    -- Password management
    password_expires_at TIMESTAMP NOT NULL,
    last_login TIMESTAMP,
    must_change_password BOOLEAN NOT NULL DEFAULT FALSE,
    
    -- Audit
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    
    -- Constraints
    CONSTRAINT uk_tenant_email UNIQUE (tenant_id, email),
    CONSTRAINT uk_tenant_username UNIQUE (tenant_id, username)
);

-- Indexes
CREATE INDEX idx_tenant_users_tenant_id ON tenant_users(tenant_id);
CREATE INDEX idx_tenant_users_email ON tenant_users(email);
CREATE INDEX idx_tenant_users_role ON tenant_users(role);
CREATE INDEX idx_tenant_users_is_active ON tenant_users(is_active);

-- Update trigger
CREATE OR REPLACE FUNCTION update_tenant_users_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_tenant_users_updated_at
    BEFORE UPDATE ON tenant_users
    FOR EACH ROW
    EXECUTE FUNCTION update_tenant_users_updated_at();

-- Insert default super admin for testing (will be removed in production)
-- This is just for development purposes
INSERT INTO tenant_users (tenant_id, username, email, password_hash, first_name, last_name, role, password_expires_at)
SELECT id, 'admin', 'admin@edumanager.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/X4wJz8M8M9q5q5q5q', 'Super', 'Admin', 'TENANT_ADMIN', CURRENT_TIMESTAMP + INTERVAL '90 days'
FROM tenants
WHERE slug = 'demo'
ON CONFLICT DO NOTHING;

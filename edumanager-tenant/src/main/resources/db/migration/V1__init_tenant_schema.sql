-- ============================================================
-- V1__init_tenant_schema.sql
-- EduManager SaaS - Tenant Management Database Schema
-- ============================================================

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ============================================================
-- TENANTS TABLE
-- ============================================================
CREATE TABLE tenants (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug                VARCHAR(50)     NOT NULL UNIQUE,
    name                VARCHAR(200)    NOT NULL,
    domain              VARCHAR(200)    UNIQUE,
    subdomain           VARCHAR(100)    UNIQUE,
    logo_url            VARCHAR(500),
    primary_color       VARCHAR(7),
    secondary_color     VARCHAR(7),
    
    -- Contact Information
    contact_email       VARCHAR(255)    NOT NULL,
    contact_phone       VARCHAR(20),
    address             TEXT,
    city                VARCHAR(100),
    country             VARCHAR(100)    DEFAULT 'Burkina Faso',
    
    -- Configuration
    timezone            VARCHAR(50)     DEFAULT 'Africa/Ouagadougou',
    locale              VARCHAR(10)     DEFAULT 'fr',
    currency            VARCHAR(3)      DEFAULT 'XOF',
    
    -- Status
    status              VARCHAR(20)     NOT NULL DEFAULT 'PENDING',
    -- PENDING, ACTIVE, SUSPENDED, TERMINATED
    
    -- Subscription
    subscription_plan   VARCHAR(50)     NOT NULL DEFAULT 'STARTER',
    -- STARTER, PROFESSIONAL, ENTERPRISE
    subscription_start  DATE            NOT NULL,
    subscription_end    DATE,
    max_students        INTEGER         NOT NULL DEFAULT 100,
    max_teachers        INTEGER         NOT NULL DEFAULT 20,
    max_staff           INTEGER         NOT NULL DEFAULT 10,
    
    -- Database Configuration
    db_schema_name      VARCHAR(100)    NOT NULL UNIQUE,
    db_host             VARCHAR(255),
    db_port             INTEGER,
    db_name             VARCHAR(100),
    
    -- Audit
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    created_by          UUID,
    updated_by          UUID,
    
    CONSTRAINT chk_subscription_dates CHECK (subscription_end IS NULL OR subscription_end > subscription_start),
    CONSTRAINT chk_status CHECK (status IN ('PENDING', 'ACTIVE', 'SUSPENDED', 'TERMINATED')),
    CONSTRAINT chk_plan CHECK (subscription_plan IN ('STARTER', 'PROFESSIONAL', 'ENTERPRISE'))
);

CREATE INDEX idx_tenants_slug ON tenants(slug);
CREATE INDEX idx_tenants_status ON tenants(status);
CREATE INDEX idx_tenants_domain ON tenants(domain);
CREATE INDEX idx_tenants_subdomain ON tenants(subdomain);

-- ============================================================
-- TENANT CONFIGURATIONS
-- ============================================================
CREATE TABLE tenant_configurations (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id           UUID            NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    config_key          VARCHAR(100)    NOT NULL,
    config_value        TEXT,
    value_type          VARCHAR(20)     NOT NULL DEFAULT 'STRING',
    -- STRING, INTEGER, BOOLEAN, JSON
    description         TEXT,
    is_encrypted        BOOLEAN         NOT NULL DEFAULT FALSE,
    
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    
    CONSTRAINT uq_tenant_config UNIQUE (tenant_id, config_key)
);

CREATE INDEX idx_tenant_config_tenant ON tenant_configurations(tenant_id);

-- ============================================================
-- TENANT USERS (Super Admins)
-- ============================================================
CREATE TABLE tenant_users (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id           UUID            NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    
    -- Authentication
    username            VARCHAR(50)     NOT NULL,
    email               VARCHAR(255)    NOT NULL,
    password_hash       VARCHAR(255)    NOT NULL,
    
    -- Profile
    first_name          VARCHAR(100)    NOT NULL,
    last_name           VARCHAR(100)    NOT NULL,
    phone               VARCHAR(20),
    
    -- Role & Permissions
    role                VARCHAR(50)     NOT NULL DEFAULT 'TENANT_ADMIN',
    permissions         JSONB,
    
    -- Status
    is_active           BOOLEAN         NOT NULL DEFAULT TRUE,
    is_locked           BOOLEAN         NOT NULL DEFAULT FALSE,
    failed_attempts     SMALLINT        NOT NULL DEFAULT 0,
    locked_until        TIMESTAMPTZ,
    password_expires_at TIMESTAMPTZ     NOT NULL,
    last_login          TIMESTAMPTZ,
    must_change_pwd     BOOLEAN         NOT NULL DEFAULT FALSE,
    
    -- Audit
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    
    CONSTRAINT uq_tenant_user UNIQUE (tenant_id, email),
    CONSTRAINT uq_tenant_username UNIQUE (tenant_id, username),
    CONSTRAINT chk_role CHECK (role IN ('TENANT_ADMIN', 'TENANT_USER'))
);

CREATE INDEX idx_tenant_users_tenant ON tenant_users(tenant_id);
CREATE INDEX idx_tenant_users_email ON tenant_users(email);
CREATE INDEX idx_tenant_users_active ON tenant_users(is_active);

-- ============================================================
-- TENANT DATABASE SCHEMAS
-- ============================================================
-- This table tracks which database schemas exist for each tenant
CREATE TABLE tenant_schemas (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id           UUID            NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    schema_name         VARCHAR(100)    NOT NULL UNIQUE,
    schema_type         VARCHAR(20)     NOT NULL DEFAULT 'MAIN',
    -- MAIN, ARCHIVE, TEMP
    status              VARCHAR(20)     NOT NULL DEFAULT 'ACTIVE',
    size_bytes          BIGINT,
    last_migration      VARCHAR(20),
    
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    
    CONSTRAINT chk_schema_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'ARCHIVED'))
);

CREATE INDEX idx_tenant_schemas_tenant ON tenant_schemas(tenant_id);

-- ============================================================
-- AUDIT LOG
-- ============================================================
CREATE TABLE tenant_audit_log (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id           UUID            NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    
    entity_type         VARCHAR(100)    NOT NULL,
    entity_id           VARCHAR(100)    NOT NULL,
    action              VARCHAR(50)     NOT NULL,
    -- CREATE, UPDATE, DELETE, READ, LOGIN, LOGOUT
    
    old_value           JSONB,
    new_value           JSONB,
    
    performed_by        UUID,
    performed_by_email  VARCHAR(255),
    performed_at        TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    ip_address          INET,
    user_agent          TEXT,
    justification       TEXT,
    
    INDEX idx_audit_tenant (tenant_id),
    INDEX idx_audit_entity (entity_type, entity_id),
    INDEX idx_audit_user (performed_by),
    INDEX idx_audit_date (performed_at)
);

-- ============================================================
-- TENANT INVITATIONS
-- ============================================================
CREATE TABLE tenant_invitations (
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tenant_id           UUID            NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    
    email               VARCHAR(255)    NOT NULL,
    role                VARCHAR(50)     NOT NULL,
    permissions         JSONB,
    
    token               VARCHAR(255)    NOT NULL UNIQUE,
    expires_at          TIMESTAMPTZ     NOT NULL,
    accepted_at         TIMESTAMPTZ,
    rejected_at         TIMESTAMPTZ,
    
    invited_by          UUID,
    invited_by_email    VARCHAR(255),
    
    created_at          TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    
    CONSTRAINT chk_invitation_status CHECK (
        (accepted_at IS NULL AND rejected_at IS NULL) OR
        (accepted_at IS NOT NULL AND rejected_at IS NULL) OR
        (accepted_at IS NULL AND rejected_at IS NOT NULL)
    )
);

CREATE INDEX idx_invitations_tenant ON tenant_invitations(tenant_id);
CREATE INDEX idx_invitations_token ON tenant_invitations(token);
CREATE INDEX idx_invitations_email ON tenant_invitations(email);

-- ============================================================
-- FUNCTIONS FOR AUTOMATIC SCHEMA CREATION
-- ============================================================
CREATE OR REPLACE FUNCTION create_tenant_schema(schema_name VARCHAR)
RETURNS VOID AS $$
BEGIN
    EXECUTE format('CREATE SCHEMA IF NOT EXISTS %I', schema_name);
    
    -- Grant permissions
    EXECUTE format('GRANT ALL PRIVILEGES ON SCHEMA %I TO edumanager', schema_name);
    EXECUTE format('GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA %I TO edumanager', schema_name);
    EXECUTE format('ALTER DEFAULT PRIVILEGES IN SCHEMA %I GRANT ALL ON TABLES TO edumanager', schema_name);
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

-- ============================================================
-- TRIGGER FOR UPDATED_AT
-- ============================================================
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_tenants_updated_at BEFORE UPDATE ON tenants
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_tenant_configurations_updated_at BEFORE UPDATE ON tenant_configurations
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_tenant_users_updated_at BEFORE UPDATE ON tenant_users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_tenant_schemas_updated_at BEFORE UPDATE ON tenant_schemas
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

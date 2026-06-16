-- ============================================================
-- V2__seed_data.sql
-- EduManager SaaS - Seed Data
-- ============================================================

-- Insert default tenant configurations
INSERT INTO tenant_configurations (tenant_id, config_key, config_value, value_type, description) VALUES
-- These will be inserted when a tenant is created via the application
-- This is just a template for reference
(NULL, 'maintenance.mode', 'false', 'BOOLEAN', 'Enable maintenance mode'),
(NULL, 'registration.enabled', 'true', 'BOOLEAN', 'Allow new student registrations'),
(NULL, 'academic.year.current', '2024-2025', 'STRING', 'Current academic year'),
(NULL, 'grading.scale', '20', 'INTEGER', 'Maximum grade scale'),
(NULL, 'attendance.required.percentage', '75', 'INTEGER', 'Required attendance percentage'),
(NULL, 'password.min.length', '8', 'INTEGER', 'Minimum password length'),
(NULL, 'password.require.uppercase', 'true', 'BOOLEAN', 'Require uppercase in password'),
(NULL, 'password.require.lowercase', 'true', 'BOOLEAN', 'Require lowercase in password'),
(NULL, 'password.require.number', 'true', 'BOOLEAN', 'Require number in password'),
(NULL, 'password.require.special', 'true', 'BOOLEAN', 'Require special character in password'),
(NULL, 'session.timeout.minutes', '30', 'INTEGER', 'Session timeout in minutes'),
(NULL, 'max.file.size.mb', '10', 'INTEGER', 'Maximum file upload size in MB'),
(NULL, 'allowed.file.types', 'pdf,doc,docx,jpg,jpeg,png', 'STRING', 'Allowed file types'),
(NULL, 'notification.email.enabled', 'true', 'BOOLEAN', 'Enable email notifications'),
(NULL, 'notification.sms.enabled', 'false', 'BOOLEAN', 'Enable SMS notifications'),
(NULL, 'notification.sms.provider', 'twilio', 'STRING', 'SMS provider'),
(NULL, 'backup.retention.days', '90', 'INTEGER', 'Backup retention period'),
(NULL, 'audit.retention.days', '365', 'INTEGER', 'Audit log retention period')
ON CONFLICT DO NOTHING;

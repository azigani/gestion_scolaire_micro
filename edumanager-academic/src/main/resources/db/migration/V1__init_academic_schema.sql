-- Migration V1: Initialize academic schema
-- This schema handles subjects, grades, schedules, and report cards

-- Subjects
CREATE TABLE subjects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    
    code VARCHAR(20) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    coefficient INTEGER NOT NULL DEFAULT 1,
    is_core BOOLEAN NOT NULL DEFAULT TRUE,
    
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    
    CONSTRAINT uk_tenant_subject_code UNIQUE (tenant_id, code)
);

CREATE INDEX idx_subjects_tenant_id ON subjects(tenant_id);
CREATE INDEX idx_subjects_code ON subjects(code);
CREATE INDEX idx_subjects_is_core ON subjects(is_core);

-- Grade types (exams, assignments, etc.)
CREATE TABLE grade_types (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    
    name VARCHAR(100) NOT NULL,
    description TEXT,
    weight DECIMAL(5,2) NOT NULL DEFAULT 1.00,
    max_score DECIMAL(5,2) NOT NULL DEFAULT 20.00,
    
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_grade_types_tenant_id ON grade_types(tenant_id);

-- Grades
CREATE TABLE grades (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    
    student_id UUID NOT NULL,
    subject_id UUID NOT NULL,
    grade_type_id UUID NOT NULL,
    class_id UUID NOT NULL,
    academic_year_id UUID NOT NULL,
    
    score DECIMAL(5,2) NOT NULL,
    max_score DECIMAL(5,2) NOT NULL,
    comments TEXT,
    graded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    graded_by UUID NOT NULL,
    
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT uk_student_subject_grade_type UNIQUE (student_id, subject_id, grade_type_id, academic_year_id)
);

CREATE INDEX idx_grades_tenant_id ON grades(tenant_id);
CREATE INDEX idx_grades_student_id ON grades(student_id);
CREATE INDEX idx_grades_subject_id ON grades(subject_id);
CREATE INDEX idx_grades_class_id ON grades(class_id);
CREATE INDEX idx_grades_academic_year_id ON grades(academic_year_id);
CREATE INDEX idx_grades_graded_at ON grades(graded_at);

-- Schedule entries
CREATE TABLE schedule_entries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    
    class_id UUID NOT NULL,
    subject_id UUID NOT NULL,
    teacher_id UUID NOT NULL,
    room_id UUID,
    
    day_of_week INTEGER NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    academic_year_id UUID NOT NULL,
    
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    
    CONSTRAINT uk_class_day_time UNIQUE (class_id, day_of_week, start_time, end_time, academic_year_id)
);

CREATE INDEX idx_schedule_entries_tenant_id ON schedule_entries(tenant_id);
CREATE INDEX idx_schedule_entries_class_id ON schedule_entries(class_id);
CREATE INDEX idx_schedule_entries_subject_id ON schedule_entries(subject_id);
CREATE INDEX idx_schedule_entries_teacher_id ON schedule_entries(teacher_id);
CREATE INDEX idx_schedule_entries_day_of_week ON schedule_entries(day_of_week);
CREATE INDEX idx_schedule_entries_academic_year_id ON schedule_entries(academic_year_id);

-- Report cards
CREATE TABLE report_cards (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    
    student_id UUID NOT NULL,
    class_id UUID NOT NULL,
    academic_year_id UUID NOT NULL,
    term VARCHAR(50) NOT NULL,
    
    overall_average DECIMAL(5,2),
    class_rank INTEGER,
    total_students INTEGER,
    remarks TEXT,
    generated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    generated_by UUID NOT NULL,
    
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT uk_student_class_academic_year_term UNIQUE (student_id, class_id, academic_year_id, term)
);

CREATE INDEX idx_report_cards_tenant_id ON report_cards(tenant_id);
CREATE INDEX idx_report_cards_student_id ON report_cards(student_id);
CREATE INDEX idx_report_cards_class_id ON report_cards(class_id);
CREATE INDEX idx_report_cards_academic_year_id ON report_cards(academic_year_id);
CREATE INDEX idx_report_cards_term ON report_cards(term);

-- Report card subject details
CREATE TABLE report_card_subjects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL,
    
    report_card_id UUID NOT NULL REFERENCES report_cards(id) ON DELETE CASCADE,
    subject_id UUID NOT NULL,
    
    average DECIMAL(5,2),
    coefficient INTEGER,
    weighted_score DECIMAL(5,2),
    rank INTEGER,
    teacher_remarks TEXT,
    
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT uk_report_card_subject UNIQUE (report_card_id, subject_id)
);

CREATE INDEX idx_report_card_subjects_tenant_id ON report_card_subjects(tenant_id);
CREATE INDEX idx_report_card_subjects_report_card_id ON report_card_subjects(report_card_id);
CREATE INDEX idx_report_card_subjects_subject_id ON report_card_subjects(subject_id);

-- Update triggers
CREATE OR REPLACE FUNCTION update_subjects_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_subjects_updated_at
    BEFORE UPDATE ON subjects
    FOR EACH ROW
    EXECUTE FUNCTION update_subjects_updated_at();

CREATE OR REPLACE FUNCTION update_grade_types_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_grade_types_updated_at
    BEFORE UPDATE ON grade_types
    FOR EACH ROW
    EXECUTE FUNCTION update_grade_types_updated_at();

CREATE OR REPLACE FUNCTION update_grades_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_grades_updated_at
    BEFORE UPDATE ON grades
    FOR EACH ROW
    EXECUTE FUNCTION update_grades_updated_at();

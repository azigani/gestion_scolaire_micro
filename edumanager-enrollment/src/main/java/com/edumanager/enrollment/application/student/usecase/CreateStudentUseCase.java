package com.edumanager.enrollment.application.student.usecase;

import com.edumanager.enrollment.application.student.dto.request.CreateStudentRequest;
import com.edumanager.enrollment.application.student.dto.response.StudentResponse;
import com.edumanager.enrollment.application.student.mapper.StudentMapper;
import com.edumanager.enrollment.domain.student.entity.Student;
import com.edumanager.enrollment.domain.student.enums.StudentStatus;
import com.edumanager.enrollment.domain.student.repository.StudentRepository;
import com.edumanager.enrollment.domain.student.valueobject.StudentId;
import com.edumanager.enrollment.domain.student.valueobject.StudentNumber;
import com.edumanager.enrollment.exception.domain.BusinessException;
import com.edumanager.enrollment.constants.ErrorCodes;
import com.edumanager.enrollment.infrastructure.multitenancy.TenantContext;
import com.edumanager.enrollment.infrastructure.event.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use Case: Create Student
 * 
 * Orchestrates the creation of a new student.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateStudentUseCase {

    private final StudentRepository studentRepository;
    private final StudentMapper mapper;
    private final DomainEventPublisher eventPublisher;

    @Transactional
    public StudentResponse execute(CreateStudentRequest request) {
        log.info("Creating student with number: {}", request.studentNumber());

        // 1. Get tenant context
        UUID tenantId = TenantContext.getTenantId()
            .orElseThrow(() -> new BusinessException(ErrorCodes.VALIDATION_ERROR, "Tenant context not set"));

        // 2. Validate uniqueness
        StudentNumber studentNumber = new StudentNumber(request.studentNumber());
        if (studentRepository.existsByStudentNumber(studentNumber)) {
            throw new BusinessException(ErrorCodes.STUDENT_NUMBER_DUPLICATE,
                "Student number '" + request.studentNumber() + "' already exists");
        }

        // 3. Create domain entity
        Student student = new Student.Builder(
            tenantId,
            studentNumber,
            request.firstName(),
            request.lastName(),
            request.dateOfBirth(),
            request.gender()
        )
        .nationalId(request.nationalId())
        .photoUrl(request.photoUrl())
        .address(request.address())
        .city(request.city())
        .country(request.country())
        .enrollmentDate(request.enrollmentDate())
        .status(StudentStatus.ACTIVE)
        .build();

        // 4. Persist
        Student saved = studentRepository.save(student);

        // 5. Publish domain events
        saved.pullDomainEvents().forEach(eventPublisher::publish);

        log.info("Student created successfully: {}", saved.getStudentNumber().getValue());

        return mapper.toResponse(saved);
    }
}

package com.edumanager.enrollment.application.student.mapper;

import com.edumanager.enrollment.application.student.dto.response.StudentResponse;
import com.edumanager.enrollment.domain.student.entity.Student;
import org.springframework.stereotype.Component;

/**
 * Mapper between Domain Entity and DTOs for Student.
 */
@Component
public class StudentMapper {

    public StudentResponse toResponse(Student student) {
        return new StudentResponse(
            student.getId().toString(),
            student.getStudentNumber().getValue(),
            student.getFirstName(),
            student.getLastName(),
            student.getDateOfBirth(),
            student.getGender(),
            student.getNationalId(),
            student.getPhotoUrl(),
            student.getAddress(),
            student.getCity(),
            student.getCountry(),
            student.getEnrollmentDate(),
            student.getStatus().name(),
            student.getCreatedAt(),
            student.getUpdatedAt()
        );
    }
}

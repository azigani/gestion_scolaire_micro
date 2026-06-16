package com.edumanager.enrollment.application.student.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for student response.
 */
public record StudentResponse(

    String id,
    String studentNumber,
    String firstName,
    String lastName,
    LocalDate dateOfBirth,
    String gender,
    String nationalId,
    String photoUrl,
    String address,
    String city,
    String country,
    LocalDate enrollmentDate,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt

) {}

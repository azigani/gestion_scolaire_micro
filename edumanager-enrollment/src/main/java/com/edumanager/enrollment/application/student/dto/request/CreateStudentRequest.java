package com.edumanager.enrollment.application.student.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO for creating a new student.
 */
public record CreateStudentRequest(

    @NotBlank(message = "Student number is required")
    @Size(min = 8, max = 20, message = "Student number must be between 8 and 20 characters")
    String studentNumber,

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    String lastName,

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth,

    @NotBlank(message = "Gender is required")
    String gender,

    String nationalId,

    String photoUrl,

    String address,

    String city,

    String country,

    LocalDate enrollmentDate

) {}

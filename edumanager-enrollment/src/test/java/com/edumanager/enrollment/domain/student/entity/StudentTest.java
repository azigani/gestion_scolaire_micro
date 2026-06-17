package com.edumanager.enrollment.domain.student.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Student Entity Tests")
class StudentTest {

    @Test
    @DisplayName("Should create student with valid data")
    void shouldCreateStudentWithValidData() {
        Student student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("+1234567890")
                .dateOfBirth("2000-01-01")
                .gender("MALE")
                .address("123 Main St")
                .city("New York")
                .country("USA")
                .build();

        assertNotNull(student);
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("john.doe@example.com", student.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when first name is null")
    void shouldThrowExceptionWhenFirstNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            Student.builder()
                    .firstName(null)
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .build();
        });
    }

    @Test
    @DisplayName("Should throw exception when email is invalid")
    void shouldThrowExceptionWhenEmailIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            Student.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("invalid-email")
                    .build();
        });
    }

    @Test
    @DisplayName("Should enroll student successfully")
    void shouldEnrollStudentSuccessfully() {
        Student student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        student.enroll();

        assertEquals(StudentStatus.ENROLLED, student.getStatus());
    }

    @Test
    @DisplayName("Should graduate student successfully")
    void shouldGraduateStudentSuccessfully() {
        Student student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .status(StudentStatus.ENROLLED)
                .build();

        student.graduate();

        assertEquals(StudentStatus.GRADUATED, student.getStatus());
    }
}

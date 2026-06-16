package com.edumanager.enrollment.presentation.controller;

import com.edumanager.enrollment.application.student.dto.request.CreateStudentRequest;
import com.edumanager.enrollment.application.student.dto.response.StudentResponse;
import com.edumanager.enrollment.application.student.usecase.CreateStudentUseCase;
import com.edumanager.enrollment.presentation.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Student management.
 */
@Tag(name = "Students", description = "Student management endpoints")
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final CreateStudentUseCase createStudentUseCase;

    @Operation(summary = "Create a new student")
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(
            @Valid @RequestBody CreateStudentRequest request) {
        
        StudentResponse response = createStudentUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, "Student created successfully"));
    }
}

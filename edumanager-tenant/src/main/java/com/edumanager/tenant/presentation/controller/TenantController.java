package com.edumanager.tenant.presentation.controller;

import com.edumanager.tenant.application.tenant.dto.request.CreateTenantRequest;
import com.edumanager.tenant.application.tenant.dto.response.TenantResponse;
import com.edumanager.tenant.application.tenant.usecase.CreateTenantUseCase;
import com.edumanager.tenant.presentation.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Tenant management.
 */
@Tag(name = "Tenants", description = "Tenant management endpoints")
@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final CreateTenantUseCase createTenantUseCase;

    @Operation(summary = "Create a new tenant")
    @PostMapping
    public ResponseEntity<ApiResponse<TenantResponse>> createTenant(
            @Valid @RequestBody CreateTenantRequest request) {
        
        TenantResponse response = createTenantUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(response, "Tenant created successfully"));
    }
}

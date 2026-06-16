package com.edumanager.hr.domain.employee.repository;

import com.edumanager.hr.domain.employee.entity.Employee;
import com.edumanager.hr.domain.employee.enums.EmployeeStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository Interface: EmployeeRepository
 * Defines the contract for employee persistence operations.
 */
public interface EmployeeRepository {

    Employee save(Employee employee);

    Optional<Employee> findById(UUID id);

    Optional<Employee> findByEmployeeNumber(String employeeNumber);

    List<Employee> findByTenantId(UUID tenantId);

    List<Employee> findByStatus(EmployeeStatus status);

    List<Employee> findByDepartment(String department);

    List<Employee> findByEmployeeTypeId(UUID employeeTypeId);

    boolean existsByEmployeeNumber(String employeeNumber);

    void deleteById(UUID id);
}

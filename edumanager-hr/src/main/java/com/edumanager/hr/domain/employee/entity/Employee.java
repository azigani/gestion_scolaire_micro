package com.edumanager.hr.domain.employee.entity;

import com.edumanager.hr.domain.employee.enums.EmployeeStatus;
import com.edumanager.hr.domain.shared.AggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Aggregate Root: Employee
 * 
 * Represents a teacher or staff member.
 * Contains business rules for employee management.
 * 
 * Business Rules:
 * - RG-EMP01: Employee number must be unique within tenant
 * - RG-EMP02: Hire date cannot be in the future
 * - RG-EMP03: Termination date cannot be before hire date
 * - RG-EMP04: Salary must be positive
 */
public class Employee extends AggregateRoot {

    private final UUID id;
    private final UUID tenantId;
    
    private String employeeNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String country;
    
    private UUID employeeTypeId;
    private String department;
    private String position;
    private LocalDate hireDate;
    private LocalDate terminationDate;
    
    private BigDecimal salary;
    private String currency;
    
    private EmployeeStatus status;
    
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Employee(Builder builder) {
        this.id = builder.id != null ? builder.id : UUID.randomUUID();
        this.tenantId = builder.tenantId;
        this.employeeNumber = builder.employeeNumber;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.gender = builder.gender;
        this.nationality = builder.nationality;
        this.phone = builder.phone;
        this.email = builder.email;
        this.address = builder.address;
        this.city = builder.city;
        this.country = builder.country;
        this.employeeTypeId = builder.employeeTypeId;
        this.department = builder.department;
        this.position = builder.position;
        this.hireDate = builder.hireDate;
        this.terminationDate = builder.terminationDate;
        this.salary = builder.salary;
        this.currency = builder.currency != null ? builder.currency : "XOF";
        this.status = builder.status != null ? builder.status : EmployeeStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // BUSINESS BEHAVIORS
    // ==========================================

    /**
     * Terminate employee
     */
    public void terminate(LocalDate terminationDate) {
        if (this.status == EmployeeStatus.TERMINATED || this.status == EmployeeStatus.RESIGNED) {
            throw new IllegalStateException("Employee is already terminated or resigned");
        }
        if (terminationDate != null && terminationDate.isBefore(this.hireDate)) {
            throw new IllegalArgumentException("Termination date cannot be before hire date");
        }
        this.terminationDate = terminationDate != null ? terminationDate : LocalDate.now();
        this.status = EmployeeStatus.TERMINATED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Suspend employee
     */
    public void suspend() {
        if (this.status == EmployeeStatus.TERMINATED || this.status == EmployeeStatus.RESIGNED) {
            throw new IllegalStateException("Cannot suspend a terminated or resigned employee");
        }
        this.status = EmployeeStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Activate employee
     */
    public void activate() {
        if (this.status == EmployeeStatus.TERMINATED || this.status == EmployeeStatus.RESIGNED) {
            throw new IllegalStateException("Cannot activate a terminated or resigned employee");
        }
        this.status = EmployeeStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update salary
     */
    public void updateSalary(BigDecimal newSalary) {
        if (newSalary == null || newSalary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }
        this.salary = newSalary;
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public UUID getId() { return id; }
    public UUID getTenantId() { return tenantId; }
    public String getEmployeeNumber() { return employeeNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getNationality() { return nationality; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public UUID getEmployeeTypeId() { return employeeTypeId; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public LocalDate getHireDate() { return hireDate; }
    public LocalDate getTerminationDate() { return terminationDate; }
    public BigDecimal getSalary() { return salary; }
    public String getCurrency() { return currency; }
    public EmployeeStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ==========================================
    // BUILDER
    // ==========================================

    public static class Builder {
        private UUID id;
        private UUID tenantId;
        private String employeeNumber;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String gender;
        private String nationality;
        private String phone;
        private String email;
        private String address;
        private String city;
        private String country;
        private UUID employeeTypeId;
        private String department;
        private String position;
        private LocalDate hireDate;
        private LocalDate terminationDate;
        private BigDecimal salary;
        private String currency;
        private EmployeeStatus status;

        public Builder(UUID tenantId, String employeeNumber, String firstName, 
                     String lastName, UUID employeeTypeId, LocalDate hireDate) {
            this.tenantId = tenantId;
            this.employeeNumber = employeeNumber;
            this.firstName = firstName;
            this.lastName = lastName;
            this.employeeTypeId = employeeTypeId;
            this.hireDate = hireDate;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder nationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Builder position(String position) {
            this.position = position;
            return this;
        }

        public Builder terminationDate(LocalDate terminationDate) {
            this.terminationDate = terminationDate;
            return this;
        }

        public Builder salary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder status(EmployeeStatus status) {
            this.status = status;
            return this;
        }

        public Employee build() {
            validate();
            return new Employee(this);
        }

        private void validate() {
            if (tenantId == null) throw new IllegalArgumentException("tenantId is required");
            if (employeeNumber == null || employeeNumber.isBlank()) throw new IllegalArgumentException("employeeNumber is required");
            if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("firstName is required");
            if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("lastName is required");
            if (employeeTypeId == null) throw new IllegalArgumentException("employeeTypeId is required");
            if (hireDate == null) throw new IllegalArgumentException("hireDate is required");
            if (hireDate.isAfter(LocalDate.now())) throw new IllegalArgumentException("hireDate cannot be in the future");
        }
    }
}

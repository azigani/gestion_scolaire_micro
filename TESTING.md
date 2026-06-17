# Testing Guide for EduManager

This document provides comprehensive testing guidelines for the EduManager microservices architecture.

## Test Structure

### Backend Tests (Spring Boot)

Each microservice includes:
- **Unit Tests**: Test individual components in isolation
- **Integration Tests**: Test interactions between components
- **Controller Tests**: Test REST API endpoints
- **Repository Tests**: Test database operations

### Frontend Tests (Angular)

The frontend includes:
- **Unit Tests**: Test services, components, and interceptors
- **Integration Tests**: Test component interactions
- **E2E Tests**: Test user flows end-to-end

## Running Tests

### Backend Tests

```bash
# Run tests for a specific microservice
cd edumanager-tenant
mvn test

# Run all tests with coverage
mvn test jacoco:report

# Run integration tests
mvn verify
```

### Frontend Tests

```bash
cd edumanager-frontend

# Install dependencies
npm install

# Run unit tests
npm test

# Run tests with coverage
npm run test:coverage

# Run e2e tests
npm run e2e
```

## Test Coverage Goals

- **Backend**: Minimum 80% code coverage
- **Frontend**: Minimum 70% code coverage

## Test Categories

### Unit Tests

Test individual classes and methods without external dependencies.

**Example:**
```java
@Test
@DisplayName("Should create tenant with valid data")
void shouldCreateTenantWithValidData() {
    Tenant tenant = Tenant.builder()
            .name("Test School")
            .subdomain("testschool")
            .build();
    
    assertNotNull(tenant);
    assertEquals("Test School", tenant.getName());
}
```

### Integration Tests

Test interactions between multiple components.

**Example:**
```java
@SpringBootTest
@AutoConfigureMockMvc
class TenantControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldCreateTenantSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/tenants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tenantJson))
                .andExpect(status().isCreated());
    }
}
```

### E2E Tests

Test complete user flows from UI to backend.

**Example:**
```typescript
it('should login successfully', () => {
    page.navigateTo();
    page.getEmailInput().sendKeys('test@example.com');
    page.getPasswordInput().sendKeys('password');
    page.getLoginButton().click();
    
    expect(page.getDashboardTitle()).toContain('Welcome');
});
```

## Test Data Management

### Test Database

- Use H2 in-memory database for unit tests
- Use test PostgreSQL instance for integration tests
- Flyway migrations run automatically for test schema

### Test Data Fixtures

Create reusable test data fixtures:

```java
public class TenantFixture {
    public static Tenant createValidTenant() {
        return Tenant.builder()
                .name("Test School")
                .subdomain("testschool")
                .email("admin@testschool.com")
                .status(TenantStatus.ACTIVE)
                .build();
    }
}
```

## Continuous Integration

Tests run automatically on:
- Every pull request
- Every push to main/develop branches
- Before deployment to production

## Best Practices

1. **Write tests before or with code** (TDD approach)
2. **Keep tests independent** - no test should depend on another
3. **Use descriptive test names** - explain what is being tested
4. **Mock external dependencies** - databases, APIs, file systems
5. **Test edge cases** - null values, empty strings, boundary conditions
6. **Keep tests fast** - unit tests should run in milliseconds
7. **Maintain test data** - keep fixtures up to date with schema changes

## Troubleshooting

### Common Issues

**Tests failing due to database connection:**
- Ensure test database is running
- Check application-test.yml configuration

**Flaky tests:**
- Add proper cleanup in @AfterEach
- Use deterministic test data
- Avoid time-dependent assertions

**Slow tests:**
- Mock external services
- Use in-memory databases for unit tests
- Parallelize test execution

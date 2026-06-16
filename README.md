# EduManager SaaS - Multi-tenant School Management System

## 🏫 Overview

EduManager SaaS is an enterprise-grade, multi-tenant school management system designed to serve multiple educational institutions through a single platform. Built with Clean Architecture, Domain-Driven Design (DDD), and microservices principles.

## 🎯 Architecture Highlights

### Multi-Tenancy Strategy
- **Database-per-Tenant**: Complete isolation for security and compliance
- **Tenant Context**: Automatic tenant resolution from JWT tokens
- **Row-Level Security**: PostgreSQL RLS for additional data protection
- **Tenant-Specific Schemas**: Each tenant has isolated database schema

### Architectural Patterns
- **Clean Architecture**: Domain-driven, dependency inversion
- **Domain-Driven Design**: Bounded contexts, aggregates, value objects
- **CQRS**: Separate read/write models where appropriate
- **Event-Driven**: Domain events for cross-service communication
- **Microservices**: Modular, independently deployable services

### Technology Stack

#### Backend
- **Java 21** with Spring Boot 3.2
- **Spring Cloud** for microservices
- **PostgreSQL 16** with Flyway migrations
- **Redis** for caching and session management
- **Apache Kafka** for event streaming
- **JWT** for stateless authentication
- **MapStruct** for object mapping

#### Frontend
- **Angular 17** with standalone components
- **Angular Material** for UI components
- **RxJS** for reactive programming
- **ngx-translate** for internationalization

#### DevOps
- **Docker** & Docker Compose
- **Kubernetes** for orchestration
- **GitHub Actions** for CI/CD
- **Prometheus** & Grafana for monitoring
- **ELK Stack** for logging

## 📁 Project Structure

```
edumanager-saas/
├── edumanager-tenant/          # Tenant management service
├── edumanager-enrollment/      # Student enrollment module
├── edumanager-academic/       # Academic management (grades, schedules)
├── edumanager-financial/      # Financial management (fees, payments)
├── edumanager-hr/             # Human resources (teachers, staff)
├── edumanager-communication/  # Messaging and notifications
├── edumanager-gateway/        # API Gateway
└── edumanager-frontend/       # Angular frontend
```

## 🚀 Quick Start

### Prerequisites
- Java 21
- Maven 3.9+
- Docker & Docker Compose
- Node.js 20+ (for frontend)
- PostgreSQL 16+
- Redis 7+

### 1. Clone and Build
```bash
git clone <repository-url>
cd edumanager-saas
mvn clean install
```

### 2. Start Infrastructure
```bash
docker-compose up -d postgres redis kafka zookeeper
```

### 3. Initialize Database
```bash
# Run migrations for tenant service
cd edumanager-tenant
mvn flyway:migrate
```

### 4. Start Services
```bash
# Start tenant service first
cd edumanager-tenant
mvn spring-boot:run

# Start other services in separate terminals
cd ../edumanager-enrollment
mvn spring-boot:run

cd ../edumanager-academic
mvn spring-boot:run

# ... etc
```

### 5. Start Frontend
```bash
cd edumanager-frontend
npm install
ng serve
```

## 🔐 Multi-Tenant Security

### Tenant Resolution
1. JWT token contains `tenantId` claim
2. `TenantContextFilter` extracts and sets tenant context
3. `TenantAwareRepository` automatically filters by tenant
4. PostgreSQL RLS enforces tenant isolation at database level

### Authentication Flow
```
1. POST /api/v1/auth/login
   → { email, password, tenantSlug }

2. Validate credentials against tenant's user table
   → Verify tenant exists and is active

3. Generate JWT with tenantId
   → { userId, tenantId, roles, permissions }

4. All subsequent requests include JWT
   → Tenant context automatically resolved
   → Data automatically filtered by tenant
```

## 📊 Bounded Contexts

### Enrollment Context
- Student registration
- Class assignments
- Guardian management
- Enrollment history

### Academic Context
- Course management
- Grade recording
- Attendance tracking
- Schedule management
- Report cards

### Financial Context
- Fee structures
- Payment processing
- Invoice generation
- Financial reports

### HR Context
- Teacher management
- Staff management
- Contract management
- Payroll

### Communication Context
- Internal messaging
- Notifications
- Announcements
- Email/SMS integration

## 🧪 Testing

### Unit Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn verify -Pintegration
```

### E2E Tests
```bash
cd edumanager-frontend
npm run e2e
```

## 📦 Deployment

### Docker
```bash
docker build -t edumanager-tenant:latest ./edumanager-tenant
docker-compose -f docker-compose.prod.yml up -d
```

### Kubernetes
```bash
kubectl apply -f k8s/
```

## 📖 Documentation

- [Architecture Guide](docs/architecture/README.md)
- [API Documentation](docs/api/README.md)
- [Developer Guide](docs/developer/README.md)
- [Deployment Guide](docs/deployment/README.md)
- [Architecture Decision Records](docs/adr/)

## 🔧 Configuration

### Environment Variables
```bash
# Database
DB_URL=jdbc:postgresql://localhost:5432/edumanager
DB_USERNAME=edumanager
DB_PASSWORD=your-password

# Security
JWT_SECRET=your-256-bit-secret
JWT_EXPIRATION=3600

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379

# Kafka
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Team

- **Architecture**: Principal Software Architect
- **Development**: Full-stack Development Team
- **DevOps**: DevOps Engineering Team

## 📞 Support

For support, email support@edumanager.com or create an issue in the repository.

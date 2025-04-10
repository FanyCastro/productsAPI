# 🚀 ProductsAPI - Kotlin Product Management System

![Java CI](https://img.shields.io/github/actions/workflow/status/FanyCastro/productsAPI/maven.yml?logo=github&label=Build)
![Coverage](https://img.shields.io/codecov/c/github/FanyCastro/productsAPI/main?logo=codecov&label=Coverage)
![License](https://img.shields.io/github/license/FanyCastro/productsAPI?color=blue)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.2-blueviolet?logo=kotlin)

A high-performance REST API for product management built with Kotlin and Spring Boot. Perfect for e-commerce backends or Kotlin learning projects.

## ✨ Key Features

- **Modern Stack**: Spring Boot 3 + Kotlin 1.9
- **Multi-Database**: H2 (dev) 
- **Advanced Search**: Filter by name, category, price range
- **Full Test Coverage**: 95%+ with unit & integration tests

## ✨ Key Improvements

- Replace H2 in-memory database with PostgreSQL for production environments
- Add connection pooling configuration for better performance
- Add authentication and authorization (JWT/OAuth2)
- Add chaos testing to verify system resilience
- Add structured logging with correlation IDs
- Set up metrics collection for business KPIs 
- Implement health checks and readiness probes

## 📚 API Documentation

This project provides automatic API documentation through Swagger UI and OpenAPI 3.0 specifications.

### Accessing Documentation
1. Swagger UI (Interactive)
   - Local Development: visit this URL in your browser while the application is running http://localhost:8080/swagger-ui/index.html
2. OpenAPI JSON
   - Access the raw OpenAPI specification at: http://localhost:8080/v3/api-docs (Use this for API clients or code generation tools)
   - For a specific API group (if applicable): http://localhost:8080/v3/api-docs/{group-name}

### Core Endpoints

| Method | Endpoint                | Description                  |
|--------|-------------------------|------------------------------|
| GET    | `/api/products`         | List all products            |

### Additional Features
- Pagination: `?page=1&size=20`
- Sorting: `?sort=price,desc`
- Search: `?name=phone&minPrice=100`

## 🛠️ Installation

### Prerequisites
- Java 17+
- Maven 3.8+

### Quick Start
```bash
git clone https://github.com/FanyCastro/productsAPI.git
cd productsAPI
mvn spring-boot:run
```

##  🧪 Testing Suite

Run the complete test suite with coverage:

```bash
mvn clean verify
```
##  🏗️ Project Structure

This project follows Hexagonal Architecture (also known as Ports & Adapters), a design pattern that emphasizes separation of concerns by isolating the core business logic from external concerns like databases, UIs, or third-party services.

- **Unit Tests**: Verify individual components in isolation
- **Integration Tests**: Validate interactions between components
- **Architecture Tests**: Enforce hexagonal architecture rules
- **BDD Tests**: Business scenarios using Cucumber

```
src/
├── main/
│   ├── kotlin/
│   │   ├── com/capitole/productsapi/
│   │   │   ├── domain/            # Pure business logic
│   │   │   │   ├── model/         # Domain entities (e.g., Product)
│   │   │   │   └── ports/         # Interfaces (e.g., ProductRepositoryPort)
│   │   │   ├── application/       # Use cases and application services
│   │   │   └── infrastructure/    # Technological adapters
│   │   │       ├── web/           # Controllers (REST adapters)
│   │   │       └── persistence/   # Database implementations
│   │   └── ProductsapiApplication.kt
└── test/
│   ├── kotlin/
│   │   ├── com/capitole/productsapi/
│   │   │   ├── architecture/  # ArchUnit tests
│   │   │   ├── bdd/           # Cucumber tests
│   │   │   └── unit/          # Unit and integration test
└──
```
### Key Components in This Repo

| Layer       | Location                                  | Responsibility                                                                               |
|-------------|-------------------------------------------|----------------------------------------------------------------------------------------------|
| Domain      | `com.capitole.productsapi.domain`         | Contains pure business logic, entities, and domain rules.                                    |
| Application | `com.capitole.productsapi.application`    | Orchestrates use cases, implements business workflows.                                       |
| Ports       | `com.capitole.productsapi.domain.ports`   | Interfaces defining how the domain interacts with external systems (input/output contracts). |
| Adapters    | `com.capitole.productsapi.infrastructure` | Concrete implementations of ports (e.g., databases, web controllers, external APIs).         |

## 🔄 Flow of Control
1. Inbound (Driving Side):
   - HTTP Requests → Controllers (in `infrastructure.web`) → Use Cases (in `application`) → Domain Logic.
2. Outbound (Driven Side):
   - Domain Logic → Port Interfaces → Adapters (e.g., database repositories in `infrastructure.persistence`).

##  📊 Code Quality
The project uses SonarCloud for code analysis. You can view the results at:
https://sonarcloud.io/project/overview?id=FanyCastro_productsAPI

Key metrics tracked:
   - Code coverage
   - Vulnerability detection
   - Code smells

Security hotspots

## 🤝 Contributing
We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature)
5. Open a Pull Request

## 📜 License
Distributed under the Apache License. See [LICENSE](http://www.apache.org/licenses/) for more information.
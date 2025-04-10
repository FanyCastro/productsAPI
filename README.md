# 🚀 ProductsAPI - Kotlin Product Management System

![Java CI](https://img.shields.io/github/actions/workflow/status/FanyCastro/productsAPI/maven.yml?logo=github&label=Build)
![Coverage](https://img.shields.io/codecov/c/github/FanyCastro/productsAPI/main?logo=codecov&label=Coverage)
![License](https://img.shields.io/github/license/FanyCastro/productsAPI?color=blue)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blueviolet?logo=kotlin)

A high-performance REST API for product management built with Kotlin and Spring Boot. Perfect for e-commerce backends or Kotlin learning projects.

## ✨ Key Features

- **Modern Stack**: Spring Boot 3 + Kotlin 1.9
- **Multi-Database**: H2 (dev) 
- **Advanced Search**: Filter by name, category, price range
- **Full Test Coverage**: 95%+ with unit & integration tests

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

The project follows Hexagonal Architecture (Ports & Adapters) principles, organizing code into clearly defined layers:

```
src/
├── main/
│   ├── kotlin/com/example/productsapi/
│   │   ├── application/       # Use cases and application services
│   │   ├── domain/            # Core business logic
│   │   ├── infrastructure/    # Technological adapters
│   │   └── ProductsapiApplication.kt
└── test/                      # Unit and integration tests
```

## 🤝 Contributing
We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature)
5. Open a Pull Request

## 📜 License
Distributed under the Apache License. See [LICENSE](http://www.apache.org/licenses/) for more information.
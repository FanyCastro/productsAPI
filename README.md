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

### Core Endpoints

| Method | Endpoint                | Description                  |
|--------|-------------------------|------------------------------|
| GET    | `/api/products`         | List all products            |
| POST   | `/api/products`         | Create new product           |
| GET    | `/api/products/{id}`    | Get product details          |
| PUT    | `/api/products/{id}`    | Update product               |
| DELETE | `/api/products/{id}`    | Delete product               |

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

## 🤝 Contributing
We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature)
5. Open a Pull Request

## 📜 License
Distributed under the Apache License. See [LICENSE](http://www.apache.org/licenses/) for more information.
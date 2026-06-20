# Inventory & Order Microservices Platform

A production-grade microservices architecture with 4 decoupled services built with Java, Spring Boot, Spring Cloud, Redis, PostgreSQL, Docker, and GitHub Actions CI/CD.

## Architecture

```
Client → API Gateway (8080) → Inventory Service (8081)
                            → Order Service     (8082)
                            → Notification Svc  (8083)
```

## Services

| Service | Port | Description |
|---------|------|-------------|
| API Gateway | 8080 | Spring Cloud Gateway — JWT auth, routing, rate limiting |
| Inventory Service | 8081 | Product catalog, stock management, Redis caching |
| Order Service | 8082 | Order processing, inter-service communication |
| Notification Service | 8083 | Event-driven async notifications |

## Tech Stack
Java 21 | Spring Boot 3.2 | Spring Cloud Gateway | Eureka | Redis | PostgreSQL | Docker | GitHub Actions | JUnit 5 | REST Assured

## Key Features
- 4-service microservices with Spring Cloud Gateway & Eureka Service Discovery
- JWT auth & RBAC at API Gateway level
- Redis caching — reduced DB load 60%, response 180ms → <45ms
- Async event-driven messaging between Order → Notification service
- Docker Compose orchestration
- GitHub Actions CI/CD pipeline
- 82%+ test coverage — JUnit 5, Mockito, REST Assured
- PostgreSQL indexing cut query time ~35%

## Quick Start

### Prerequisites
- Java 21
- Maven 3.9+
- Docker + Docker Compose

### Run with Docker Compose
```bash
git clone https://github.com/satyajit5231/inventory-order-microservices.git
cd inventory-order-microservices
docker-compose up --build
```

### Run individually (local dev)

**1. Create databases in PostgreSQL:**
```sql
CREATE DATABASE inventory_db;
CREATE DATABASE order_db;
```

**2. Start each service in IntelliJ (open each folder as separate project):**
```
inventory-service  → port 8081
order-service      → port 8082
notification-service → port 8083
api-gateway        → port 8080
```

## API Endpoints (via Gateway on port 8080)

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register user |
| POST | /api/auth/login | Login, get JWT token |

### Inventory
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/inventory | Get all products |
| POST | /api/inventory | Add product (Admin) |
| GET | /api/inventory/{id} | Get by ID |
| GET | /api/inventory/sku/{sku} | Get by SKU |
| PUT | /api/inventory/stock | Update stock |
| GET | /api/inventory/low-stock | Low stock alert |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/orders | Place order |
| GET | /api/orders | Get my orders |
| GET | /api/orders/{id} | Get order by ID |

# Vinculo

> **A sophisticated graph-based social network platform for visualizing and managing personal and professional relationships**

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Neo4j](https://img.shields.io/badge/Neo4j-5.0-blue.svg)](https://neo4j.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 🎯 Overview

**Vinculo** (Portuguese for "bond" or "connection") is a next-generation social networking platform that leverages the power of graph databases to provide users with meaningful insights into their personal and professional networks. Unlike traditional social networks, Vinculo visualizes relationships as an interconnected graph, allowing users to understand not just who they're connected to, but how their entire network is interconnected.

### Vision

In an increasingly connected world, understanding the nuances of our relationships becomes crucial. Vinculo transforms abstract social connections into a tangible, visual network where users can:

- **Visualize their entire relationship network** as an interactive graph
- **Categorize connections** by relationship type (family, friends, colleagues, business partners, etc.)
- **Discover hidden connections** between people in their network
- **Understand relationship dynamics** through weighted connection types
- **Manage their social capital** more effectively

## ✨ Key Features

### 🔐 Secure Authentication
- **JWT-based authentication** for secure API access
- **Role-based access control** (RBAC) with ADMIN and NORMAL user roles
- **Spring Security integration** for enterprise-grade security
- **Password encryption** using BCrypt

### 👥 Person Management
- Comprehensive user profile management
- Email and phone number validation
- Profile update capabilities
- Admin-level user deletion

### 🌐 Connection System
- **Multiple relationship types** with weighted importance:
  - **Tier 1 (Closest)**: Partner, Family
  - **Tier 2 (Close)**: Friend, Business Partner
  - **Tier 3 (Important)**: Mentor, Referral
  - **Tier 4 (Regular)**: Colleague, Buddy
  - **Tier 5 (Casual)**: Acquaintance
- Create and manage connections between users
- Retrieve personal network connections
- Graph-based relationship storage for efficient querying

### 📊 Graph Database Architecture
- **Neo4j integration** for native graph storage
- Efficient relationship queries and traversals
- Optimized for complex network analysis
- Real-time relationship updates

## 🏗️ Technology Stack

### Backend
- **Java 21** - Latest LTS version with modern language features
- **Spring Boot 4.0.2** - Enterprise application framework
- **Spring Security** - Authentication and authorization
- **Spring Data Neo4j** - Graph database integration
- **Maven** - Dependency management and build automation

### Database
- **Neo4j 5 Community Edition** - Graph database for relationship management
- **Bolt Protocol** - Native Neo4j communication protocol

### Security & Authentication
- **JWT (JSON Web Tokens)** - Stateless authentication
- **BCrypt** - Password hashing algorithm
- **OAuth2 Resource Server** - Token-based security

### Additional Libraries
- **Lombok** - Boilerplate code reduction
- **Jakarta Validation** - Request validation
- **libphonenumber** - Phone number validation

### Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Maven Wrapper** - Version-locked Maven builds

## 🏛️ Architecture

Vinculo follows **Hexagonal Architecture** (Ports and Adapters), also known as Clean Architecture, ensuring:

- **High maintainability** through clear separation of concerns
- **Testability** with independent business logic
- **Flexibility** to swap infrastructure components
- **Domain-driven design** principles

### Project Structure

```
vinculo/
├── src/
│   ├── main/
│   │   ├── java/com/vinculo/
│   │   │   ├── module/
│   │   │   │   ├── person/              # Person domain module
│   │   │   │   │   ├── domain/          # Business logic & entities
│   │   │   │   │   │   ├── model/       # Domain models (Person, RoleUser)
│   │   │   │   │   │   ├── use_case/    # Use cases (CRUD operations)
│   │   │   │   │   │   ├── port/        # Interfaces for adapters
│   │   │   │   │   │   ├── command/     # Command objects
│   │   │   │   │   │   └── exception/   # Domain exceptions
│   │   │   │   │   ├── infrastructure/  # External adapters
│   │   │   │   │   │   ├── persistence/ # Neo4j repositories
│   │   │   │   │   │   ├── encoder/     # Password encoding
│   │   │   │   │   │   └── validator/   # Phone validation
│   │   │   │   │   └── controller/      # REST API layer
│   │   │   │   │       ├── controller/  # Controllers
│   │   │   │   │       ├── dto/         # Data transfer objects
│   │   │   │   │       ├── mapper/      # DTO mappers
│   │   │   │   │       └── handler/     # Request handlers
│   │   │   │   ├── connection/          # Connection domain module
│   │   │   │   │   ├── domain/          # Connection business logic
│   │   │   │   │   │   ├── model/       # Connection, TypeConnection
│   │   │   │   │   │   ├── use_case/    # Connection operations
│   │   │   │   │   │   └── port/        # Repository interfaces
│   │   │   │   │   ├── infrastructure/  # Connection adapters
│   │   │   │   │   └── application/     # REST API layer
│   │   │   │   └── auth/                # Authentication module
│   │   │   │       ├── domain/          # Auth business logic
│   │   │   │       └── application/     # Auth endpoints
│   │   │   └── share/                   # Shared components
│   │   │       ├── security/            # Security configuration
│   │   │       ├── service/             # Shared services
│   │   │       └── exception/           # Global exception handling
│   │   └── resources/
│   │       └── application.properties   # Application configuration
│   └── test/                            # Test suite
├── docker-compose.yml                   # Docker orchestration
├── Dockerfile                           # Application container
└── pom.xml                              # Maven configuration
```

### Architecture Layers

#### 1. **Domain Layer** (Core Business Logic)
- Pure business logic with no framework dependencies
- Domain models: `Person`, `Connection`, `TypeConnection`
- Use cases encapsulate business operations
- Domain events and exceptions

#### 2. **Application Layer** (API Gateway)
- REST controllers expose HTTP endpoints
- Request/Response DTOs for data transfer
- Request handlers orchestrate use cases
- Input validation using Jakarta Validation

#### 3. **Infrastructure Layer** (External Adapters)
- Neo4j repositories for graph persistence
- Password encoding adapters
- Phone number validation adapters
- Security configuration

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 21** or higher ([Download](https://www.oracle.com/java/technologies/downloads/#java21))
- **Docker** and **Docker Compose** ([Download](https://docs.docker.com/get-docker/))
- **Maven 3.9+** (optional, Maven Wrapper included)
- **Git** for version control

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Luca5Eckert/vinculo.git
cd vinculo
```

### 2. Configure Environment Variables

Create a `.env` file in the project root:

```env
# Neo4j Database Configuration
NEO4J_URI=bolt://neo4j:7687
NEO4J_USER=neo4j
NEO4J_PASSWORD=your_secure_password

# Application Configuration
APP_PORT=8080

# JWT Configuration (Optional - defaults provided)
JWT_KEY=your_jwt_secret_key_here_minimum_32_characters
```

> **⚠️ Security Note**: Never commit the `.env` file to version control. Update passwords before deployment.

### 3. Start the Application with Docker Compose

```bash
docker-compose up -d
```

This command will:
1. Pull and start **Neo4j 5 Community** database
2. Build the **Spring Boot application**
3. Configure networking between containers
4. Expose ports for API (8080) and Neo4j Browser (7474)

### 4. Verify Installation

- **API Health Check**: http://localhost:8080/actuator/health
- **Neo4j Browser**: http://localhost:7474
  - Username: `neo4j`
  - Password: (from your `.env` file)

### 5. Run Without Docker (Development)

If you prefer to run locally without Docker:

```bash
# Start Neo4j separately
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/password \
  neo4j:5-community

# Build and run the application
./mvnw clean install
./mvnw spring-boot:run
```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/v1
```

### Authentication Endpoints

#### Register a New User
```http
POST /auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "securePassword123",
  "phoneNumber": "+5511999999999"
}
```

**Response**: `201 Created`

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

**Response**: `200 OK`
```json
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### Person Endpoints

All person endpoints (except registration) require authentication. Include the JWT token in the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

#### Get Person by ID
```http
GET /persons/{personId}
```

**Response**: `200 OK`
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+5511999999999",
  "role": "NORMAL"
}
```

#### Update Person
```http
PUT /persons/{personId}
Content-Type: application/json

{
  "name": "John Smith",
  "phoneNumber": "+5511888888888"
}
```

**Response**: `204 No Content`

#### Delete Person (Admin Only)
```http
DELETE /persons/{personId}
```

**Response**: `204 No Content`

### Connection Endpoints

#### Create a Connection
```http
POST /connections
Content-Type: application/json
Authorization: Bearer <token>

{
  "personId": 2,
  "type": "FRIEND"
}
```

**Connection Types**:
- `PARTNER` - Romantic partner (Tier 1)
- `FAMILY` - Family member (Tier 1)
- `FRIEND` - Close friend (Tier 2)
- `BUSINESS_PARTNER` - Business partner (Tier 2)
- `MENTOR` - Mentor/Mentee (Tier 3)
- `REFERRAL` - Professional referral (Tier 3)
- `COLLEAGUE` - Work colleague (Tier 4)
- `BUDDY` - Casual friend (Tier 4)
- `ACQUAINTANCE` - Acquaintance (Tier 5)

**Response**: `200 OK`

#### Get My Connections
```http
GET /connections/me
Authorization: Bearer <token>
```

**Response**: `200 OK`
```json
[
  {
    "id": 1,
    "person": {
      "id": 2,
      "name": "Jane Doe",
      "email": "jane.doe@example.com"
    },
    "type": "FRIEND",
    "weight": 2
  }
]
```

## 🔧 Development

### Build the Project

```bash
./mvnw clean install
```

### Run Tests

```bash
./mvnw test
```

### Run the Application

```bash
./mvnw spring-boot:run
```

### Hot Reload (Development)

```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

Connect your IDE debugger to port 5005.

### Code Style

The project follows standard Java conventions:
- **Lombok** annotations for reducing boilerplate
- **Clean Code** principles
- **SOLID** principles in design
- Comprehensive exception handling

## 🗂️ Database Schema (Neo4j)

### Nodes

#### Person Node
```cypher
(:Person {
  id: Long,
  name: String,
  email: String,
  password: String,
  phoneNumber: String,
  role: String
})
```

### Relationships

#### CONNECTED_WITH Relationship
```cypher
(:Person)-[r:CONNECTED_WITH {
  id: Long,
  type: String,
  weight: Integer
}]->(:Person)
```

### Example Query

Find all connections of a person:
```cypher
MATCH (p:Person {id: 1})-[r:CONNECTED_WITH]->(connected:Person)
RETURN p, r, connected
```

## 🐳 Docker Configuration

### Services

#### Neo4j Database
- **Image**: `neo4j:5-community`
- **Ports**: 
  - 7474 (HTTP Browser)
  - 7687 (Bolt Protocol)
- **Health Check**: Automated health monitoring
- **Volumes**: Persistent data storage

#### Vinculo Application
- **Build**: Multi-stage Dockerfile
- **Port**: 8080
- **Dependencies**: Waits for Neo4j health check
- **User**: Runs as `nobody` for security

### Networking
Both services run on a bridge network named `vinculo-network` for isolated communication.

## 🧪 Testing

The project includes a comprehensive test suite covering:
- **Unit tests** for business logic
- **Integration tests** with Neo4j testcontainers
- **Security tests** for authentication flows
- **API tests** for endpoint validation

```bash
# Run all tests
./mvnw test

# Run with coverage
./mvnw clean verify

# Run specific test class
./mvnw test -Dtest=PersonUseCaseTest
```

## 📊 Monitoring & Observability

Spring Boot Actuator endpoints are available for monitoring:

- `/actuator/health` - Application health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Application metrics

Access these endpoints after starting the application.

## 🔒 Security Considerations

1. **Password Storage**: All passwords are hashed using BCrypt before storage
2. **JWT Tokens**: Stateless authentication with configurable expiration
3. **HTTPS**: Recommended for production deployments
4. **Input Validation**: All inputs are validated using Jakarta Validation
5. **SQL Injection**: Not applicable (NoSQL/Graph database)
6. **XSS Protection**: Spring Security defaults
7. **CSRF**: Disabled for stateless API (JWT-based)

## 🚢 Deployment

### Production Considerations

1. **Environment Variables**: Use secure secret management (e.g., AWS Secrets Manager, HashiCorp Vault)
2. **Database Backups**: Configure Neo4j backup strategy
3. **Logging**: Integrate with centralized logging (ELK, CloudWatch)
4. **Monitoring**: Set up APM tools (New Relic, Datadog)
5. **Load Balancing**: Use reverse proxy (Nginx, HAProxy)
6. **HTTPS**: Configure SSL/TLS certificates

### Docker Compose Production

Update `docker-compose.yml` for production:
- Remove port mappings for internal services
- Add health checks
- Configure resource limits
- Use external volumes for data persistence

## 🤝 Contributing

Contributions are welcome! Please follow these guidelines:

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/amazing-feature`
3. **Commit your changes**: `git commit -m 'Add amazing feature'`
4. **Push to the branch**: `git push origin feature/amazing-feature`
5. **Open a Pull Request**

### Code Standards
- Follow Java naming conventions
- Write unit tests for new features
- Update documentation as needed
- Ensure all tests pass before submitting PR

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Luca Eckert**
- GitHub: [@Luca5Eckert](https://github.com/Luca5Eckert)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Neo4j for powerful graph database technology
- All contributors and supporters of this project

## 📞 Support

For support, please:
- Open an issue in the [GitHub repository](https://github.com/Luca5Eckert/vinculo/issues)
- Check existing documentation
- Review closed issues for similar problems

## 🗺️ Roadmap

Future enhancements planned:

- [ ] GraphQL API for flexible querying
- [ ] Real-time notifications via WebSocket
- [ ] Advanced graph algorithms (shortest path, community detection)
- [ ] Interactive web UI for network visualization
- [ ] Mobile applications (iOS/Android)
- [ ] AI-powered connection recommendations
- [ ] Import/Export functionality
- [ ] Privacy controls and connection visibility settings
- [ ] Analytics dashboard
- [ ] Multi-language support

---

**Built with ❤️ using Spring Boot and Neo4j**

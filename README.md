# Authentication Spring Boot

A Spring Boot application for user authentication using Spring Security and JWT (JSON Web Tokens).

## 🚀 Getting Started

### Prerequisites
- Java JDK 17+
- Maven
- (Optional) PostgreSQL or any other relational database
- (Optional) Flyway for database migrations
- Lombok, Spring Data JPA, Spring Security

### Installation & Run

```bash
# Clone the repository
git clone https://github.com/Exzustic/authentication-spring-boot.git
cd authentication-spring-boot

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080`

## 📌 API Usage

### User Registration
```
POST /auth/register
Content-Type: application/json

{
  "name": "Example User",
  "email": "user@example.com",
  "password": "securePassword",
  "role": "USER"
}
```

### User Login (Authentication)
```
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword"
}
```
If successful, the server returns a JWT token.

### Accessing Protected Endpoints
Include the JWT in the `Authorization` header:
```
Authorization: Bearer <JWT_TOKEN>
```

## ⚙️ Configuration

Example `application.properties`:
```properties
# Database settings
spring.datasource.url=jdbc:postgresql://localhost:5432/dbname
spring.datasource.username=...
spring.datasource.password=...

spring.jpa.hibernate.ddl-auto=update

# JWT settings
jwt.secret=yourSecretKey
jwt.expiration-ms=3600000
```

## 🛠 Technologies & Libraries
- Spring Boot — Main framework
- Spring Web — REST API
- Spring Data JPA — Database access
- Spring Security — Authentication and authorization
- JWT (e.g., `io.jsonwebtoken` or Auth0 Java JWT)
- Lombok — Reduce boilerplate code
- (Optional) Flyway — Database schema versioning

## 🔄 How It Works

1. The user sends registration or login data.
2. On login, the system validates the credentials and generates a JWT.
3. The JWT is used for accessing protected endpoints.

## 🧪 Testing
You can test the API using Postman, curl, or any HTTP client.

Example curl request for login:
```bash
curl -X POST http://localhost:8080/auth/login   -H "Content-Type: application/json"   -d '{"email":"user@example.com","password":"securePassword"}'
```

## 🤝 Contributing
1. Fork this repository
2. Create a new branch (`git checkout -b feature/awesome-feature`)
3. Commit your changes (`git commit -am 'Add awesome feature'`)
4. Push to the branch (`git push origin feature/awesome-feature`)
5. Create a Pull Request

## 📄 License

This project is licensed under the [MIT License](LICENSE) (replace with the actual license if different).

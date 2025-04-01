# 👤 User Management Service (Spring Boot + Hexagonal Architecture)

This project is a microservice built with **Java 21**, **Spring Boot**, and **Hexagonal Architecture (Ports & Adapters)**.  
It exposes a simple REST API to manage users and demonstrates solid practices in design, validation, testing, and persistence.

---

## 🚀 Technologies Used

- Java 21
- Spring Boot 3.4.4
- Spring Data JPA
- H2 Database (in-memory for testing)
- Jakarta Bean Validation (`@Valid`)
- Hexagonal Architecture (Clean Architecture principles)
- JUnit 5, Mockito, Spring MockMvc
- Maven

---

## 📁 Project Structure

```
src/
🔺 domain/             # Domain entities and interfaces (ports)
🔺 application/        # Use cases (business logic)
🔺 infrastructure/
    🔺 rest/           # REST controllers (input adapters)
    🔺 persistence/    # JPA adapter and entity mapping
    🔺 exception/      # Centralized error handling
🔺 test/
    🔺 application/    # Unit tests for use cases
    🔺 infrastructure/
        🔺 rest/       # Controller tests (MockMvc)
        🔺 persistence/# Repository integration tests (H2 + JPA)
```

---

## 📬 API Endpoints

### ➕ Create User

```http
POST /users
Content-Type: application/json
```

**Request Body:**

```json
{
  "name": "Jean",
  "email": "jeancaardo@gmail.com"
}
```

**Response:**  
Returns the created user with a generated `id` and `createdAt` timestamp.

---

### 🔍 Get User by ID

```http
GET /users/{id}
```

Returns the user data if found.  
Returns `404` with error message if the user does not exist.

---

### 📋 Get All Users with Sorting

```http
GET /users?field=createdAt&order=desc
```

**Query Parameters:**

| Parameter | Description               | Default |
|-----------|---------------------------|---------|
| `field`   | Field to sort by (`name`, `email`, `createdAt`) | `createdAt` |
| `order`   | Sort direction (`asc`, `desc`)                  | `desc`      |

Returns a list of all users sorted by the given field and direction.  
Returns `400` if invalid `field` or `order` values are provided.

---

## 🥪 Testing

### Covered:

- ✅ Use case unit tests (`CreateUserService`, `GetUserService`, `GetAllUsersService`)
- ✅ REST controller tests (success + validation + error paths)
- ✅ JPA repository adapter tests (with in-memory H2)
- ✅ Global exception handling and validation error mapping
- ✅ 90% of line coverage

To run tests:

```bash
./mvnw test
```

---

## 🧠 Design Highlights

- ✅ Clean, layered architecture using **Hexagonal structure**
- ✅ Separation of domain logic, adapters, and infrastructure
- ✅ DTOs used for request/response mapping
- ✅ Strong validation and centralized error handling
- ✅ Fully testable and modular

---

## 📂 Next Improvements (Optional)

- Replace H2 with MySQL/PostgreSQL for persistence
- Add Docker + Docker Compose for local containers
- Add Kafka producer (as originally planned)
- Add OpenAPI (Swagger) documentation

---

## 📃 License

This project is provided for demonstration purposes under the MIT License.


# Testing Demo

Small Spring Boot project for teaching:
- unit testing with Mockito
- web/controller testing with MockMvc and `@WebMvcTest`
- system/integration testing with `@SpringBootTest`

## Requirements
- Java 17+
- Maven 3.9+

## Run the application

```bash
mvn spring-boot:run
```

## Run all tests

```bash
mvn test
```

## Endpoints
- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`

# Software-Testing-Demo

This repository is a compact classroom demo for showing the difference between **unit testing** and **system testing** in Spring Boot.

## Repository purpose

The application exposes a small REST API for books:
- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`

It contains three kinds of tests:
- **Unit tests** for the service layer with Mockito
- **Web layer tests** with `@WebMvcTest` and `MockMvc`
- **System/integration tests** with `@SpringBootTest` and `MockMvc`

This matches common Spring testing guidance: unit tests isolate one class without starting Spring, `@WebMvcTest` focuses on the controller layer, and `@SpringBootTest` loads the full application context.

## 1. Prepare the demo

1. Make sure Java 17 and Maven are installed.
2. Unzip the project.
3. Open a terminal in the project root.
4. Run:

```bash
java -version
mvn -version
```

## 2. Start the application manually

Run:

```bash
mvn spring-boot:run
```

Then in a second terminal:

```bash
curl http://localhost:8080/api/books
curl http://localhost:8080/api/books/1
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Domain-Driven Design","author":"Eric Evans","year":2003}'
```


## 3. Show the project structure

Explain these packages:
- `controller` — REST endpoints
- `service` — business logic
- `repository` — simple in-memory data access
- `dto` — request validation object
- `exception` — custom exception

Then explain the test folders:
- `src/test/java/.../service` — unit tests
- `src/test/java/.../controller` — web layer tests
- `src/test/java/.../integration` — system tests

## 4. Demonstrate the unit test

Open `BookServiceTest`.

Explain:
- `@ExtendWith(MockitoExtension.class)` enables Mockito in JUnit 5.
- `@Mock` creates a fake repository.
- `@InjectMocks` creates the service under test and injects the mock.
- No Spring context is started.

Run only the service test:

```bash
mvn -Dtest=BookServiceTest test
```

Discussion point:
A unit test is fast and isolated. It checks the service logic only.

## 5. Demonstrate the controller/web test

Open `BookControllerWebMvcTest`.

Explain:
- `@WebMvcTest(BookController.class)` loads only the web layer.
- `MockMvc` sends fake HTTP requests without starting a real server.
- `@MockBean` replaces the service dependency.

Run only the controller test:

```bash
mvn -Dtest=BookControllerWebMvcTest test
```

Discussion point:
This is bigger than a unit test because Spring MVC is involved, but smaller than a full system test.

## 6. Demonstrate the system test

Open `BookSystemTest`.

Explain:
- `@SpringBootTest` loads the full Spring application context.
- `@AutoConfigureMockMvc` provides `MockMvc` for end-to-end request testing.
- The controller, service, repository, validation, and exception handler work together.

Run only the system test:

```bash
mvn -Dtest=BookSystemTest test
```

Discussion point:
This is close to the real running application. It verifies collaboration between components.

## 7. Run all tests together

```bash
mvn test
```

Use this to show that different test types can coexist in one project.

## 8. Suggested student tasks

After the demo, students can:
1. Add a new validation rule.
2. Add a failing test first, then implement the feature.
3. Add a new endpoint and cover it with all three test styles.
4. Add a custom exception and test the error response.

## 10. Commands summary

```bash
mvn spring-boot:run
mvn test
mvn -Dtest=BookServiceTest test
mvn -Dtest=BookControllerWebMvcTest test
mvn -Dtest=BookSystemTest test
```

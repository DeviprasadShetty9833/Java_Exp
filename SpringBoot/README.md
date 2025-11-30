Springboot folder structure

```
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com
    │   │   │       └── ecogauge
    │   │   │           ├── config
    │   │   │           ├── controller
    │   │   │           ├── model
    │   │   │           ├── repository
    │   │   │           ├── security
    │   │   │           ├── service
    │   │   │           ├── EcoGaugeApplication.java
    │   │   └── resources
    │   │       ├── application.properties
    │   │       ├── static
    │   │       └── templates
    │   └── test
    │       └── java
    │           └── com
    │               └── ecogauge
    │                   └── EcoGaugeApplicationTests.java
    └── target
```


# **Spring Boot REST API Viva Theory**

## **1. Spring Boot Fundamentals**

### **Q: What is Spring Boot?**
**A:** Spring Boot is an opinionated framework that simplifies Spring application development by:
- **Auto-configuration**: Automatically configures Spring based on dependencies
- **Starter dependencies**: Pre-configured dependency sets
- **Embedded servers**: Built-in Tomcat, Jetty, or Undertow
- **Production-ready features**: Metrics, health checks, externalized configuration

### **Q: What are Spring Boot starters?**
**A:** Starters are dependency descriptors that bundle common dependencies:
- **spring-boot-starter-web**: Web applications, REST APIs
- **spring-boot-starter-data-jpa**: JPA with Hibernate
- **spring-boot-starter-validation**: Bean validation
- **spring-boot-starter-test**: Testing dependencies

### **Q: What is Spring Initializr?**
**A:** A web-based tool (start.spring.io) to bootstrap Spring Boot projects with selected dependencies and configuration.

---

## **2. REST Architecture**

### **Q: What is REST?**
**A:** Representational State Transfer (REST) is an architectural style for designing networked applications:
- **Stateless**: No client context stored on server
- **Client-Server**: Separation of concerns
- **Cacheable**: Responses can be cached
- **Uniform Interface**: Consistent API design
- **Layered System**: Architecture with multiple layers

### **Q: What are REST principles?**
**A:**
- **Resources**: Everything is a resource (students, books)
- **URI**: Unique resource identifiers (`/api/students`)
- **HTTP Methods**: GET, POST, PUT, DELETE
- **Representation**: JSON, XML response format
- **Stateless**: Each request contains all information

### **Q: REST vs SOAP?**
**A:**
| **REST** | **SOAP** |
|----------|----------|
| Uses HTTP methods | Uses XML protocol |
| JSON/XML format | XML only |
| Stateless | Can be stateful |
| Lightweight | Heavyweight |
| Easy to implement | Complex |

---

## **3. Spring MVC Annotations**

### **Q: Explain key Spring Boot annotations**
**A:**

**Controller Layer:**
```java
@RestController          // = @Controller + @ResponseBody
@RequestMapping("/api")  // Base URL mapping
@GetMapping("/students") // HTTP GET method
@PostMapping             // HTTP POST method  
@PutMapping              // HTTP PUT method
@DeleteMapping           // HTTP DELETE method
@PathVariable            // URL path parameters
@RequestBody             // Request body to object
@Valid                   // Bean validation
```

**Service Layer:**
```java
@Service                 // Business logic component
@Transactional           // Transaction management
```

**Data Layer:**
```java
@Entity                  // JPA entity
@Repository              // Data access component
```

### **Q: What is @RestController vs @Controller?**
**A:**
- **@Controller**: Returns view names for MVC applications
- **@RestController**: Returns data directly (JSON/XML) for REST APIs
- **@RestController** = @Controller + @ResponseBody

---

## **4. Spring Data JPA**

### **Q: What is Spring Data JPA?**
**A:** An abstraction layer over JPA that reduces boilerplate code:
- **Automatic repository implementation**
- **Method name query derivation**
- **Pagination and sorting support**
- **Custom query with @Query**

### **Q: How does JpaRepository work?**
**A:**
```java
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Inherits CRUD methods: save(), findById(), findAll(), deleteById()
    Student findByRollNumber(String rollNumber); // Query derivation
}
```

### **Q: What is Hibernate/JPA Entity?**
**A:**
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String rollNumber;
    // ...
}
```

---

## **5. Database Configuration**

### **Q: What is H2 Database?**
**A:** An in-memory Java SQL database perfect for development and testing:
- **Zero configuration**: Runs in memory
- **Fast**: No disk I/O
- **Web console**: Access via `/h2-console`
- **File-based**: Can persist data

### **Q: Explain application.properties configuration**
**A:**
```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:studentdb
spring.h2.console.enabled=true

# JPA Configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

### **Q: What is ddl-auto?**
**A:** Controls database schema generation:
- **create**: Drops and creates schema
- **update**: Updates schema
- **validate**: Validates schema
- **create-drop**: Creates on startup, drops on shutdown
- **none**: No action

---

## **6. Validation**

### **Q: Why use validation?**
**A:** 
- **Data integrity**: Ensure valid data enters system
- **Security**: Prevent malicious data
- **User experience**: Clear error messages
- **Business rules**: Enforce domain constraints

### **Q: Common validation annotations**
**A:**
```java
@NotNull                    // Cannot be null
@NotBlank                  // Not null and not empty
@Email                     // Valid email format
@Min(1) @Max(4)           // Numeric range
@DecimalMin("0.0")        // Decimal range
@DecimalMax("10.0")
@Size(min=2, max=50)      // String length
@Pattern(regexp="")       // Regular expression
```

### **Q: How to handle validation errors?**
**A:**
```java
@PostMapping
public Student create(@Valid @RequestBody Student student) {
    // Automatically returns 400 Bad Request if validation fails
    return service.create(student);
}
```

---

## **7. Exception Handling**

### **Q: How to handle exceptions in Spring Boot?**
**A:**
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(StudentNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Handle validation errors
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
```

### **Q: Common HTTP Status Codes**
**A:**
- **200 OK**: Successful GET
- **201 Created**: Successful POST
- **400 Bad Request**: Invalid input
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error

---

## **8. Layered Architecture**

### **Q: Why use layered architecture?**
**A:**
- **Separation of Concerns**: Each layer has specific responsibility
- **Maintainability**: Easy to modify one layer without affecting others
- **Testability**: Each layer can be tested independently
- **Reusability**: Business logic can be reused

### **Q: Explain the layers**
**A:**

**Controller Layer (StudentController):**
- Handles HTTP requests/responses
- Input validation
- Error handling

**Service Layer (StudentService):**
- Business logic
- Transaction management
- Data transformation

**Repository Layer (StudentRepository):**
- Data access
- Database operations
- Query execution

**Entity Layer (Student):**
- Data model
- Database mapping
- Validation rules

---

## **9. Testing REST APIs**

### **Q: How to test REST APIs?**
**A:**
- **Postman**: GUI tool for API testing
- **curl**: Command-line tool
- **Spring Boot Test**: Integration testing
- **Swagger/OpenAPI**: API documentation and testing

### **Q: Sample curl commands**
**A:**
```bash
# GET all students
curl -X GET http://localhost:8080/api/students

# POST new student
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"rollNumber":"CS001","firstName":"John","email":"john@example.com","year":2,"gpa":8.5}'

# PUT update student
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John Updated","gpa":9.0}'

# DELETE student
curl -X DELETE http://localhost:8080/api/students/1
```

---

## **10. Best Practices**

### **Q: REST API design best practices**
**A:**
- **Use nouns for resources**: `/students` not `/getStudents`
- **Use HTTP methods properly**: GET for read, POST for create, etc.
- **Version your API**: `/api/v1/students`
- **Use proper status codes**
- **Provide consistent error responses**
- **Use pagination for large datasets**
- **Secure your API**: Authentication/Authorization

### **Q: How to improve this student API?**
**A:**
- **DTOs**: Use Data Transfer Objects instead of entities
- **Pagination**: Add pageable responses
- **Search/filter**: Add query parameters
- **HATEOAS**: Hypermedia links
- **Caching**: Response caching
- **Security**: Spring Security integration
- **Documentation**: Swagger/OpenAPI
- **Logging**: Proper request/response logging

---

## **11. DTO Pattern**

### **Q: Why use DTOs?**
**A:**
```java
// Request DTO
public class StudentRequest {
    private String rollNumber;
    private String firstName;
    // ... without id field
}

// Response DTO  
public class StudentResponse {
    private Long id;
    private String rollNumber;
    private String firstName;
    // ... with id field
}
```
**Benefits:**
- **Separation**: Separate API model from database model
- **Security**: Control exposed fields
- **Performance**: Select only needed fields
- **Versioning**: Handle API changes

---

## **Quick Revision Checklist:**

✅ **Spring Boot**: Auto-configuration, starters, embedded server  
✅ **REST Principles**: Resources, HTTP methods, stateless  
✅ **Annotations**: @RestController, @GetMapping, @PostMapping, etc.  
✅ **Spring Data JPA**: Repository pattern, entity mapping  
✅ **Validation**: @Valid, @NotBlank, @Email, etc.  
✅ **Exception Handling**: @ControllerAdvice, proper status codes  
✅ **Testing**: Postman, curl commands  
✅ **Layered Architecture**: Controller → Service → Repository  

## **Common Viva Questions:**
- "What is the difference between @Controller and @RestController?"
- "How does Spring Data JPA reduce boilerplate code?"
- "Why use validation annotations?"
- "How would you handle pagination in this API?"
- "What are the advantages of using DTOs?"

**Remember**: Always relate answers to the student management example - it makes Spring Boot concepts more concrete and practical!

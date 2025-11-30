# **Spring MVC with Thymeleaf Viva Theory**

## **1. Spring MVC Architecture**

### **Q: What is Spring MVC?**
**A:** Spring MVC is a web framework that implements the Model-View-Controller pattern:
- **Model**: Contains application data (Student object)
- **View**: Renders model data (Thymeleaf templates)
- **Controller**: Handles user requests and prepares model

### **Q: Explain the request flow in Spring MVC**
**A:**
1. **Client sends** HTTP request to `/students/new`
2. **DispatcherServlet** receives request and finds appropriate controller
3. **Controller** processes request, prepares model, returns view name
4. **ViewResolver** finds Thymeleaf template
5. **Thymeleaf** renders HTML with model data
6. **Response sent** back to client

---

## **2. Thymeleaf Template Engine**

### **Q: What is Thymeleaf and why use it?**
**A:** Thymeleaf is a Java template engine for web and standalone environments:
- **Natural Templates**: HTML files can be opened directly in browsers
- **Spring Integration**: Tight integration with Spring MVC
- **Expression Language**: Powerful Spring EL support
- **Extensible**: Custom dialects and processors

### **Q: Key Thymeleaf attributes**
**A:**
```html
<!-- Template declaration -->
<html xmlns:th="http://www.thymeleaf.org">

<!-- URL expressions -->
<th:action="@{/students}" th:object="${student}">

<!-- Field binding -->
<th:field="*{firstName}">

<!-- Error display -->
<th:errors="*{firstName}">

<!-- Iteration -->
<th:each="s : ${students}">

<!-- Text output -->
<th:text="${s.firstName}">
```

---

## **3. Form Handling in Spring MVC**

### **Q: What is form backing object?**
**A:** A Java object that holds form data and is bound to HTML form:
```java
// In controller
model.addAttribute("student", new Student());

// In template
<form th:object="${student}" th:action="@{/students}" method="post">
    <input th:field="*{firstName}" />
</form>
```

### **Q: Explain @ModelAttribute**
**A:** Binds method parameters or method return values to named model attributes:
```java
@GetMapping("/new")
public String showForm(Model model) {
    model.addAttribute("student", new Student());  // Explicit
    return "students/form";
}

// OR using @ModelAttribute
@ModelAttribute("student")
public Student getStudent() {
    return new Student();
}
```

---

## **4. Validation Framework**

### **Q: How does server-side validation work?**
**A:**
1. **Annotations** on model fields define validation rules
2. **@Valid** triggers validation in controller
3. **BindingResult** holds validation results
4. **Thymeleaf** displays errors using `th:errors`

### **Q: Common validation annotations**
**A:**
```java
@NotBlank(message = "First name is required")
private String firstName;

@Email(message = "Enter valid email")
private String email;

@Min(1) @Max(4)
private Integer year;

@Size(min=2, max=50)
private String department;

@Pattern(regexp="[A-Z]{2}\\d{3}")
private String rollNumber;
```

### **Q: How to handle validation in controller?**
**A:**
```java
@PostMapping
public String submitForm(@Valid @ModelAttribute("student") Student student,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes) {
    
    if (bindingResult.hasErrors()) {
        return "students/form";  // Return to form with errors
    }
    
    // Process valid data
    studentStore.add(student);
    redirectAttributes.addFlashAttribute("successMessage", 
                                        "Student registered successfully!");
    return "redirect:/students";
}
```

---

## **5. Redirect-After-Post Pattern**

### **Q: What is Redirect-After-Post and why use it?**
**A:** A web development pattern where:
- **POST** request processes form data
- **Redirect** to GET request after processing
- **Prevents** duplicate form submission on browser refresh

### **Q: How to implement Redirect-After-Post?**
**A:**
```java
@PostMapping
public String submitForm(/* parameters */) {
    // Process form data
    return "redirect:/students";  // Redirect to GET endpoint
}
```

### **Q: What are flash attributes?**
**A:** Temporary attributes that survive redirects:
```java
redirectAttributes.addFlashAttribute("successMessage", 
                                    "Student registered successfully!");
```
**Usage in template:**
```html
<div th:if="${successMessage}">
    <strong th:text="${successMessage}"></strong>
</div>
```

---

## **6. Thymeleaf Form Binding**

### **Q: How to bind form fields to object properties?**
**A:**
```html
<form th:object="${student}" th:action="@{/students}" method="post">
    <!-- Text input -->
    <input th:field="*{firstName}" />
    
    <!-- Number input -->
    <input type="number" th:field="*{year}" min="1" max="4" />
    
    <!-- Email input -->
    <input type="email" th:field="*{email}" />
    
    <!-- Display errors -->
    <div th:if="${#fields.hasErrors('firstName')}" 
         th:errors="*{firstName}"></div>
</form>
```

### **Q: What is the difference between ${} and *{}?**
**A:**
- **${}**: Variable expressions (context variables)
- ***{}**: Selection expressions (form object properties)

```html
<!-- Context variable -->
<th:block th:object="${student}">
    <!-- Form object property -->
    <input th:field="*{firstName}" />
</th:block>
```

---

## **7. Error Handling in Forms**

### **Q: How to display field-level errors?**
**A:**
```html
<div>
    <label>First Name:</label>
    <input th:field="*{firstName}" />
    <div th:if="${#fields.hasErrors('firstName')}" 
         th:errors="*{firstName}"></div>
</div>
```

### **Q: How to display global errors?**
**A:**
```html
<div th:if="${#fields.hasGlobalErrors()}">
    <ul>
        <li th:each="err : ${#fields.globalErrors()}" 
            th:text="${err}"></li>
    </ul>
</div>
```

### **Q: How to apply CSS classes for errors?**
**A:**
```html
<input th:field="*{firstName}" 
       th:classappend="${#fields.hasErrors('firstName')} ? 'error-field'" />
```

---

## **8. Spring Data JPA Integration (Optional)**

### **Q: How to persist form data?**
**A:**
```java
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(unique = true)
    private String rollNumber;
    // ... other fields
}

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

@Service
public class StudentService {
    private final StudentRepository repository;
    
    public Student save(Student student) {
        return repository.save(student);
    }
    
    public List<Student> findAll() {
        return repository.findAll();
    }
}
```

---

## **9. Configuration**

### **Q: Important application.properties settings**
**A:**
```properties
# Thymeleaf configuration
spring.thymeleaf.cache=false  # Disable cache for development
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html

# H2 Database (if using persistence)
spring.datasource.url=jdbc:h2:mem:studentdb
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

---

## **10. Best Practices**

### **Q: Form handling best practices**
**A:**
- **Always validate** on server side (client-side is optional)
- **Use Redirect-After-Post** to prevent duplicate submissions
- **Provide clear error messages** near the problematic fields
- **Use flash attributes** for success/status messages
- **Keep controllers thin** - delegate business logic to services

### **Q: Thymeleaf best practices**
**A:**
- **Use fragments** for reusable template parts
- **Externalize messages** for internationalization
- **Use layout dialects** for consistent page structure
- **Keep logic in controllers** - templates should focus on presentation

---

## **Quick Revision Checklist:**

✅ **Spring MVC**: Model-View-Controller architecture  
✅ **Thymeleaf**: Template engine with natural templates  
✅ **Form Binding**: th:object, th:field for object binding  
✅ **Validation**: @Valid, BindingResult, error display  
✅ **Redirect-After-Post**: Prevents duplicate form submission  
✅ **Flash Attributes**: Temporary data across redirects  
✅ **Error Handling**: th:errors for field and global errors  
✅ **Persistence**: Optional JPA integration with H2  

## **Common Viva Questions:**
- "What is the difference between @Controller and @RestController?"
- "How does Thymeleaf prevent XSS attacks?"
- "Why use Redirect-After-Post pattern?"
- "How to handle file uploads in Spring MVC?"
- "What is the role of BindingResult in form processing?"

**Remember**: Spring MVC with Thymeleaf provides a robust, secure way to build web applications with proper separation of concerns and built-in protection against common web vulnerabilities!

# 📘 Experiment No. 2

## 🎯 Aim

To design and implement a Java program demonstrating **packages**, **access control**, and **inheritance** using a real-world case study.

---

## 💡 Case Study Problem: E-Commerce Product Catalog System

A small e-commerce platform wants to organize its product catalog in a modular way using **Java Packages**.

The system should:

1. Have a package **`catalog`** containing the base class `Product` with attributes like `id`, `name`, `price`.
2. Create a package **`catalog.electronics`** with subclass `Electronics` that adds attributes like `brand` and `warranty`.
3. Create a package **`catalog.clothing`** with subclass `Clothing` that adds attributes like `size` and `material`.
4. Demonstrate **access control** using modifiers:

   * `private`: `id`, `price` (with getters/setters)
   * `protected`: `name` (accessible by subclasses)
   * `public`: `displayDetails()` method (overridden by subclasses)
5. Create a main class in package **`app`** that:

   * Imports the catalog packages
   * Creates objects of `Electronics` and `Clothing`
   * Demonstrates **inheritance** and **access control** in action

---

## 🧩 Folder Structure

```bash
src/
 ├── app/
 │   └── MainApp.java
 └── catalog/
     ├── Product.java
     ├── electronics/
     │   └── Electronics.java
     └── clothing/
         └── Clothing.java
```

## Command Line:
```
# Compile
javac -d . catalog/Product.java
javac -d . catalog/electronics/Electronics.java
javac -d . catalog/clothing/Clothing.java
javac -d . app/MainApp.java

# Run
java app.MainApp
```

## 🧪 Sample Output

```bash
Product Details:

--- Electronics ---
ID: 101
Name: Laptop
Brand: Dell
Warranty: 2 years
Price: 55000.0

--- Clothing ---
ID: 202
Name: Jacket
Size: L
Material: Leather
Price: 3200.0
```

## 📖 Brief Theory

### 🔹 Packages

A **package** in Java is a namespace that organizes classes and interfaces. It helps in **modular development** and prevents **naming conflicts**.

### 🔹 Access Modifiers

| Modifier    | Accessibility                             |
| ----------- | ----------------------------------------- |
| `private`   | Only within the class                     |
| `protected` | Within the same package and by subclasses |
| `public`    | Accessible from anywhere                  |
| *(default)* | Within the same package only              |

### 🔹 Inheritance Across Packages

By importing classes from one package into another, we can achieve **code reusability** and **polymorphism**.


# **Packages & Access Control Viva Theory**

## **1. Packages Fundamentals**

### **Q: What are packages in Java?**
**A:** Packages are containers for classes that:
- Organize related classes and interfaces
- Prevent naming conflicts
- Control access through access modifiers
- Provide namespace management

### **Q: Why use packages?**
**A:**
- **Modularity**: Group related functionality
- **Reusability**: Easy to import and use
- **Security**: Access control between packages
- **Maintainability**: Easier to locate and manage code
- **Avoid naming conflicts**: Same class names in different packages

### **Q: What is the directory structure for packages?**
**A:** 
```
project/
├── catalog/
│   ├── Product.java
│   ├── electronics/
│   │   └── Electronics.java
│   └── clothing/
│       └── Clothing.java
└── app/
    └── MainApp.java
```
Package name must match directory structure.

---

## **2. Access Modifiers Deep Dive**

### **Q: Explain all access modifiers with examples**
**A:**

| Modifier | Class | Package | Subclass (same pkg) | Subclass (diff pkg) | World |
|----------|-------|---------|---------------------|---------------------|-------|
| **private** | ✅ | ❌ | ❌ | ❌ | ❌ |
| **default** | ✅ | ✅ | ✅ | ❌ | ❌ |
| **protected** | ✅ | ✅ | ✅ | ✅ | ❌ |
| **public** | ✅ | ✅ | ✅ | ✅ | ✅ |

### **Q: Why did we make 'id' and 'price' private?**
**A:**
- **Encapsulation**: Hide internal implementation
- **Data Protection**: Prevent direct modification
- **Validation**: Can add validation in setters
- **Flexibility**: Can change internal representation without affecting users

### **Q: Why is 'name' protected?**
**A:**
- **Subclass Access**: Allow direct access in subclasses (even in different packages)
- **Controlled Visibility**: Not exposed to unrelated classes
- **Inheritance Benefit**: Subclasses can use it directly without getters

### **Q: What happens if we don't specify any access modifier?**
**A:** It becomes **default** (package-private) - accessible only within the same package.

---

## **3. Inheritance Across Packages**

### **Q: Can a class in one package inherit from a class in another package?**
**A:** Yes, but:
- The subclass must **import** the parent class
- The parent class must be **public**
- Only **public** and **protected** members are accessible to subclass

### **Q: What access does a subclass get in different package?**
**A:**
- ✅ **Public** members: Full access
- ✅ **Protected** members: Direct access
- ❌ **Private** members: No access (must use getters/setters)
- ❌ **Default** members: No access (package-private)

### **Q: In our example, how can Electronics access 'name'?**
**A:** Because 'name' is declared as **protected** in Product class, so subclasses in any package can access it directly.

```java
// In Electronics.java (different package)
this.name = "Laptop";  // ✅ Works - protected access
```

---

## **4. Import Statements**

### **Q: What are import statements?**
**A:** Ways to include classes from other packages:
```java
import catalog.Product;                    // Specific class
import catalog.electronics.*;              // All classes in package
import catalog.electronics.Electronics;    // Specific class
```

### **Q: What happens if we don't use import?**
**A:** We can use fully qualified names:
```java
catalog.Product product = new catalog.Product();
```

### **Q: What is static import?**
**A:** Import static members (methods/fields):
```java
import static java.lang.Math.PI;
// Now can use PI directly instead of Math.PI
```

---

## **5. Real-world Scenarios**

### **Q: Why is this e-commerce structure good?**
**A:**
- **Separation of Concerns**: Electronics and Clothing in separate packages
- **Scalability**: Easy to add new product categories (Books, Furniture packages)
- **Team Development**: Different teams can work on different packages
- **Access Control**: Sensitive data (price) protected, common data (name) shared

### **Q: How would you add a new product category?**
**A:**
```java
// Create new package
package catalog.books;

// Import and extend Product
import catalog.Product;

public class Books extends Product {
    private String author;
    private String isbn;
    
    // Implementation...
}
```

### **Q: What if we want to make Product abstract?**
**A:**
```java
public abstract class Product {
    // abstract methods can be defined
    public abstract void applyDiscount();
}
```
Subclasses must implement abstract methods.

---

## **6. Compilation & Execution**

### **Q: How to compile packages from command line?**
**A:**
```bash
# Compile base class first
javac -d . catalog/Product.java

# Then compile subclasses
javac -d . catalog/electronics/Electronics.java
javac -d . catalog/clothing/Clothing.java

# Finally compile main class
javac -d . app/MainApp.java

# Run
java app.MainApp
```

### **Q: What does the -d flag do?**
**A:** It specifies the **destination directory** for compiled class files, maintaining the package structure.

### **Q: What is classpath?**
**A:** The location where JVM looks for classes. Can be set using:
```bash
java -cp . app.MainApp
```

---

## **7. Common Mistakes & Solutions**

### **Q: What if we make Product class without 'public'?**
**A:** It becomes default (package-private) and won't be accessible outside catalog package.

### **Q: What if we try to access 'name' from MainApp?**
**A:** Compilation error - protected members are not accessible from unrelated classes in different packages.

### **Q: How to provide controlled access to private fields?**
**A:** Use public getters and setters:
```java
public double getPrice() { return price; }
public void setPrice(double price) { 
    if(price > 0) this.price = price;  // Validation
}
```

---

## **8. Advanced Concepts**

### **Q: What is the difference between packages and modules?**
**A:**
- **Packages**: Logical grouping of classes (since Java 1.0)
- **Modules**: Physical grouping of packages with dependencies (Java 9+)

### **Q: What is package-info.java?**
**A:** A special file that contains package-level documentation and annotations.

### **Q: Can we have circular dependencies between packages?**
**A:** Technically yes, but it's bad design. It creates tight coupling and should be avoided.

---

## **9. Design Principles Demonstrated**

### **Q: How does this demonstrate encapsulation?**
**A:**
- Private fields hide implementation
- Public methods provide controlled access
- Internal changes don't affect external code

### **Q: How does this demonstrate inheritance?**
**A:**
- Electronics and Clothing inherit common properties from Product
- Code reuse through base class functionality
- Polymorphism through method overriding

### **Q: How does this demonstrate abstraction?**
**A:**
- Product class defines common interface
- Subclasses provide specific implementations
- MainApp works with general Product concept

---

## **Quick Revision Checklist:**

✅ **Packages**: Organize code, prevent conflicts  
✅ **Access Control**: private, protected, public, default  
✅ **Inheritance Across Packages**: Import required, access limited  
✅ **Encapsulation**: Private fields + public methods  
✅ **Modular Design**: Scalable and maintainable  
✅ **Compilation**: -d flag maintains package structure  

## **Common Viva Questions:**
- "Why use packages instead of putting all classes together?"
- "When to use protected vs private?"
- "How does access change when subclass is in different package?"
- "What are the compilation steps for package structure?"
- "How would you improve this design?"

**Remember**: Always relate answers to the e-commerce example for practical understanding!

---

## 🧩 Laboratory Exercise

### 🗂️ Step 1 – Package `catalog`

**Class:** `Product`

* Attributes:

  * `private int id`
  * `protected String name`
  * `private double price`
* Methods:

  * Getters and setters for `id` and `price`
  * `public void displayDetails()`

### ⚡ Step 2 – Package `catalog.electronics`

**Class:** `Electronics` (extends `Product`)

* Additional attributes: `brand`, `warranty`
* Overrides `displayDetails()` to include electronics info

### 👕 Step 3 – Package `catalog.clothing`

**Class:** `Clothing` (extends `Product`)

* Additional attributes: `size`, `material`
* Overrides `displayDetails()` to include clothing info

### 💻 Step 4 – Package `app`

**Class:** `MainApp`

* Imports all catalog packages
* Creates objects of `Electronics` and `Clothing`
* Demonstrates:

  * Inheritance
  * Access modifier behavior
  * Dynamic method invocation (`displayDetails()` override)



---

## 🧭 Objectives

* Understand the concept of **Java packages** for organizing classes
* Implement **inheritance across multiple packages**
* Explore **access control modifiers** (`private`, `protected`, `public`) in real-world design
* Develop **modular and maintainable** Java applications

---

## 🏁 Outcomes

After completing this experiment, you will be able to:

* Create and use **Java packages**
* Apply **inheritance** across packages
* Use **access modifiers** to enforce encapsulation
* Build scalable, **modular Java applications**

---

## 🧠 Prerequisites

* Knowledge of **Java classes and objects**
* Understanding of **inheritance and encapsulation**
* Familiarity with **directory structures** and **command-line compilation**

---

## ⚙️ Requirements

* **JDK 8 or above**
* Any Java IDE (Eclipse, IntelliJ, NetBeans) or command-line setup

---

## 🧾 Conclusion

This experiment successfully demonstrates how **Java packages**, **access control**, and **inheritance** can be combined to build a modular and scalable application.
By organizing classes into meaningful packages, the system ensures **code clarity**, **reusability**, and **encapsulation**, which are vital for large-scale software projects.


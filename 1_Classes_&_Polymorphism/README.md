# Experiment No. 1

## 🎯 **Aim**

To design and implement a Java program demonstrating the concept of **Classes and Polymorphism** using an innovative case study.

---

## 🚖 **Case Study: Smart Transport Booking System**

A city is launching a **Smart Transport Booking App** where citizens can book different modes of transport — **Bus, Metro, Auto-rickshaw, and E-Bike**.

Each transport mode has:

* A **fare calculation system** (different logic for each type)
* A **display of estimated travel time** (varies by transport type)
* A **print receipt option** that shows journey details

### ✳️ **Base Class: `Transport`**

Contains abstract methods:

1. `calculateFare(int distance)`
2. `getETA(int distance)`
3. `printReceipt(String source, String destination, int distance)`

### ✳️ **Derived Classes:**

* `Bus`
* `Metro`
* `Auto`
* `EBike`

Each subclass implements its **own logic** for fare calculation and ETA.

### ✳️ **Main Class:**

* Demonstrates **runtime polymorphism** by creating a `Transport` reference that points to different transport mode objects.
* Dynamically invokes overridden methods.
* Prints journey receipts.

---

## 💻 **Code Structure**

```
Transport.java        → Abstract base class
Bus.java              → Derived class (fare + ETA logic)
Metro.java            → Derived class (fare + ETA logic)
Auto.java             → Derived class (fare + ETA logic)
EBike.java            → Derived class (fare + ETA logic)
SmartTransportDemo.java → Main driver class demonstrating polymorphism
```

## ⚙️ **Compilation & Execution (Command Line)**

```
cd SmartTransportSystem
javac app/MainApp.java transport/*.java
java app.MainApp
```

## 🧮 **Sample Output**

```
-- Smart Transport Booking System ---
Demonstrating Runtime Polymorphism

=========================================
  Smart Transport Receipt (Bus)
=========================================
From: City Center
To: University
Distance: 12 km
Estimated Time: 24 minutes
Total Fare: $34.00
Thank you for using Smart Transport!
=========================================

=========================================
  Smart Transport Receipt (Metro)
=========================================
From Station: Main Station
To Station: North Plaza
Distance: 7 km
Estimated Time: 13 minutes
Total Fare: $30.00
Thank you for using Smart Transport!
=========================================

=========================================
  Smart Transport Receipt (Auto-rickshaw)
=========================================
Pickup: Hospital
Drop: Railway Station
Distance: 5 km
Estimated Time: 8 minutes
Total Fare: $65.00
Thank you for using Smart Transport!
=========================================

=========================================
  Smart Transport Receipt (E-Bike)
=========================================
Start: Park
End: Library
Distance: 3 km
Estimated Time: 7 minutes
Total Fare: $9.00
Thank you for using Smart Transport!
=========================================

--- Demonstration Complete ---
```

---

## 📘 **Brief Theory**

### 🧱 **Polymorphism**

The ability of an object to take many forms.
In Java, polymorphism is mainly achieved through **method overriding** and **interfaces**.

### 🔁 **Method Overriding**

Occurs when a subclass provides a specific implementation of a method declared in its superclass.

### ⚡ **Runtime Polymorphism (Dynamic Binding)**

When the method call is resolved **at runtime**, depending on the object’s actual type—not the reference type.

# **Polymorphism Viva Questions & Answers**

## **1. Basic Concepts**

### **Q: What is polymorphism?**
**A:** Polymorphism means "many forms". It's the ability of an object to take different forms and behave differently based on its actual type. In Java, it allows us to perform a single action in different ways.

### **Q: What are the types of polymorphism in Java?**
**A:** 
- **Compile-time Polymorphism**: Method overloading (same method name, different parameters & implementation)
- **Runtime Polymorphism**: Method overriding (same method name & parameters, different implementation)

### **Q: What is method overriding?**
**A:** When a subclass provides a specific implementation of a method that is already defined in its parent class. It must have the same name, return type, and parameters.

---

## **2. Abstract Classes & Methods**

### **Q: What is an abstract class?**
**A:** An abstract class is a class that cannot be instantiated and may contain abstract methods (methods without implementation). It serves as a template for other classes.

### **Q: Why did we use abstract class Transport?**
**A:** 
- To define a common interface for all transport types
- To enforce that all transport classes must implement certain methods
- To achieve polymorphism through a common base type

### **Q: Can we create object of abstract class?**
**A:** No, we cannot instantiate an abstract class directly. We must create objects of its concrete subclasses.

### **Q: What are abstract methods?**
**A:** Methods declared without implementation (without body) using the `abstract` keyword. They must be implemented by subclasses.

---

## **3. Runtime Polymorphism**

### **Q: What is runtime polymorphism?**
**A:** When the method to be executed is determined at runtime based on the actual object type, not the reference type. This is also called dynamic method dispatch.

### **Q: How is runtime polymorphism achieved?**
**A:** Through method overriding and using parent class references to child class objects.

### **Q: Explain this line: `Transport transport = new Bus();`**
**A:** 
- `Transport transport` - Reference variable of parent type
- `new Bus()` - Actual object of child class
- The reference can point to any Transport subclass object
- Method calls will execute the Bus class implementation

### **Q: What is dynamic method dispatch?**
**A:** The process where Java decides at runtime which overridden method to call based on the actual object type.

---

## **4. Implementation Details**

### **Q: Why use protected methods in abstract class?**
**A:** Protected methods are accessible to subclasses but not to outside classes. This allows code reuse while maintaining encapsulation.

### **Q: What is the advantage of using static final constants?**
**A:** 
- `static`: Shared across all objects of the class
- `final`: Cannot be changed (constants)
- Improves readability and maintainability
- Easy to modify pricing/rates in one place

### **Q: How does `printReceipt()` demonstrate polymorphism?**
**A:** It calls `calculateFare()` and `getETA()` internally, which are overridden in each subclass. The same method produces different results based on the actual object type.

---

## **5. Real-world Applications**

### **Q: Why is this transport system a good example of polymorphism?**
**A:** 
- Different transport types have same operations but different implementations
- Easy to add new transport types without changing existing code
- Client code works with Transport interface, not specific types
- Models real-world scenario accurately

### **Q: What are the benefits of this design?**
**A:**
- **Extensibility**: Easy to add new transport modes
- **Maintainability**: Changes in one transport don't affect others
- **Code Reuse**: Common functionality in base class
- **Flexibility**: Can switch transport types at runtime

### **Q: How would you add a new transport type like "Taxi"?**
**A:**
```java
class Taxi extends Transport {
    public double calculateFare(int distance) {
        return 40.0 + (distance * 15.0);
    }
    // implement other methods...
}
```
No changes needed in existing code!

---

## **6. Technical Deep Dive**

### **Q: What happens if we don't override abstract methods?**
**A:** The subclass must be declared abstract, or we get a compilation error. All abstract methods must be implemented.

### **Q: Can we have constructor in abstract class?**
**A:** Yes, abstract classes can have constructors. They are called when subclass objects are created.

### **Q: Difference between abstract class and interface?**
**A:**
- **Abstract Class**: Can have implemented methods, constructors, instance variables
- **Interface**: Only abstract methods (before Java 8), no constructors, only constants
- A class can extend only one abstract class but implement multiple interfaces

### **Q: What is the @Override annotation?**
**A:** It's optional but recommended. It tells the compiler that we're intentionally overriding a method. Helps catch errors if method signature doesn't match.

---

## **7. Code Analysis Questions**

### **Q: Why are the constants declared as static final?**
**A:** 
- `static`: Shared across all instances (memory efficient)
- `final`: Cannot be modified (safety)
- Example: All Bus objects share the same BASE_FARE

### **Q: How is encapsulation maintained?**
**A:** 
- Private constants in each class
- Protected methods in base class
- Public interface through abstract methods
- Each transport encapsulates its own pricing logic

### **Q: What design pattern is this similar to?**
**A:** This follows the **Template Method Pattern** where the base class defines the skeleton (printReceipt) and subclasses provide specific implementations.

---

## **8. Advanced Concepts**

### **Q: Can we achieve polymorphism without inheritance?**
**A:** Yes, through interfaces. Interfaces provide another way to achieve polymorphism in Java.

### **Q: What is the advantage of using base class reference?**
**A:** 
- Loose coupling: Code depends on abstraction, not concrete classes
- Flexibility: Can easily substitute different implementations
- Testability: Easy to mock objects for testing

### **Q: How does this relate to Open/Closed Principle?**
**A:** The system is **open for extension** (can add new transports) but **closed for modification** (don't need to change existing code).

---

## **Quick Revision Points:**

1. **Polymorphism** = Same interface, different implementations
2. **Runtime** = Decision made when program runs
3. **Abstract class** = Template that cannot be instantiated
4. **Method overriding** = Subclass provides specific implementation
5. **Dynamic dispatch** = Java calls method based on actual object type
6. **Benefits** = Extensibility, maintainability, flexibility

## **Common Viva Questions:**
- "Explain polymorphism with real example"
- "Why use abstract class instead of interface?"
- "What happens when we call transport.calculateFare()?"
- "How would you extend this system?"
- "What are the advantages of this design?"

**Remember**: Always relate your answers to the transport booking example for better understanding!


---

## 🧠 **Objectives**

* Understand the concept of **inheritance** and **polymorphism** in Java.
* Implement a **real-world case study** using base and derived classes.
* Demonstrate **runtime polymorphism** by invoking overridden methods dynamically.
* Practice **object-oriented design** principles.

---

## 🏁 **Outcomes**

After completing this experiment, students will be able to:

* Apply **polymorphism** in Java using base class references.
* Implement **method overriding** in child classes.
* Develop **object-oriented solutions** for real-world scenarios.
* Write **reusable and maintainable** Java code.

---

## 🧩 **Prerequisites**

* Basic knowledge of Java programming
* Understanding of classes, objects, and inheritance

---

## ⚙️ **Requirements**

* **JDK 8** or above
* Any IDE (Eclipse, IntelliJ, NetBeans) or command-line environment

---

### 💡 **Use Case**

By modeling different transport modes (Bus, Metro, Auto, E-Bike), we demonstrate how each class **overrides** the fare calculation and ETA logic.

---

## 🧾 **Conclusion**

The experiment successfully demonstrates **Classes and Polymorphism** in Java.
By using **method overriding** and **dynamic binding**, different transport modes exhibit distinct behaviors while sharing a common interface.
This approach promotes **code reusability, scalability,** and **maintainability** — key pillars of object-oriented programming.

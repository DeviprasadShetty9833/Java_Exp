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

## 📘 **Brief Theory**

### 🧱 **Polymorphism**

The ability of an object to take many forms.
In Java, polymorphism is mainly achieved through **method overriding** and **interfaces**.

### 🔁 **Method Overriding**

Occurs when a subclass provides a specific implementation of a method declared in its superclass.

### ⚡ **Runtime Polymorphism (Dynamic Binding)**

When the method call is resolved **at runtime**, depending on the object’s actual type—not the reference type.

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

---

## 🧮 **Sample Output**

```
=== Smart Transport Booking System ===
Bus: Fare = ₹120.0, ETA = 45 minutes
Metro: Fare = ₹80.0, ETA = 25 minutes
Auto: Fare = ₹150.0, ETA = 35 minutes
E-Bike: Fare = ₹60.0, ETA = 15 minutes
```

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

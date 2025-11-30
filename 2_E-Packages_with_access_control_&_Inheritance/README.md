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


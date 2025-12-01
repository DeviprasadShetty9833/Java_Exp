# Experiment No. 3

# Title: Implement a mini contact book using Java Collections (ArrayList/HashMap) and handle exceptions using try-catch-finally and custom exceptions
# Aim
To design and implement a mini contact book application using Java Collections and demonstrate exception handling with try-catch-finally and custom exceptions.

# Objectives
    • To understand and use Java Collections (ArrayList, HashMap).
    • To implement basic CRUD (Create, Read, Update, Delete) operations on contact data.
    • To apply exception handling in real-world problems.
    • To design and use custom exceptions.
    • To build a menu-driven program simulating a small contact management system.
# Outcomes
After completing this experiment, the student will be able to:
    • Use ArrayList and HashMap for data storage and retrieval.
    • Apply try-catch-finally for handling runtime errors.
    • Create and use custom exceptions for specific scenarios.
    • Develop a simple yet practical console-based application.
Prerequisite
    • Knowledge of Java classes and objects.
    • Understanding of Collections framework.
    • Basics of exception handling in Java.
Requirements
    • JDK installed (version 8 or above).
    • Any Java IDE (Eclipse, IntelliJ, NetBeans) or command-line environment.
Brief Theory
    • Java Collections Framework: Provides data structures like ArrayList, HashMap, HashSet, etc. for efficient data handling.
        ◦ ArrayList: A resizable array, suitable for storing ordered contact lists.
        ◦ HashMap: Stores key-value pairs, ideal for grouping contacts by category (Student, Faculty, Staff).
    • Exception Handling: Mechanism to handle runtime errors gracefully.
        ◦ try: Block of code that might throw an exception.
        ◦ catch: Block that handles the exception.
        ◦ finally: Executes code regardless of exception occurrence (e.g., cleanup tasks).
    • Custom Exceptions: User-defined exceptions for specific error conditions (e.g., ContactNotFoundException).


# Project Structure:

```
contactbook/
├── src/
│   └── contactbook/
│       ├── Contact.java
│       ├── ContactNotFoundException.java
│       ├── ContactManager.java
│       └── SmartContactBookApp.java
└── README.md
```

# Command 
```
javac src/contactbook/*.java -d out/

java -cp out contactbook.SmartContactBookApp
```
---

# **Collections & Exception Handling Viva Theory**

## **1. Collections Framework Fundamentals**

### **Q: What is Java Collections Framework?**
**A:** A unified architecture for storing and manipulating groups of objects. It provides:
- **Interfaces**: List, Set, Map, Queue
- **Implementations**: ArrayList, HashMap, HashSet, LinkedList
- **Algorithms**: Searching, sorting, shuffling

### **Q: Why use ArrayList in this contact book?**
**A:**
- **Dynamic sizing**: Grows automatically as contacts are added
- **Fast access**: O(1) access by index
- **Order preservation**: Maintains insertion order
- **Duplicates allowed**: Multiple contacts can have same name

### **Q: Why use HashMap for categorization?**
**A:**
- **Fast lookup**: O(1) average case for category-based retrieval
- **Key-Value pairs**: Category → List of contacts mapping
- **Grouping**: Natural way to organize contacts by category
- **Efficient updates**: Easy to add/remove from specific categories

```java
// HashMap structure
"Student" → [Contact1, Contact2, Contact3]
"Faculty" → [Contact4, Contact5]
"Staff"   → [Contact6]
```

---

## **2. ArrayList vs HashMap Comparison**

### **Q: When to use ArrayList vs HashMap?**
**A:**

| ArrayList | HashMap |
|-----------|---------|
| Ordered collection | Key-Value pairs |
| Index-based access | Key-based access |
| Duplicates allowed | Unique keys |
| Slower search O(n) | Faster search O(1) |
| Maintains order | No guaranteed order |

### **Q: How do we maintain consistency between ArrayList and HashMap?**
**A:** All operations (add, delete) must update both collections:
```java
public void addContact(Contact c) {
    contacts.add(c);  // Add to ArrayList
    categorizedContacts.putIfAbsent(c.getCategory(), new ArrayList<>());
    categorizedContacts.get(c.getCategory()).add(c);  // Add to HashMap
}
```

---

## **3. Exception Handling Deep Dive**

### **Q: What is exception handling?**
**A:** Mechanism to handle runtime errors gracefully without crashing the program.

### **Q: Explain try-catch-finally blocks**
**A:**
- **try**: Code that might throw exceptions
- **catch**: Handles specific exceptions
- **finally**: Always executes (cleanup code)

### **Q: Why use finally block in this application?**
**A:**
- **Resource cleanup**: Scanner closure
- **Consistent messaging**: "Operation completed" regardless of success/failure
- **Mandatory execution**: Code that must run even if exception occurs

```java
try {
    // Risky operation
} catch (Exception e) {
    // Handle error
} finally {
    System.out.println("Operation completed");  // Always shows
}
```

---

## **4. Custom Exceptions**

### **Q: Why create custom exceptions?**
**A:**
- **Domain-specific errors**: ContactNotFoundException for our app
- **Better error messages**: Specific to business logic
- **Cleaner code**: Separate from built-in exceptions
- **Type safety**: Catch specific business exceptions

### **Q: How to create custom exceptions?**
**A:**
```java
public class ContactNotFoundException extends Exception {
    public ContactNotFoundException(String message) {
        super(message);  // Call parent constructor
    }
}
```

### **Q: Checked vs Unchecked exceptions?**
**A:**
- **Checked**: Must be declared or handled (Exception)
- **Unchecked**: Runtime exceptions (RuntimeException)
- **Our case**: ContactNotFoundException is checked

---

## **5. CRUD Operations Implementation**

### **Q: What are CRUD operations?**
**A:** Create, Read, Update, Delete - basic data operations

### **Q: How is search implemented?**
**A:** Linear search through ArrayList:
```java
public Contact searchContact(String keyword) throws ContactNotFoundException {
    for (Contact c : contacts) {
        if (c.getName().equalsIgnoreCase(keyword) || 
            c.getPhoneNumber().equals(keyword)) {
            return c;
        }
    }
    throw new ContactNotFoundException("Contact not found for: " + keyword);
}
```

### **Q: How to improve search performance?**
**A:** Use additional HashMap for phone-based lookup:
```java
private HashMap<String, Contact> phoneMap = new HashMap<>();

// During addContact:
phoneMap.put(contact.getPhoneNumber(), contact);
```

---

## **6. Input Validation**

### **Q: Why validate inputs?**
**A:**
- **Data integrity**: Prevent invalid data in system
- **Security**: Avoid injection attacks
- **User experience**: Immediate feedback on errors

### **Q: What validations are implemented?**
**A:**
```java
// Phone validation: exactly 10 digits
public static boolean isValidPhone(String phone) {
    return phone.matches("\\d{10}");
}

// Email validation: basic format check
public static boolean isValidEmail(String email) {
    return email.contains("@") && email.contains(".");
}

// Name validation: not blank
if (name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
```

---

## **7. Data Structures in Detail**

### **Q: What is putIfAbsent() in HashMap?**
**A:** 
```java
categorizedContacts.putIfAbsent(c.getCategory(), new ArrayList<>());
```
- If key doesn't exist, creates new ArrayList
- If key exists, does nothing
- Prevents NullPointerException

### **Q: How does ArrayList.remove() work?**
**A:** 
- Searches for object using equals() method
- Removes first occurrence found
- Shifts subsequent elements to left
- Time complexity: O(n)

### **Q: Why use String.format() in toString()?**
**A:** 
```java
return String.format("Name: %s | Phone: %s | Email: %s | Category: %s",
        name, phoneNumber, email, category);
```
- Cleaner than concatenation
- Better performance
- More readable

---

## **8. Menu-Driven Program Design**

### **Q: Why use menu-driven approach?**
**A:**
- **User-friendly**: Clear options for users
- **Structured flow**: Logical program progression
- **Easy testing**: Can test individual features
- **Professional**: Standard for console applications

### **Q: How to handle invalid menu choices?**
**A:** Default case in switch statement:
```java
default:
    System.out.println("Invalid choice! Try again.");
```

### **Q: Why use do-while loop?**
**A:** 
- **Guaranteed execution**: Menu shows at least once
- **Condition at end**: Check after user interaction
- **Natural flow**: Perfect for menu systems

---

## **9. Resource Management**

### **Q: Why close Scanner in finally block?**
**A:**
- **Resource leak prevention**: Scanner uses system resources
- **Good practice**: Always close I/O resources
- **Exception safety**: Ensures cleanup even if errors occur

### **Q: What is try-with-resources?**
**A:** Java 7+ feature for automatic resource management:
```java
try (Scanner sc = new Scanner(System.in)) {
    // Use scanner
} // Automatically closed
```

---

## **10. Design Principles Demonstrated**

### **Q: How does this demonstrate encapsulation?**
**A:**
- Private fields in Contact class
- Public methods for controlled access
- Validation logic in ContactManager

### **Q: How does this demonstrate separation of concerns?**
**A:**
- **Contact**: Data representation
- **ContactManager**: Business logic
- **SmartContactBookApp**: User interface

### **Q: What design pattern is used?**
**A:** **Repository Pattern** - ContactManager acts as repository handling data operations.

---

## **11. Error Scenarios Handled**

### **Q: What exceptions are handled?**
**A:**
1. **ContactNotFoundException**: Custom exception for missing contacts
2. **IllegalArgumentException**: Invalid input data
3. **NumberFormatException**: Invalid menu choice input
4. **General Exception**: Unexpected errors

### **Q: How to make it more robust?**
**A:**
- Add input retry mechanisms
- Implement data persistence (file/database)
- Add contact editing functionality
- Implement duplicate contact prevention

---

## **Quick Revision Checklist:**

✅ **Collections**: ArrayList (ordering), HashMap (grouping)  
✅ **Exception Handling**: try-catch-finally, custom exceptions  
✅ **CRUD Operations**: Add, Search, Delete, Display  
✅ **Input Validation**: Phone, email, name validation  
✅ **Menu System**: User-friendly interface  
✅ **Resource Management**: Scanner cleanup  

## **Common Viva Questions:**
- "Why use both ArrayList and HashMap?"
- "What happens if we don't handle ContactNotFoundException?"
- "How would you add contact editing functionality?"
- "What are the performance implications of linear search?"
- "How to make this application persistent?"

**Remember**: Always relate answers to the contact book example - it makes concepts more concrete and understandable!


Laboratory Exercise
Case Study Problem – Smart Contact Book Application
    1. Define a Contact class with attributes:
        ◦ name, phoneNumber, email, category.
    2. Use Collections to store contacts:
        ◦ Maintain an ArrayList<Contact> for all contacts.
        ◦ Maintain a HashMap<String, List<Contact>> where key = category (Student/Faculty/Staff).
    3. Implement features:
        ◦ Add Contact → Insert into ArrayList and HashMap.
        ◦ Search Contact by name/phone → If not found, throw ContactNotFoundException.
        ◦ Delete Contact → Remove from both ArrayList and HashMap.
        ◦ Display Contacts grouped by category.
    4. Implement Exception Handling:
        ◦ Invalid inputs (blank name, invalid phone/email) handled with try-catch.
        ◦ finally block to display a message (“Operation completed”).
    5. Write a menu-driven program in main() to demonstrate all functionalities.




Output:
```
===== SMART CONTACT BOOK =====
1. Add Contact
2. Search Contact
3. Delete Contact
4. Display All Contacts
5. Exit
Enter your choice: 1
Enter Name: Alice Johnson
Enter Phone (10 digits): 9876543210
Enter Email: alice@gmail.com
Enter Category (Student/Faculty/Staff): Student
Contact added successfully!
Operation completed.


===== SMART CONTACT BOOK =====
1. Add Contact
2. Search Contact
3. Delete Contact
4. Display All Contacts
5. Exit
Enter your choice: 1
Enter Name: Dr. Smith
Enter Phone (10 digits): 9123456789
Enter Email: dr.smith@college.edu
Enter Category (Student/Faculty/Staff): Faculty
Contact added successfully!
Operation completed.


===== SMART CONTACT BOOK =====
1. Add Contact
2. Search Contact
3. Delete Contact
4. Display All Contacts
5. Exit
Enter your choice: 4
Contact List (Grouped by Category):

[Student]
    • Name: Alice Johnson | Phone: 9876543210 | Email: alice@gmail.com | Category: Student

[Faculty]
    • Name: Dr. Smith | Phone: 9123456789 | Email: dr.smith@college.edu | Category: Faculty


===== SMART CONTACT BOOK =====
1. Add Contact
2. Search Contact
3. Delete Contact
4. Display All Contacts
5. Exit
Enter your choice: 2
Enter Name or Phone to search: 9876543210
 Contact Found: Name: Alice Johnson | Phone: 9876543210 | Email: alice@gmail.com | Category: Student
Operation completed.


===== SMART CONTACT BOOK =====
1. Add Contact
2. Search Contact
3. Delete Contact
4. Display All Contacts
5. Exit
Enter your choice: 3
Enter Name or Phone to delete: 9123456789
Contact deleted successfully!
Operation completed.


===== SMART CONTACT BOOK =====
1. Add Contact
2. Search Contact
3. Delete Contact
4. Display All Contacts
5. Exit
Enter your choice: 4
 Contact List (Grouped by Category):

[Student]
    • Name: Alice Johnson | Phone: 9876543210 | Email: alice@gmail.com | Category: Student


===== SMART CONTACT BOOK =====
1. Add Contact
2. Search Contact
3. Delete Contact
4. Display All Contacts
5. Exit
Enter your choice: 2
Enter Name or Phone to search: Bob
 Contact not found for: Bob
Operation completed.


===== SMART CONTACT BOOK =====
1. Add Contact
2. Search Contact
3. Delete Contact
4. Display All Contacts
5. Exit
Enter your choice: 5
Exiting… Goodbye!
 Application Terminated.
```

Conclusion:
This experiment demonstrates: Use of ArrayList and HashMap for storing and organizing data, Implementation of custom exception (ContactNotFoundException), Robust try-catch-finally handling for runtime issues, A menu-driven, user-friendly console interface, Real-world design practical, modular, and maintainable.

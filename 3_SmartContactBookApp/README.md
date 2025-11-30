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

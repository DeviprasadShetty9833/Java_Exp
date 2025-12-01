
// File 1: contactbook/Contact.java

package contactbook;

// Represents a single contact
public class Contact {
    private String name;
    private String phoneNumber;
    private String email;
    private String category; // e.g., Student, Faculty, Staff

    public Contact(String name, String phoneNumber, String email, String category) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.category = category;
    }

    // Getters
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getCategory() { return category; }

    // Setters
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format(
                "Name: %s | Phone: %s | Email: %s | Category: %s",
                name, phoneNumber, email, category
        );
    }
}



// File 2: contactbook/ContactNotFoundException.java

package contactbook;

// Custom exception for missing contacts
public class ContactNotFoundException extends Exception {
    public ContactNotFoundException(String message) {
        super(message);
    }
}


// File 3: contactbook/ContactManager.java

package contactbook;

import java.util.*;

public class ContactManager {

    private ArrayList<Contact> contacts = new ArrayList<>();
    private HashMap<String, List<Contact>> categorizedContacts = new HashMap<>();

    // Add a contact to both ArrayList and HashMap
    public void addContact(Contact c) {
        contacts.add(c);

        categorizedContacts.putIfAbsent(c.getCategory(), new ArrayList<>());
        categorizedContacts.get(c.getCategory()).add(c);

        System.out.println("Contact added successfully!");
    }

    // Search by name or phone
    public Contact searchContact(String keyword) throws ContactNotFoundException {
        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(keyword) ||
                c.getPhoneNumber().equals(keyword)) {
                return c;
            }
        }
        throw new ContactNotFoundException("Contact not found for: " + keyword);
    }

    // Delete contact
    public void deleteContact(String keyword) throws ContactNotFoundException {
        Contact toRemove = searchContact(keyword);
        contacts.remove(toRemove);
        categorizedContacts.get(toRemove.getCategory()).remove(toRemove);

        System.out.println("Contact deleted successfully!");
    }

    // Display all contacts grouped by category
    public void displayContactsByCategory() {
        if (categorizedContacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("Contact List (Grouped by Category):");
        for (String category : categorizedContacts.keySet()) {
            System.out.println("\n[" + category + "]");
            for (Contact c : categorizedContacts.get(category)) {
                System.out.println("  - " + c);
            }
        }
    }

    // Utility to validate inputs
    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
}


---

// File 4: app/SmartContactBookApp.java

package app;

import contactbook.*;
import java.util.*;

public class SmartContactBookApp {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ContactManager manager = new ContactManager();

        int choice = 0;

        try {
            do {
                System.out.println("\n===== SMART CONTACT BOOK =====");
                System.out.println("1. Add Contact");
                System.out.println("2. Search Contact");
                System.out.println("3. Delete Contact");
                System.out.println("4. Display All Contacts");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Phone (10 digits): ");
                        String phone = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Category (Student/Faculty/Staff): ");
                        String category = sc.nextLine();

                        try {
                            if (name.isBlank() ||
                                !ContactManager.isValidPhone(phone) ||
                                !ContactManager.isValidEmail(email)) {
                                throw new IllegalArgumentException("Invalid input! Please check phone or email format.");
                            }

                            Contact c = new Contact(name, phone, email, category);
                            manager.addContact(c);

                        } catch (IllegalArgumentException e) {
                            System.out.println("Error: " + e.getMessage());
                        } finally {
                            System.out.println("Operation completed.\n");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Name or Phone to search: ");
                        String searchKey = sc.nextLine();

                        try {
                            Contact found = manager.searchContact(searchKey);
                            System.out.println("Contact Found: " + found);
                        } catch (ContactNotFoundException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            System.out.println("Operation completed.\n");
                        }
                        break;

                    case 3:
                        System.out.print("Enter Name or Phone to delete: ");
                        String deleteKey = sc.nextLine();

                        try {
                            manager.deleteContact(deleteKey);
                        } catch (ContactNotFoundException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            System.out.println("Operation completed.\n");
                        }
                        break;

                    case 4:
                        manager.displayContactsByCategory();
                        break;

                    case 5:
                        System.out.println("Exiting… Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice! Try again.");
                }

            } while (choice != 5);

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());

        } finally {
            sc.close();
            System.out.println("Application Terminated.");
        }
    }
}


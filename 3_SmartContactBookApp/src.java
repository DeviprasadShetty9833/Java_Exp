// File 1: contactbook/Contact.java

Package contactbook;

// Represents a single contact
Public class Contact {
    Private String name;
    Private String phoneNumber;
    Private String email;
    Private String category; // e.g., Student, Faculty, Staff

    Public Contact(String name, String phoneNumber, String email, String category) {
        This.name = name;
        This.phoneNumber = phoneNumber;
        This.email = email;
        This.category = category;
    }

    // Getters and setters
    Public String getName() { return name; }
    Public String getPhoneNumber() { return phoneNumber; }
    Public String getEmail() { return email; }
    Public String getCategory() { return category; }

    Public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    Public void setEmail(String email) { this.email = email; }

    @Override
    Public String toString() {
        Return String.format(“Name: %s | Phone: %s | Email: %s | Category: %s”,
                Name, phoneNumber, email, category);
    }
}

// File 2: contactbook/ContactNotFoundException.java

Package contactbook;

// Custom exception for missing contacts
Public class ContactNotFoundException extends Exception {
    Public ContactNotFoundException(String message) {
        Super(message);
    }
}

// File 3: contactbook/ContactManager.java

Package contactbook;

Import java.util.*;
Public class ContactManager {
    Private ArrayList<Contact> contacts = new ArrayList<>();
    Private HashMap<String, List<Contact>> categorizedContacts = new HashMap<>();

    // Add a contact to both ArrayList and HashMap
    Public void addContact(Contact c) {
        Contacts.add©;

        categorizedContacts.putIfAbsent(c.getCategory(), new ArrayList<>());
        categorizedContacts.get(c.getCategory()).add©;

        System.out.println(“ Contact added successfully!”);
    }

    // Search by name or phone
    Public Contact searchContact(String keyword) throws ContactNotFoundException {
        For (Contact c : contacts) {
            If (c.getName().equalsIgnoreCase(keyword) || c.getPhoneNumber().equals(keyword)) {
                Return c;
            }
        }
        Throw new ContactNotFoundException(“ Contact not found for: “ + keyword);
    }

    // Delete contact
    Public void deleteContact(String keyword) throws ContactNotFoundException {
        Contact toRemove = searchContact(keyword);
        Contacts.remove(toRemove);
        categorizedContacts.get(toRemove.getCategory()).remove(toRemove);
        System.out.println(“Contact deleted successfully!”);
    }

    // Display all contacts grouped by category
    Public void displayContactsByCategory() {
        If (categorizedContacts.isEmpty()) {
            System.out.println(“No contacts available.”);
            Return;
        }

        System.out.println(“ Contact List (Grouped by Category):”);
        For (String category : categorizedContacts.keySet()) {
            System.out.println(“\n[“ + category + “]”);
            For (Contact c : categorizedContacts.get(category)) {
                System.out.println(“  - “ + c);
            }
        }
    }

    // Utility to validate inputs
    Public static boolean isValidPhone(String phone) {
        Return phone.matches(\\d{10});
    }

    Public static boolean isValidEmail(String email) {
        Return email.contains(“@”) && email.contains(“.”);
    }
}



// File 4: app/SmartContactBookApp.java

Package app;

Import contactbook.*;
Import java.util.*;

Public class SmartContactBookApp {
    Public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactManager manager = new ContactManager();

        Int choice = 0;
        Try {
            Do {
                System.out.println(“\n===== SMART CONTACT BOOK =====”);
                System.out.println(“1. Add Contact”);
                System.out.println(“2. Search Contact”);
                System.out.println(“3. Delete Contact”);
                System.out.println(“4. Display All Contacts”);
                System.out.println(“5. Exit”);
                System.out.print(“Enter your choice: “);
                Choice = Integer.parseInt(sc.nextLine());

                Switch (choice) {
                    Case 1:
                        System.out.print(“Enter Name: “);
                        String name = sc.nextLine();
                        System.out.print(“Enter Phone (10 digits): “);
                        String phone = sc.nextLine();
                        System.out.print(“Enter Email: “);
                        String email = sc.nextLine();
                        System.out.print(“Enter Category (Student/Faculty/Staff): “);
                        String category = sc.nextLine();

                        Try {
                            If (name.isBlank() || !ContactManager.isValidPhone(phone) || !ContactManager.isValidEmail(email))
                                Throw new IllegalArgumentException(“Invalid input! Please check phone or email format.”);

                            Contact c = new Contact(name, phone, email, category);
                            Manager.addContact©;
                        } catch (IllegalArgumentException e) {
                            System.out.println(“Error: “ + e.getMessage());
                        } finally {
                            System.out.println(“Operation completed.\n”);
                        }
                        Break;

                    Case 2:
                        System.out.print(“Enter Name or Phone to search: “);
                        String searchKey = sc.nextLine();
                        Try {
                            Contact found = manager.searchContact(searchKey);
                            System.out.println(“ Contact Found: “ + found);
                        } catch (ContactNotFoundException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            System.out.println(“Operation completed.\n”);
                        }
                        Break;

                    Case 3:
                        System.out.print(“Enter Name or Phone to delete: “);
                        String deleteKey = sc.nextLine();
                        Try {
                            Manager.deleteContact(deleteKey);
                        } catch (ContactNotFoundException e) {
                            System.out.println(e.getMessage());
                        } finally {
                            System.out.println(“Operation completed.\n”);
                        }
                        Break;

                    Case 4:
                        Manager.displayContactsByCategory();
                        Break;

                    Case 5:
                        System.out.println(“Exiting… Goodbye!”);
                        Break;

                    Default:
                        System.out.println(“Invalid choice! Try again.”);
                }

            } while (choice != 5);
        } catch (Exception e) {
            System.out.println(“Unexpected error: “ + e.getMessage());
        } finally {
            Sc.close();
            System.out.println(“Application Terminated.”);
        }
    }
}


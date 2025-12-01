# **JDBC & Database Connectivity Viva Theory**

## **1. JDBC Fundamentals**

### **Q: What is JDBC?**
**A:** Java Database Connectivity (JDBC) is a Java API that enables Java applications to connect to relational databases. It provides:
- Standard interface for database connectivity
- Methods to execute SQL queries
- Transaction management
- Database metadata access

### **Q: What are the main components of JDBC?**
**A:**
1. **DriverManager**: Manages database drivers
2. **Connection**: Represents database connection
3. **Statement/PreparedStatement**: Executes SQL queries
4. **ResultSet**: Holds query results
5. **SQLException**: Handles database errors

### **Q: What is JDBC Driver?**
**A:** A software component that enables Java application to interact with a specific database. Types:
- **Type 4**: Pure Java driver (most common - MySQL Connector/J, PostgreSQL JDBC)
- **Type 1**: JDBC-ODBC bridge
- **Type 2**: Native API driver
- **Type 3**: Network protocol driver

---

## **2. Database Connection**

### **Q: How to establish database connection?**
**A:**
```java
String URL = "jdbc:mysql://localhost:3306/librarydb";
String USER = "root";
String PASSWORD = "password";

Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
```

### **Q: What is Connection URL format for different databases?**
**A:**
- **MySQL**: `jdbc:mysql://hostname:port/database`
- **PostgreSQL**: `jdbc:postgresql://hostname:port/database`
- **SQLite**: `jdbc:sqlite:filename.db`
- **Oracle**: `jdbc:oracle:thin:@hostname:port:SID`

### **Q: Why use try-with-resources?**
**A:**
- **Automatic resource management**: Automatically closes Connection, Statement, ResultSet
- **Exception safety**: Ensures cleanup even if exceptions occur
- **Cleaner code**: Reduces boilerplate code

```java
try (Connection conn = getConnection();
     PreparedStatement ps = conn.prepareStatement(sql)) {
    // Use resources
} // Automatically closed
```

---

## **3. PreparedStatement vs Statement**

### **Q: Why use PreparedStatement over Statement?**
**A:**
- **Prevention of SQL Injection**: Uses parameter binding
- **Performance**: Pre-compiled and cached
- **Type safety**: Compile-time parameter type checking
- **Readability**: Cleaner code with parameter placeholders

### **Q: Demonstrate SQL Injection vulnerability with Statement**
**A:**
```java
// UNSAFE - SQL Injection vulnerable
String sql = "SELECT * FROM users WHERE username = '" + username + "'";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);

// SAFE - Using PreparedStatement
String sql = "SELECT * FROM users WHERE username = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, username);
ResultSet rs = ps.executeQuery();
```

### **Q: How to set parameters in PreparedStatement?**
**A:**
```java
ps.setString(1, "value");    // String parameter
ps.setInt(2, 100);          // Integer parameter  
ps.setDouble(3, 99.99);     // Double parameter
ps.setDate(4, new Date());  // Date parameter
ps.setBoolean(5, true);     // Boolean parameter
```

---

## **4. Transaction Management**

### **Q: What are database transactions?**
**A:** A sequence of database operations that are executed as a single unit of work. They follow ACID properties:
- **Atomicity**: All or nothing
- **Consistency**: Database constraints preserved
- **Isolation**: Concurrent transactions don't interfere
- **Durability**: Committed changes persist

### **Q: How to implement transactions in JDBC?**
**A:**
```java
conn.setAutoCommit(false);  // Start transaction
try {
    // Multiple database operations
    updateBookCopies(bookId);
    insertLoanRecord(memberId, bookId);
    
    conn.commit();  // Save changes
    System.out.println("Transaction successful");
} catch (SQLException e) {
    conn.rollback();  // Revert changes
    System.err.println("Transaction failed: " + e.getMessage());
}
```

### **Q: Why is transaction important in borrowBook()?**
**A:** Ensures both operations succeed or fail together:
1. Decrement book copies ✅
2. Create loan record ✅
If either fails, both are rolled back to maintain data consistency.

---

## **5. CRUD Operations**

### **Q: What are CRUD operations?**
**A:**
- **Create**: INSERT records (`addBook()`, `addMember()`)
- **Read**: SELECT queries (`getBookByISBN()`, `listAllBooks()`)
- **Update**: UPDATE records (`updateBookCopies()`)
- **Delete**: DELETE records (`deleteBook()`)

### **Q: How to get auto-generated keys after INSERT?**
**A:**
```java
PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
ps.executeUpdate();

ResultSet generatedKeys = ps.getGeneratedKeys();
if (generatedKeys.next()) {
    long newId = generatedKeys.getLong(1);
    System.out.println("New ID: " + newId);
}
```

### **Q: How to handle ResultSet?**
**A:**
```java
try (ResultSet rs = ps.executeQuery()) {
    while (rs.next()) {
        int id = rs.getInt("book_id");
        String title = rs.getString("title");
        int copies = rs.getInt("copies");
        // Process data
    }
}
```

---

## **6. Exception Handling in JDBC**

### **Q: What is SQLException?**
**A:** Checked exception that provides database access error information:
- Error message
- SQL state
- Error code
- Cause (chained exception)

### **Q: How to handle database exceptions?**
**A:**
```java
try {
    // Database operations
} catch (SQLException e) {
    System.err.println("Error Code: " + e.getErrorCode());
    System.err.println("SQL State: " + e.getSQLState());
    System.err.println("Message: " + e.getMessage());
}
```

### **Q: What are common SQLException scenarios?**
**A:**
- **Connection failures**: Database unavailable
- **Constraint violations**: Duplicate keys, foreign key violations
- **Syntax errors**: Invalid SQL
- **Data type mismatches**: Wrong parameter types

---

## **7. Database Design & Relationships**

### **Q: Explain the database schema relationships**
**A:**
- **Books ↔ Loans**: One-to-Many (one book can have multiple loans)
- **Members ↔ Loans**: One-to-Many (one member can have multiple loans)
- **Foreign Keys**: `book_id` and `member_id` in loans table

### **Q: What is referential integrity?**
**A:** Ensures relationships between tables remain consistent:
- Cannot delete a book that has active loans
- Cannot create loan for non-existent book/member
- Maintains data consistency across related tables

### **Q: How does foreign key constraint affect delete operation?**
**A:**
```java
public static void deleteBook(int bookId) {
    // This will fail if book has existing loans due to foreign key constraint
    String sql = "DELETE FROM books WHERE book_id = ?";
    // Error: Cannot delete or update a parent row: a foreign key constraint fails
}
```

---

## **8. Performance & Best Practices**

### **Q: Why close database resources?**
**A:**
- **Prevent resource leaks**: Database connections are limited
- **Free memory**: ResultSets and Statements consume memory
- **Avoid connection pool exhaustion**: In web applications

### **Q: What is connection pooling?**
**A:** Technique of creating and maintaining a pool of database connections that can be reused, improving performance by avoiding frequent connection establishment.

### **Q: How to improve search performance?**
**A:**
- Add indexes on frequently searched columns (ISBN, email)
- Use WHERE clauses efficiently
- Limit result sets using LIMIT
- Use database-specific optimization

---

## **9. Security Considerations**

### **Q: How does PreparedStatement prevent SQL injection?**
**A:** It separates SQL structure from data:
- SQL query template with `?` placeholders
- Data bound separately using setter methods
- Database treats data as values, not executable code

### **Q: What other security measures should be taken?**
**A:**
- **Validate inputs**: Check data before database operations
- **Use least privilege**: Database user with minimal permissions
- **Encrypt sensitive data**: Passwords, personal information
- **Sanitize outputs**: Prevent XSS in web applications

---

## **10. Real-world Applications**

### **Q: How would you extend this library system?**
**A:**
- **Reservation system**: Queue for popular books
- **Fine calculation**: Automatic fine for overdue books
- **Email notifications**: Reminder for due dates
- **Reports**: Popular books, member activity
- **Web interface**: Browser-based access

### **Q: How to make it production-ready?**
**A:**
- **Configuration files**: Externalize database credentials
- **Logging framework**: Proper error logging
- **Connection pooling**: Better performance
- **Data access layer**: Separate from business logic
- **Unit tests**: Test database operations

---

## **Quick Revision Checklist:**

✅ **JDBC Components**: DriverManager, Connection, PreparedStatement, ResultSet  
✅ **Connection URLs**: Database-specific format  
✅ **PreparedStatement**: Prevents SQL injection, better performance  
✅ **Transactions**: ACID properties, commit/rollback  
✅ **CRUD Operations**: INSERT, SELECT, UPDATE, DELETE  
✅ **Exception Handling**: SQLException, try-with-resources  
✅ **Best Practices**: Resource cleanup, input validation  

## **Common Viva Questions:**
- "What is the advantage of PreparedStatement over Statement?"
- "How do you handle transactions in JDBC?"
- "What happens if we don't close database connections?"
- "How does foreign key constraint affect delete operations?"
- "What is SQL injection and how to prevent it?"

**Remember**: Always relate answers to the library management example - it makes database concepts more practical and understandable!

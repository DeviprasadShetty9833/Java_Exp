import java.sql.*;



public class LibraryApp {

    private static final String URL = "jdbc:sqlite:library.db"; // Change for MySQL/Postgres

    private static final String USER = "root"; // For MySQL/Postgres

    private static final String PASSWORD = "password"; 



    // Establish connection

    private static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }



    // Add a book

    public static void addBook(String isbn, String title, String author, int copies) {

        String sql = "INSERT INTO books(isbn, title, author, copies) VALUES(?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);

            ps.setString(2, title);

            ps.setString(3, author);

            ps.setInt(4, copies);

            ps.executeUpdate();

            System.out.println("✅ Book added successfully.");

        } catch (SQLException e) {

            System.err.println("❌ Error: " + e.getMessage());

        }

    }



    // Borrow a book (Transaction)

    public static void borrowBook(int memberId, int bookId) {

        String updateBook = "UPDATE books SET copies = copies - 1 WHERE book_id = ? AND copies > 0";

        String insertLoan = "INSERT INTO loans(book_id, member_id, due_date) VALUES(?, ?, DATE('now', '+14 day'))";



        try (Connection conn = getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(updateBook);

                 PreparedStatement ps2 = conn.prepareStatement(insertLoan)) {



                ps1.setInt(1, bookId);

                int updated = ps1.executeUpdate();



                if (updated == 0) {

                    throw new SQLException("No copies available for book " + bookId);

                }



                ps2.setInt(1, bookId);

                ps2.setInt(2, memberId);

                ps2.executeUpdate();



                conn.commit();

                System.out.println("📖 Book borrowed successfully.");

            } catch (SQLException e) {

                conn.rollback();

                System.err.println("❌ Transaction failed: " + e.getMessage());

            }

        } catch (SQLException e) {

            System.err.println("❌ Connection error: " + e.getMessage());

        }

    }



    // Main menu (simplified)

    public static void main(String[] args) {

        addBook("123456", "Effective Java", "Joshua Bloch", 5);

        borrowBook(1, 1); // Assume member_id=1, book_id=1

    }

}


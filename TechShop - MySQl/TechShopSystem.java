import java.sql.*;

// Database Connection Class
class DatabaseConnector {
    
    private static final String URL = "jdbc:mysql://localhost:3306/TechShopDB";
    private static final String USER = "root";
    private static final String PASSWORD = "NikhiL@8495";
    private Connection connection;

    public Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to TechShopDB successfully.");
        }
        return connection;
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Disconnected from TechShopDB.");
        }
    }
}

// Customer Class with CRUD Operations
class Customers {
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private static DatabaseConnector db = new DatabaseConnector();

    public Customers(int customerID, String firstName, String lastName, String email, String phone, String address) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Register a new customer (CREATE operation)
    public void registerCustomer() {
        String query = "INSERT INTO Customers (firstName, lastName, email, phone, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.executeUpdate();
            System.out.println("Customer registered successfully.");
        } catch (SQLException e) {
            System.err.println("Error registering customer: " + e.getMessage());
        }
    }

    // Retrieve customer information (READ operation)
    public static void getCustomerByEmail(String email) {
        String query = "SELECT * FROM Customers WHERE email = ?";
        try (Connection conn = db.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Customer Found: " + rs.getString("firstName") + " " + rs.getString("lastName"));
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
        }
    }

    // Update customer details (UPDATE operation)
    public void updateCustomerDetails(String newEmail, String newPhone) {
        String query = "UPDATE Customers SET email = ?, phone = ? WHERE customerID = ?";
        try (Connection conn = db.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setString(2, newPhone);
            stmt.setInt(3, customerID);
            stmt.executeUpdate();
            System.out.println("Customer details updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
        }
    }

    // Delete a customer (DELETE operation)
    public void deleteCustomer() {
        String query = "DELETE FROM Customers WHERE customerID = ?";
        try (Connection conn = db.connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            stmt.executeUpdate();
            System.out.println("Customer deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
        }
    }
}

// Main class to test database operations
public class TechShopSystem {
    public static void main(String[] args) {
        Customers newCustomer = new Customers(0, "John", "Doe", "john.doe@example.com", "1234567890", "123 Main St");
        newCustomer.registerCustomer();
        Customers.getCustomerByEmail("john.doe@example.com");
        newCustomer.updateCustomerDetails("john.doe@newmail.com", "0987654321");
        newCustomer.deleteCustomer();
    }
}

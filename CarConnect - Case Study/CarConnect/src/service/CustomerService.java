package service;

import entity.Customer;
import exception.InvalidInputException;
import util.DatabaseContext;

import java.sql.*;

public class CustomerService {

	public Customer getCustomerByUsername(String username) {
	    String query = "SELECT * FROM Customer WHERE Username = ?";
	    try (Connection conn = DatabaseContext.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, username);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return new Customer(
	                    rs.getInt("CustomerID"),
	                    rs.getString("FirstName"),
	                    rs.getString("LastName"),
	                    rs.getString("Email"),
	                    rs.getString("PhoneNumber"),
	                    rs.getString("Address"),
	                    rs.getString("Username"),
	                    rs.getString("Password"),
	                    rs.getTimestamp("RegistrationDate")
	            );
	        }

	    } catch (SQLException e) {
	        System.out.println("Database error while fetching customer: " + e.getMessage());
	    }
	    return null;
	}


    public boolean updateCustomerInfo(Customer updatedCustomer) throws InvalidInputException {
        String query = "UPDATE Customer SET first_name = ?, last_name = ?, email = ?, phone = ?, password = ? WHERE customer_id = ?";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, updatedCustomer.getFirstName());
            stmt.setString(2, updatedCustomer.getLastName());
            stmt.setString(3, updatedCustomer.getEmail());
            stmt.setString(4, updatedCustomer.getPhoneNumber());
            stmt.setString(5, updatedCustomer.getPassword());
            stmt.setInt(6, updatedCustomer.getCustomerID());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new InvalidInputException("Failed to update customer: " + e.getMessage());
        }
    }
    
    public boolean deleteCustomer(int customerId) {
        try (Connection conn = DatabaseContext.getConnection()) {
            String sql = "DELETE FROM Customer WHERE customerId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }

    public boolean registerCustomer(Customer customer) {
        try (Connection conn = DatabaseContext.getConnection()) {
            String sql = "INSERT INTO Customer (FirstName, LastName, Email, PhoneNumber, Address, Username, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            ps.setString(5, customer.getAddress());
            ps.setString(6, customer.getUsername());
            ps.setString(7, customer.getPassword());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

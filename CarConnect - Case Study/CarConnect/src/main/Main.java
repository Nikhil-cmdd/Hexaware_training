package main;

import entity.Customer;
import service.CustomerService;
import util.AuthenticationService;

import java.sql.Timestamp;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        // Create service
        CustomerService customerService = new CustomerService();

        // Register a new customer
        Customer customer = new Customer(11, "Rahul", "Sharma", "rahul.sharma@gmail.com", "9876543210", "Pune, Maharashtra", "rahul123", "password123", Timestamp.from(Instant.now()));

        customerService.registerCustomer(customer);

        // Authenticate the customer
        Customer fetched = customerService.getCustomerByUsername("rahul123");

        if (fetched != null && AuthenticationService.authenticateCustomer(fetched, "password123")) {
            System.out.println("Login successful! Welcome, " + fetched.getFirstName());
        } else {
            System.out.println("Authentication failed.");
        }
    }
}
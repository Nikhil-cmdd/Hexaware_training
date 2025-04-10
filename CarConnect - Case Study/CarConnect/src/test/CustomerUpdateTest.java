package test;

import entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CustomerService;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerUpdateTest {
    private CustomerService customerService;
    private Customer customer;

    @BeforeEach
    public void setup() {
        customerService = new CustomerService();
        customer = new Customer(12, "Sneha", "Verma", "sneha@gmail.com", "9876543210", "Mumbai, Maharashtra", "sneha", "pass456", Timestamp.from(Instant.now()));
        customerService.registerCustomer(customer);
    }

    @Test
    public void testUpdateCustomerInformation() {
        customer.setEmail("updatedsneha@gmail.com");
        boolean updated = customerService.updateCustomerInfo(customer);
        assertTrue(updated);

        Customer updatedCustomer = customerService.getCustomerByUsername(customer.getUsername());
        assertEquals("updatedsneha@gmail.com", updatedCustomer.getEmail());
    }
}

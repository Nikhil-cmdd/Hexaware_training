package test;

import entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CustomerService;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerUpdateTest {
    private CustomerService customerService;
    private Customer customer;

    @BeforeEach
    public void setup() {
        customerService = new CustomerService();
        customer = new Customer("Sneha", "Verma", "sneha@gmail.com", "9876543210",
                "Pune", "snehauser", "mypassword");
        customerService.registerCustomer(customer);
        customer = customerService.getCustomerByUsername("snehauser");
    }

    @Test
    public void testUpdateCustomerInformation() {
        System.out.println("Before update: " + customer.getEmail());

        customer.setEmail("updatedsneha@gmail.com");
        boolean updated = customerService.updateCustomerInfo(customer);
        assertTrue(updated);

        Customer updatedCustomer = customerService.getCustomerByUsername("snehauser");
        System.out.println("After update: " + updatedCustomer.getEmail());

        assertEquals("updatedsneha@gmail.com", updatedCustomer.getEmail());
    }
}

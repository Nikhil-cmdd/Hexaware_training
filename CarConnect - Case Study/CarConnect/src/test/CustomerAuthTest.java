package test;

import entity.Customer;
import exception.AuthenticationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CustomerService;
import util.AuthenticationService;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerAuthTest {
    private CustomerService customerService;
    private Customer testCustomer;

    @BeforeEach
    public void setup() {
        customerService = new CustomerService();
        testCustomer = new Customer(2, "Amit", "Patel", "amit.patel@gmail.com", "9876543211", "Mumbai, Maharashtra", "amitp", "hashedpassword2", Timestamp.from(Instant.now()));
        customerService.registerCustomer(testCustomer);
    }

    @Test
    public void testCustomerAuthenticationWithInvalidCredentials() {
        Customer fetched = customerService.getCustomerByUsername("amitp");
        AuthenticationService authService = new AuthenticationService();
        assertThrows(AuthenticationException.class, () -> {
            authService.authenticateCustomer(fetched.getUsername(), "wrongpass");
        });

    }
}

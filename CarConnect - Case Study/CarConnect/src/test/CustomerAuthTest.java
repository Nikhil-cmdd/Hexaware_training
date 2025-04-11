package test;

import entity.Customer;
import exception.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.CustomerService;
import util.AuthenticationService;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerAuthTest {
    private CustomerService customerService;
    private AuthenticationService authService;

    @BeforeEach
    public void setup() {
        customerService = new CustomerService();
        authService = new AuthenticationService();

        Customer customer = new Customer("Amit", "Patel", "amit.patel@gmail.com", "9876543211",
                "Mumbai", "amitp", "pass123");
        customerService.registerCustomer(customer);
    }

    @Test
    public void testInvalidCustomerAuthentication() {
        assertThrows(AuthenticationException.class, () -> {
            authService.authenticateCustomer("amitp", "wrongpass");
        });
    }
}

package util;

import entity.Admin;
import entity.Customer;
import exception.AuthenticationException;
import service.AdminService;
import service.CustomerService;

public class AuthenticationService {

    private final AdminService adminService = new AdminService();
    private final CustomerService customerService = new CustomerService();

    public Admin authenticateAdmin(String username, String password) throws AuthenticationException {
        Admin admin = adminService.getAdminByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        } else {
            throw new AuthenticationException("Invalid admin credentials!");
        }
    }

    public Customer authenticateCustomer(String username, String password) throws AuthenticationException {
        Customer customer = customerService.getCustomerByUsername(username);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        } else {
            throw new AuthenticationException("Invalid customer credentials!");
        }
    }
}

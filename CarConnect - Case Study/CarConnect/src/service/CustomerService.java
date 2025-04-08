package service;

import entity.Customer;
import interfaces.ICustomerService;

import java.util.HashMap;
import java.util.Map;

public class CustomerService implements ICustomerService {
    private final Map<Integer, Customer> customerDb = new HashMap<>();

    @Override
    public Customer getCustomerById(int customerId) {
        return customerDb.get(customerId);
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return customerDb.values().stream()
                .filter(c -> c.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    @Override
    public boolean registerCustomer(Customer customer) {
        if (customerDb.containsKey(customer.getCustomerID())) return false;
        customerDb.put(customer.getCustomerID(), customer);
        return true;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        if (!customerDb.containsKey(customer.getCustomerID())) return false;
        customerDb.put(customer.getCustomerID(), customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(int customerId) {
        return customerDb.remove(customerId) != null;
    }
}

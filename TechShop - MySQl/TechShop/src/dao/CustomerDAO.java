package dao;

import java.util.List;
import entity.Customer;

public interface CustomerDAO {
    boolean addCustomer(Customer customer);
    Customer getCustomerById(int customerId);
    List<Customer> getAllCustomers();
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int customerId);
    Customer login(String email, String password);
}

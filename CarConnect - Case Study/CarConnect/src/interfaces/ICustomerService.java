package interfaces;

import entity.Customer;

public interface ICustomerService {
    Customer getCustomerById(int customerId);
    Customer getCustomerByUsername(String username);
    boolean registerCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(int customerId);
    
}

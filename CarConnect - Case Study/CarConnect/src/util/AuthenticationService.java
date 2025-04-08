package util;

import entity.Customer;

public class AuthenticationService {
    public static boolean authenticate(String actualPassword, String inputPassword) {
        return actualPassword.equals(inputPassword);
    }

    public static boolean authenticateCustomer(Customer fetched, String inputPassword) {
        return fetched.authenticate(inputPassword);
    }
}

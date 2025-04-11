package main;

import dao.*;
import dao.impl.*;
import entity.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        CustomerDAO customerDAO = new CustomerDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        InventoryDAO inventoryDAO = new InventoryDAOImpl();

        boolean exit = false;
        System.out.println("\n===== Welcome to TechShop =====\n");

        while (!exit) {
            System.out.println("\n===== Main Menu =====");
            System.out.println("1. Register Customer");
            System.out.println("2. View All Products");
            System.out.println("3. Place an Order");
            System.out.println("4. View All Orders");
            System.out.println("5. Check Inventory");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter First Name: ");
                        String firstName = sc.nextLine();
                        System.out.print("Enter Last Name: ");
                        String lastName = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Phone: ");
                        String phone = sc.nextLine();
                        System.out.print("Enter Address: ");
                        String address = sc.nextLine();

                        Customer customer = new Customer(firstName, lastName, email, phone, address);
                        customerDAO.addCustomer(customer);
                        System.out.println("Customer registered successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        List<Product> products = productDAO.getAllProducts();
                        System.out.println("\nAvailable Products:");
                        for (Product p : products) {
                            System.out.println(p);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Enter Customer ID: ");
                        int customerId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Product ID: ");
                        int productId = sc.nextInt();

                        System.out.print("Enter Quantity: ");
                        int quantity = sc.nextInt();

                        Order order = new Order(customerId, productId, quantity);
                        boolean success = orderDAO.placeOrder(order);

                        if (success) {
                            System.out.println("Order placed successfully!");
                        } else {
                            System.out.println("Failed to place order. Check stock or inputs.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        List<Order> orders = orderDAO.getAllOrders();
                        for (Order o : orders) {
                            System.out.println(o);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        List<Inventory> inventoryList = inventoryDAO.getAllInventory();
                        for (Inventory i : inventoryList) {
                            System.out.println(i);
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 6:
                    exit = true;
                    System.out.println("Thank you for using TechShop. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        sc.close();
    }
}
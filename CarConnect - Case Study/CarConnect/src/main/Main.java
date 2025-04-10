package main;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import entity.*;
import exception.*;
import service.*;
import util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthenticationService authService = new AuthenticationService();
        CustomerService customerService = new CustomerService();
        //AdminService adminService = new AdminService();
        VehicleService vehicleService = new VehicleService();
        ReservationService reservationService = new ReservationService();

        System.out.println(" Welcome to CarConnect - Car Rental System");

        while (true) {
            try {
                System.out.println("\n1. Customer Login");
                System.out.println("2. Customer Registration");
                System.out.println("3. Admin Login ");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter Username: ");
                        String custUsername = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String custPassword = scanner.nextLine();

                        Customer customer = authService.authenticateCustomer(custUsername, custPassword);
                        System.out.println("Login successful! Welcome " + customer.getFirstName());

                        boolean custSession = true;
                        while (custSession) {
                            System.out.println("\n--- Customer Menu ---");
                            System.out.println("1. View Available Vehicles");
                            System.out.println("2. Reserve a Vehicle");
                            System.out.println("3. View My Reservations");
                            System.out.println("4. Cancel Reservation");
                            System.out.println("7. Update My Info");
                            System.out.println("6. Delete My Account");
                            System.out.println("8. Logout");
                            System.out.print("Enter choice: ");
                            int cOpt = Integer.parseInt(scanner.nextLine());

                            switch (cOpt) {
                                case 1:
                                    vehicleService.getAvailableVehicles();
                                    break;
                                case 2:
                                    System.out.print("Enter Vehicle ID: ");
                                    int vId = Integer.parseInt(scanner.nextLine());
                                    System.out.print("Enter Start Date (yyyy-mm-dd): ");
                                    String startDateStr = scanner.nextLine();
                                    System.out.print("Enter End Date (yyyy-mm-dd): ");
                                    String endDateStr = scanner.nextLine();

                                    Timestamp startTimestamp = Timestamp.valueOf(startDateStr + " 00:00:00");
                                    Timestamp endTimestamp = Timestamp.valueOf(endDateStr + " 00:00:00");

                                    Vehicle vehicle = vehicleService.getVehicleById(vId);
                                    if (vehicle == null) {
                                        System.out.println("Vehicle not found.");
                                        break;
                                    }

                                    long duration = (endTimestamp.getTime() - startTimestamp.getTime()) / (1000 * 60 * 60 * 24);
                                    if (duration <= 0) {
                                        System.out.println("Invalid reservation period.");
                                        break;
                                    }

                                    double totalCost = duration * vehicle.getDailyRate();

                                    Reservation reservation = new Reservation(
                                        0, // ReservationID auto-increment
                                        customer.getCustomerID(),
                                        vId,
                                        startTimestamp,
                                        endTimestamp,
                                        totalCost,
                                        "confirmed"
                                    );

                                    if (reservationService.createReservation(reservation)) {
                                        System.out.println("Reservation created successfully!");
                                    } else {
                                        System.out.println("Failed to create reservation.");
                                    }
                                    break;

                                case 3:
                                    List<Reservation> myReservations = reservationService.getReservationsByCustomerId(customer.getCustomerID());
                                    if (myReservations.isEmpty()) {
                                        System.out.println("No reservations found.");
                                    } else {
                                        for (Reservation res : myReservations) {
                                            System.out.println(res);
                                        }
                                    }
                                    break;

                                case 4:
                                    System.out.print("Enter Reservation ID to cancel: ");
                                    int rId = Integer.parseInt(scanner.nextLine());
                                    boolean canceled = reservationService.cancelReservation(rId);
                                    if (canceled) {
                                        System.out.println("Reservation canceled successfully.");
                                    } else {
                                        System.out.println("Failed to cancel reservation. Check the ID.");
                                    }
                                    break;

                                case 5:
                                    System.out.print("Enter new Email: ");
                                    String email = scanner.nextLine();
                                    System.out.print("Enter new Phone: ");
                                    String phone = scanner.nextLine();
                                    System.out.print("Enter new Address: ");
                                    String address = scanner.nextLine();

                                    customer.setEmail(email);
                                    customer.setPhoneNumber(phone);
                                    customer.setAddress(address);

                                    if (customerService.updateCustomerInfo(customer)) {
                                        System.out.println("Customer info updated successfully.");
                                    } else {
                                        System.out.println("Update failed.");
                                    }
                                    break;

                                case 6:
                                    System.out.print("Are you sure you want to delete your account? (yes/no): ");
                                    String confirm = scanner.nextLine();
                                    if (confirm.equalsIgnoreCase("yes")) {
                                        if (customerService.deleteCustomer(customer.getCustomerID())) {
                                            System.out.println("Account deleted. Logging out...");
                                            custSession = false;
                                        } else {
                                            System.out.println("Failed to delete account.");
                                        }
                                    }
                                    break;
                                    
                                case 8:
                                    custSession = false;
                                    break;
                                    
                                default:
                                    System.out.println("Invalid option!");
                            }
                        }
                        break;
                        
                    case 2:
                    	System.out.println("--- Customer Registration ---");
                    	System.out.print("First Name: ");
                    	String firstName = scanner.nextLine();
                    	System.out.print("Last Name: ");
                    	String lastName = scanner.nextLine();
                    	System.out.print("Email: ");
                    	String email = scanner.nextLine();
                    	System.out.print("Phone Number: ");
                    	String phone = scanner.nextLine();
                    	System.out.print("Address: ");
                    	String address = scanner.nextLine();
                    	System.out.print("Choose Username: ");
                    	String username = scanner.nextLine();
                    	System.out.print("Choose Password: ");
                    	String password = scanner.nextLine();

                    	Customer newCustomer = new Customer(firstName, lastName, email, phone, address, username, password);
                    	if (customerService.registerCustomer(newCustomer)) {
                    	    System.out.println("Registration successful! You can now log in.");
                    	} else {
                    	    System.out.println("Registration failed. Please try again.");
                    	}
                        break;

                    case 3:
                        System.out.print("Enter Admin Username: ");
                        String adminUsername = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String adminPassword = scanner.nextLine();

                        Admin admin = authService.authenticateAdmin(adminUsername, adminPassword);
                        System.out.println("Login successful! Welcome Admin " + admin.getFirstName());

                        boolean adminSession = true;
                        while (adminSession) {
                            System.out.println("\n--- Admin Menu ---");
                            System.out.println("1. Add Vehicle");
                            System.out.println("2. Update Vehicle");
                            System.out.println("3. View All Vehicles");
                            System.out.println("4. View Reservations");
                            System.out.println("5. Generate Report");
                            System.out.println("6. Logout");
                            System.out.print("Enter choice: ");
                            int aOpt = Integer.parseInt(scanner.nextLine());

                            switch (aOpt) {
                            case 1:
                                System.out.print("Enter Model: ");
                                String model = scanner.nextLine();
                                System.out.print("Enter Make: ");
                                String make = scanner.nextLine();
                                System.out.print("Enter Year: ");
                                int year = Integer.parseInt(scanner.nextLine());
                                System.out.print("Enter Color: ");
                                String color = scanner.nextLine();
                                System.out.print("Enter Registration Number: ");
                                String regNumber = scanner.nextLine();
                                System.out.print("Enter Daily Rate: ");
                                double rate = Double.parseDouble(scanner.nextLine());

                                Vehicle vehicle = new Vehicle(0, model, make, year, color, regNumber, true, rate); // 0 for auto-increment ID

                                if (vehicleService.addVehicle(vehicle)) {
                                    System.out.println("Vehicle added successfully!");
                                } else {
                                    System.out.println("Failed to add vehicle.");
                                }
                                break;
                            case 2:
                                System.out.print("Enter Vehicle ID to update: ");
                                int vehicleId = Integer.parseInt(scanner.nextLine());

                                System.out.print("Enter Updated Model: ");
                                String updatedModel = scanner.nextLine();
                                System.out.print("Enter Updated Make: ");
                                String updatedMake = scanner.nextLine();
                                System.out.print("Enter Updated Year: ");
                                int updatedYear = Integer.parseInt(scanner.nextLine());
                                System.out.print("Enter Updated Color: ");
                                String updatedColor = scanner.nextLine();
                                System.out.print("Enter Updated Registration Number: ");
                                String updatedRegNum = scanner.nextLine();
                                System.out.print("Enter Updated Daily Rate: ");
                                double updatedRate = Double.parseDouble(scanner.nextLine());
                                System.out.print("Is Vehicle Available (true/false): ");
                                boolean updatedAvailability = Boolean.parseBoolean(scanner.nextLine());

                                Vehicle updatedVehicle = new Vehicle(
                                    vehicleId,
                                    updatedModel,
                                    updatedMake,
                                    updatedYear,
                                    updatedColor,
                                    updatedRegNum,
                                    updatedAvailability,
                                    updatedRate
                                );

                                if (vehicleService.updateVehicle(updatedVehicle)) {
                                    System.out.println("Vehicle updated successfully!");
                                } else {
                                    System.out.println("Vehicle update failed.");
                                }
                                break;

                            case 3:
                                List<Vehicle> allVehicles = vehicleService.getAllVehicles();
                                if (allVehicles.isEmpty()) {
                                    System.out.println("No vehicles available.");
                                } else {
                                    for (Vehicle v : allVehicles) {
                                        System.out.println(v);
                                    }
                                }
                                break;

                            case 4:
                                List<Reservation> reservations = reservationService.getAllReservations();
                                if (reservations.isEmpty()) {
                                    System.out.println("No reservations found.");
                                } else {
                                    System.out.println("--- All Reservations ---");
                                    for (Reservation res : reservations) {
                                        System.out.println("Reservation ID: " + res.getReservationID());
                                        System.out.println("Customer ID: " + res.getCustomerID());
                                        System.out.println("Vehicle ID: " + res.getVehicleID());
                                        System.out.println("Start Date: " + res.getStartDate());
                                        System.out.println("End Date: " + res.getEndDate());
                                        System.out.println("Total Cost: " + res.getTotalCost());
                                        System.out.println("Status: " + res.getStatus());
                                        System.out.println("----------------------------");
                                    }
                                }
                                break;

                                case 5:
                                	ReportGenerator.generateReservationReport(reservationService.getAllReservations());
                                    break;
                                case 6:
                                    adminSession = false;
                                    break;
                                default:
                                    System.out.println("Invalid option!");
                            }
                        }
                        break;

                    case 4:
                        System.out.println("Thank you for using CarConnect!");
                        scanner.close();
                        System.exit(0);

                }

            } catch (AuthenticationException | ReservationNotFoundException | VehicleNotFoundException | InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}

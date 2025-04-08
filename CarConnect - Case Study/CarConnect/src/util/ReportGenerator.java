package util;

import entity.Reservation;
import entity.Vehicle;

import java.util.List;

public class ReportGenerator {

    public static void generateReservationReport(List<Reservation> reservations) {
        System.out.println("=== Reservation Report ===");
        for (Reservation r : reservations) {
            System.out.println("Reservation ID: " + r.getReservationID());
            System.out.println("Customer ID: " + r.getCustomerID());
            System.out.println("Vehicle ID: " + r.getVehicleID());
            System.out.println("Start Date: " + r.getStartDate());
            System.out.println("End Date: " + r.getEndDate());
            System.out.println("Total Cost: " + r.getTotalCost());
            System.out.println("Status: " + r.getStatus());
            System.out.println("----------------------------");
        }
    }

    public static void generateVehicleReport(List<Vehicle> vehicles) {
        System.out.println("=== Vehicle Report ===");
        for (Vehicle v : vehicles) {
            System.out.println("Vehicle ID: " + v.getVehicleID());
            System.out.println("Model: " + v.getModel());
            System.out.println("Make: " + v.getMake());
            System.out.println("Year: " + v.getYear());
            System.out.println("Available: " + v.isAvailability());
            System.out.println("Daily Rate: " + v.getDailyRate());
            System.out.println("----------------------------");
        }
    }
}

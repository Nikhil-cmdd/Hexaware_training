package service;

import entity.Vehicle;
import interfaces.IVehicleService;
import util.DatabaseContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleService implements IVehicleService {

    @Override
    public Vehicle getVehicleById(int vehicleId) {
        String query = "SELECT * FROM Vehicle WHERE VehicleID = ?";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractVehicleFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM Vehicle WHERE Availability = TRUE";

        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Available Vehicles ---");
            while (rs.next()) {
                Vehicle vehicle = extractVehicleFromResultSet(rs);
                vehicles.add(vehicle);
                System.out.println("ID: " + vehicle.getVehicleID() +
                                   ", Model: " + vehicle.getModel() +
                                   ", Make: " + vehicle.getMake() +
                                   ", Year: " + vehicle.getYear() +
                                   ", Color: " + vehicle.getColor() +
                                   ", Reg#: " + vehicle.getRegistrationNumber() +
                                   ", Rate: ₹" + vehicle.getDailyRate() +
                                   ", Available: " + vehicle.isAvailability());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    
    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> allVehicles = new ArrayList<>();
        String query = "SELECT * FROM Vehicle";

        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                allVehicles.add(extractVehicleFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allVehicles;
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO Vehicle (Model, Make, Year, Color, RegistrationNumber, Availability, DailyRate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, vehicle.getModel());
            stmt.setString(2, vehicle.getMake());
            stmt.setInt(3, vehicle.getYear());
            stmt.setString(4, vehicle.getColor());
            stmt.setString(5, vehicle.getRegistrationNumber());
            stmt.setBoolean(6, vehicle.isAvailability());
            stmt.setDouble(7, vehicle.getDailyRate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        String query = "UPDATE Vehicle SET Model = ?, Make = ?, Year = ?, Color = ?, RegistrationNumber = ?, Availability = ?, DailyRate = ? WHERE VehicleID = ?";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, vehicle.getModel());
            stmt.setString(2, vehicle.getMake());
            stmt.setInt(3, vehicle.getYear());
            stmt.setString(4, vehicle.getColor());
            stmt.setString(5, vehicle.getRegistrationNumber());
            stmt.setBoolean(6, vehicle.isAvailability());
            stmt.setDouble(7, vehicle.getDailyRate());
            stmt.setInt(8, vehicle.getVehicleID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeVehicle(int vehicleId) {
        String query = "DELETE FROM Vehicle WHERE VehicleID = ?";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, vehicleId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Vehicle extractVehicleFromResultSet(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("VehicleID"),
                rs.getString("Model"),
                rs.getString("Make"),
                rs.getInt("Year"),
                rs.getString("Color"),
                rs.getString("RegistrationNumber"),
                rs.getBoolean("Availability"),
                rs.getDouble("DailyRate")
        );
    }
    
    public Vehicle getVehicleByRegistrationNumber(String registrationNumber) {
        Vehicle vehicle = null;
        String query = "SELECT * FROM Vehicle WHERE RegistrationNumber = ?";

        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, registrationNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                vehicle = new Vehicle(
                        rs.getInt("VehicleID"),
                        rs.getString("Model"),
                        rs.getString("Make"),
                        rs.getInt("Year"),
                        rs.getString("Color"),
                        rs.getString("RegistrationNumber"),
                        rs.getBoolean("Availability"),
                        rs.getDouble("DailyRate")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vehicle;
    }

}

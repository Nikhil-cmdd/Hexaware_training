package service;

import entity.Reservation;
import interfaces.IReservationService;
import util.DatabaseContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IReservationService {

    @Override
    public Reservation getReservationById(int reservationId) {
        String query = "SELECT * FROM Reservation WHERE ReservationID = ?";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractReservationFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reservation> getReservationsByCustomerId(int customerId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservation WHERE CustomerID = ?";

        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reservations.add(extractReservationFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    @Override
    public boolean createReservation(Reservation reservation) {
        String query = "INSERT INTO Reservation (CustomerID, VehicleID, StartDate, EndDate, TotalCost, Status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservation.getCustomerID());
            stmt.setInt(2, reservation.getVehicleID());
            stmt.setTimestamp(3, reservation.getStartDate());
            stmt.setTimestamp(4, reservation.getEndDate());
            stmt.setDouble(5, reservation.getTotalCost());
            stmt.setString(6, reservation.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        String query = "UPDATE Reservation SET CustomerID = ?, VehicleID = ?, StartDate = ?, EndDate = ?, TotalCost = ?, Status = ? WHERE ReservationID = ?";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservation.getCustomerID());
            stmt.setInt(2, reservation.getVehicleID());
            stmt.setTimestamp(3, reservation.getStartDate());
            stmt.setTimestamp(4, reservation.getEndDate());
            stmt.setDouble(5, reservation.getTotalCost());
            stmt.setString(6, reservation.getStatus());
            stmt.setInt(7, reservation.getReservationID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cancelReservation(int reservationId) {
        String query = "DELETE FROM Reservation WHERE ReservationID = ?";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, reservationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Reservation extractReservationFromResultSet(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("ReservationID"),
                rs.getInt("CustomerID"),
                rs.getInt("VehicleID"),
                rs.getTimestamp("StartDate"),
                rs.getTimestamp("EndDate"),
                rs.getDouble("TotalCost"),
                rs.getString("Status")
        );
    }
    
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservation";

        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("ReservationID"),
                        rs.getInt("CustomerID"),
                        rs.getInt("VehicleID"),
                        rs.getTimestamp("StartDate"),
                        rs.getTimestamp("EndDate"),
                        rs.getDouble("TotalCost"),
                        rs.getString("Status")
                );
                reservations.add(reservation);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching reservations: " + e.getMessage());
        }

        return reservations;
    }


}

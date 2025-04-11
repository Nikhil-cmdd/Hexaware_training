package dao.impl;

import dao.OrderDetailDAO;
import entity.OrderDetail;
import exception.OrderNotFoundException;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public boolean addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO OrderDetails (OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderDetail.getOrderId());
            stmt.setInt(2, orderDetail.getProductId());
            stmt.setInt(3, orderDetail.getQuantity());
            stmt.setDouble(4, orderDetail.getPrice());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding order detail: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE OrderID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                details.add(new OrderDetail(
                        rs.getInt("OrderDetailID"),
                        rs.getInt("OrderID"),
                        rs.getInt("ProductID"),
                        rs.getInt("Quantity"),
                        rs.getDouble("Price")
                ));
            }
            if (details.isEmpty()) {
                throw new OrderNotFoundException("No order details found for OrderID: " + orderId);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order details: " + e.getMessage());
        }
        return details;
    }

    @Override
    public boolean updateOrderDetail(OrderDetail orderDetail) {
        // Your JDBC logic to update the record in DB
        // Example:
        String sql = "UPDATE OrderDetails SET quantity = ?, price = ? WHERE orderDetailId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderDetail.getQuantity());
            pstmt.setDouble(2, orderDetail.getPrice());
            pstmt.setInt(3, orderDetail.getOrderDetailId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean deleteOrderDetail(int orderDetailId) {
        String sql = "DELETE FROM OrderDetails WHERE orderDetailId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderDetailId);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

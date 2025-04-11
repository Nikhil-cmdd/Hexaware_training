package dao.impl;

import dao.OrderDAO;
import util.DBConnection;
import entity.Order;
import exception.OrderNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean placeOrder(Order order) {
        String query = "INSERT INTO Orders (CustomerID, orderDate, totalAmount, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setInt(1, order.getCustomerId());
            pstmt.setTimestamp(2, order.getOrderDate());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        String query = "UPDATE Orders SET status = ? WHERE orderID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            int rows = pstmt.executeUpdate();
            if (rows == 0) throw new OrderNotFoundException("Order ID not found: " + orderId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Order> getOrdersByCustomerId(int customerID) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE customerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("orderID"),
                        rs.getInt("customerID"),
                        rs.getTimestamp("orderDate"),
                        rs.getDouble("totalAmount"),
                        rs.getString("status")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.OrderID, o.CustomerID, od.ProductID, od.Quantity, o.OrderDate, o.Status " + "FROM Orders o " + "JOIN OrderDetails od ON o.OrderID = od.OrderID";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int OrderID = rs.getInt("OrderID");
                int CustomerID = rs.getInt("CustomerID");
                int ProductID = rs.getInt("ProductID");
                int quantity = rs.getInt("quantity");
                Timestamp orderDate = rs.getTimestamp("orderDate");
                String status = rs.getString("status");

                Order order = new Order(OrderID, CustomerID, ProductID, quantity, orderDate, status);
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

}

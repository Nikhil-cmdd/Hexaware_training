package dao.impl;

import dao.InventoryDAO;
import entity.Inventory;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOImpl implements InventoryDAO {

    private Connection conn;

    public InventoryDAOImpl() {
        conn = DBConnection.getConnection();
    }

    @Override
    public boolean addInventory(Inventory inventory) {
        String query = "INSERT INTO Inventory (ProductID, QuantityInStock) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, inventory.getProductID());
            pstmt.setInt(2, inventory.getQuantityInStock());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateInventory(Inventory inventory) {
        String query = "UPDATE Inventory SET QuantityInStock = ? WHERE InventoryID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, inventory.getQuantityInStock());
            pstmt.setInt(2, inventory.getInventoryID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteInventory(int inventoryID) {
        String query = "DELETE FROM Inventory WHERE InventoryID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, inventoryID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Inventory getInventoryById(int inventoryID) {
        String query = "SELECT * FROM Inventory WHERE InventoryID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, inventoryID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Inventory(
                    rs.getInt("InventoryID"),
                    rs.getInt("ProductID"),
                    rs.getInt("QuantityInStock"),
                    rs.getTimestamp("LastStockUpdate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = new ArrayList<>();
        String query = "SELECT * FROM Inventory";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Inventory inventory = new Inventory(
                    rs.getInt("InventoryID"),
                    rs.getInt("ProductID"),
                    rs.getInt("QuantityInStock"),
                    rs.getTimestamp("LastStockUpdate")
                );
                inventoryList.add(inventory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryList;
    }
}

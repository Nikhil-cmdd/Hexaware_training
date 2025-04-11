package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.DatabaseConnectionException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/TechShop";
    private static final String USER = "root";
    private static final String PASSWORD = "Nikhil@8495";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to database", e);
        }
    }
}

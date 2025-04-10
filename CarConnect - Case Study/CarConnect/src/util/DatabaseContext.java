package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseContext {
    private static final String URL = "jdbc:mysql://localhost:3306/CarConnect";
    private static final String USER = "root";
    private static final String PASSWORD = "Nikhil@8495"; // Replace with your actual password

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver (optional since JDBC 4.0, but good practice)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

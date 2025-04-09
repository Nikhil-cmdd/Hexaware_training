package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {

    public static Connection getDBConn() {
        Connection conn = null;
        try {
            Properties props = DBPropertyUtil.getPropertyString("db.properties");
            Class.forName(props.getProperty("db.driver"));
            conn = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.username"),
                    props.getProperty("db.password")
            );
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }
}

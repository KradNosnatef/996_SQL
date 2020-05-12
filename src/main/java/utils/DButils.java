package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DButils {
    public static Connection getConnection() {
        String dbUserName = "admin";
        String dbUserPasswd = "admins_password";
        String dbURL = "jdbc:mysql://localhost:3306/mydb?"
                + "user=" + dbUserName + "&password=" + dbUserPasswd + "&useUnicode=true&characterEncoding=UTF8";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, dbUserName, dbUserPasswd);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
    }
}

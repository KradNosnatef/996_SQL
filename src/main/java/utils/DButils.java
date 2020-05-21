package utils;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.*;

public class DButils {
    public static Connection getConnectionUnitTest() {
        String dbUserName = "admin";
        String dbUserPasswd = "admins_password";
        String dbURL = "jdbc:mysql://localhost:3306/mydb?"
                + "user=" + dbUserName + "&password=" + dbUserPasswd + "&useSSL=false&useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC";
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

    public static Connection getConnection() {
        Connection conn = null;
        Context initContext;
        try {
            initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            DataSource ds = (DataSource) envContext.lookup("jdbc/mydb");

            conn = ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
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

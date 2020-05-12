package utils;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.*;

public class DButils {
    public static Connection getConnection() {
        String dbUserName = "admin";
        String dbUserPasswd = "admins_password";
        String dbURL = "jdbc:mysql://localhost:3306/mydb?"
                + "user=" + dbUserName + "&password=" + dbUserPasswd + "&useUnicode=true&characterEncoding=UTF8";
        Connection conn = null;
        Context initContext = null;
        try {
            initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            //            if (envContext == null) {
            //                System.out.println("Conn error");
            //                return null;
            //            }
            DataSource ds = (DataSource) envContext.lookup("jdbc/mydb");
            //            if (ds == null) {
            //                System.out.println("ds error");
            //                return null;
            //            }
            //            System.out.println("corrent env & ds");
            conn = ds.getConnection();
        } catch (NamingException | SQLException e) {
            //            System.out.println("EOFH");
            e.printStackTrace();
        }

        //        try {
        //            // The newInstance() call is a work around for some
        //            // broken Java implementations
        //
        //            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        //        } catch (Exception ex) {
        //            // handle the error
        //            System.out.println("jdbc: " + ex.getMessage());
        //        }
        //        try {
        //            conn = DriverManager.getConnection(dbURL, dbUserName, dbUserPasswd);
        //        } catch (SQLException ex) {
        //            // handle any errors
        //            System.out.println("SQLException: " + ex.getMessage());
        //            System.out.println("SQLState: " + ex.getSQLState());
        //            System.out.println("VendorError: " + ex.getErrorCode());
        //        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        //        try {
        //            // The newInstance() call is a work around for some
        //            // broken Java implementations
        //
        //            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        //        } catch (Exception ex) {
        //            // handle the error
        //            System.out.println("jdbc: " + ex.getMessage());
        //        }
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

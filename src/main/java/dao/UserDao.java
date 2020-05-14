package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DButils;

// Data Access Object for User-Class

// Here is an instruction for coding:
// the scheme of using database is like this:
// function {
//     conn = DButils.getConnection();
//     String sql = "xxx"
//     try {
//         PreparedStatement ps = conn.prepareStatement(sql);
//         ...
//         ps.close();
//     } (SQLException e) {
//         e.printStackTrace();
//     } finally {
//         DButils.closeConnection(conn);
//     }
// Whenever your are making a unit test with Junit,
// **YOU NEED USE ```conn = DButils.getConnectionUnitTest();``` TO CONNECT DATABASE WHEN YOU ARE DOING UNIT TESTS.**

public class UserDao {
    public boolean hasUser(String username) {
        Connection conn = DButils.getConnection();
        String sql = "select * from User where username = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {return true;}
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return false;
    }

    public User loginUnitTest(String username, String password) {
        Connection conn = DButils.getConnectionUnitTest();
        User user = null;
        String sql = "select * from User where username = ? and password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.set_id(rs.getInt("id"));
                user.set_username(rs.getString("username"));
                user.set_password(rs.getString("password"));
                user.set_usertype(rs.getInt("usertype"));
                user.set_foreignid(rs.getString("foreign_id"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return user;
    }

    public User login(String username, String password) {
        Connection conn = DButils.getConnection();
        User user = null;
        String sql = "select * from User where username = ? and password = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.set_id(rs.getInt("id"));
                user.set_username(rs.getString("username"));
                user.set_password(rs.getString("password"));
                user.set_usertype(rs.getInt("usertype"));
                user.set_foreignid(rs.getString("foreign_id"));
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return user;
    }
}

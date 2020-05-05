package dao;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DButils;

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

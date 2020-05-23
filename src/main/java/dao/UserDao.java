package dao;

import model.Campus;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DButils;
import utils.UnitTestSwitch;

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

    public ArrayList<User> queryUser() {
        String sql = "SELECT * from User ORDER BY id;";

        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();

        ArrayList<User> user_list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.set_id(rs.getInt("id"));
                user.set_username(rs.getString("username"));
                user.set_password(rs.getString("password"));
                user.set_usertype(rs.getInt("usertype"));
                user.set_foreignid(rs.getString("foreign_id"));
                user_list.add(user);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }

        return user_list;
    }

    public int insertUser(String username, String password, int usertype, String foreign_id) {
        String sql = "INSERT INTO User (username, password, usertype, foreign_id) VALUES (?,?,?,?);";

        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();

        int insert_flag = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, usertype);
            ps.setString(4, foreign_id);
            insert_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return insert_flag;
    }

    public int deleteUser(String username) {
        String sql = "DELETE FROM User WHERE username = ?;";

        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();

        int delete_flag = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            delete_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return delete_flag;
    }

    public int updateUser (String username, String password, String usertype_string, String foreign_id) {
        int update_flag = 0;

        if (password != null) {
            String sql = "UPDATE User SET password = ? WHERE username = ?;";
            Connection conn;
            if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
            else conn = DButils.getConnection();

            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, password);
                ps.setString(2, username);
                update_flag += ps.executeUpdate();
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } finally {
                DButils.closeConnection(conn);
            }
        }

        if (usertype_string != null) {
            int usertype = Integer.parseInt(usertype_string);
            String sql = "UPDATE User SET usertype = ? WHERE username = ?;";
            Connection conn;
            if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
            else conn = DButils.getConnection();

            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, usertype);
                ps.setString(2, username);
                update_flag += ps.executeUpdate();
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } finally {
                DButils.closeConnection(conn);
            }
        }

        if (foreign_id != null) {
            String sql = "UPDATE User SET foreign_id = ? WHERE username = ?;";
            Connection conn;
            if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
            else conn = DButils.getConnection();

            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, foreign_id);
                ps.setString(2, username);
                update_flag += ps.executeUpdate();
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } finally {
                DButils.closeConnection(conn);
            }
        }

        if (update_flag > 0)
            return 1;
        else
            return 0;
    }
}

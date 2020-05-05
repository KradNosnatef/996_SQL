package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.DButils;
import model.Campus;

public class CampusDao {
    // Add
    public int insert_campus(String id, String name, String address) {
        Connection conn = DButils.getConnection();
        String sql = "insert into campus (Campus_ID, Campus_Name, Campus_Address) VALUES (?,?,?)";
        int insert_flag = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, address);
            insert_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return insert_flag;
    }

    // Delete
    public int delete_campus(String id) {
        return 1;
    }
    // Query
    public int query_campus(String id) {
        return 1;
    }

    // Modify
    public int modify_campus(String id) {
        return 1;
    }
}

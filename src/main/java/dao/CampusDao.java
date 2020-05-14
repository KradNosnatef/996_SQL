package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DButils;
import model.Campus;

// ACCESS SETTINGS:
// ACCESS SETTING FORMATINON P(XYZ)
// X = 1 can be accessed by admin, 0 cannot be accessed by admin
// Y = 1 can be accessed by teacher, 0 cannot be accessed by teacher
// Z = 1 can be accessed by student, 0 cannot be accessed by student

// - insert: P100
// - delete: P100
// - query : P111
// - update: P100

public class CampusDao {
    // Insert new campus element
    // Input:
    // - id: campus id
    // - name: campus name
    // - address: campus address
    // Ouput:
    // - If insertion action succeed, return a number, which stands for the row count for SQL Data Manipulation
    // Language (DML) statements. Otherwise, it will return 0
    public int insertCampus(String id, String name, String address) {
        Connection conn = DButils.getConnection();
        String sql = "INSERT INTO campus (Campus_ID, Campus_Name, Campus_Address) VALUES (?,?,?);";
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

    // A unit test version of data insertion
    // DELAY DELETE LATER
    public int insertCampusUnitTest(String id, String name, String address) {
        Connection conn = DButils.getConnectionUnitTest();
        String sql = "INSERT INTO campus (Campus_ID, Campus_Name, Campus_Address) VALUES (?,?,?);";
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

    // Delete a campus by campus
    // Input:
    // - element_selector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 1 -- delete by id
    //   type = 2 -- delete by name
    //   type = 3 -- delete by address (ILLEGAL)
    // Ouput:
    // - return 0 if the element is not existed
    // - return 1 if we delete the element successfully
    // - return -1 if our instruction is illegal
    public int deleteCampus(String element_selector, int type) {
        Connection conn = DButils.getConnection();
        String sql;
        int delete_flag = 0;
        if (type == 1) {
            sql = "DELETE FROM CAMPUS WHERE CAMPUS_ID = ?;";
        } else if (type == 2) {
            sql = "DELETE FROM CAMPUS WHERE CAMPUS_NAME = ?;";
        } else {
            return -1;
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, element_selector);
            delete_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return delete_flag;
    }

    // Query campus information
    // Input:
    // - element_selector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 0 -- select by id
    //   type = 1 -- select by name
    //   type = 2 -- select by address
    //   type = -1 -- selecat all elements
    // Ouput:
    // - return 0 if element is not existed
    // - return 1 if query action succeed
    // - return -1 if type is illegal
    public int queryCampus(String element_selector, int type) {
        Connection conn = DButils.getConnection();
        String sql;
        int query_flag = 0;
        if (type == -1) {
            sql = "SELECT * FROM CAMPUS ORDERED BY CAMPUS_ID;";
        } else if (type == 0) {
            sql = "SELECT * FROM CAMPUS WHERE CAMPUS_ID = ? ORDERED BY CAMPUS_ID;";
        } else if (type == 1) {
            sql = "SELECT * FROM CAMPUS WHERE CAMPUS_NAME = ? ORDERED BY CAMPUS_NAME;";
        } else if (type == 2) {
            sql = "SELECT * FROM CAMPUS WHERE CAMPUS_ADDRESS = ? ORDERED BY CAMPUS_ADDRESS;";
        } else {
            return -1;
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, element_selector);
            query_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return query_flag;
    }

    // Modify campus information
    // - old & new type: type is used to select the meaning of old_value & new_value
    //   type = 0 -- update id
    //   type = 1 -- update name
    //   type = 2 -- update address
    // - old & new value:
    //   old value is used to select which element to update
    //   new value is used to update the database
    // Ouput:
    // - return 0 if element is not existed
    // - return 1 if update action succeed
    // - return -1 if type is illegal
    public int modifyCampus(int old_type, int new_type, String old_value, String new_value) {
        Connection conn = DButils.getConnection();
        String sql;
        String sql_select_old;
        String sql_update_new;
        int query_flag = 0;

        if (old_type == 0) {
            sql_select_old = " WHERE CAMPUS_ID = ?;";
        } else if (old_type == 1) {
            sql_select_old = " WHERE CAMPUS_NAME = ?;";
        } else if (old_type == 2) {
            sql_select_old = " WHERE CAMPUS_ADDRESS = ?;";
        } else {
            return -1;
        }

        if (new_type == 0) {
            sql_update_new = "SET CAMPUS_ID = ?";
        } else if (new_type == 1) {
            sql_update_new = "SET CAMPUS_NAME = ?";
        } else if (new_type == 2) {
            sql_update_new = "SET CAMPUS_ADDRESS = ?";
        } else {
            return -1;
        }

        sql = "UPDATE CAMPUS " + sql_update_new + sql_select_old;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, new_value);
            ps.setString(2, old_value);
            query_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return query_flag;
    }
}

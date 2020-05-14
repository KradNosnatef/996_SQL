package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DButils;
import utils.UnitTestSwitch;
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
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql = "INSERT INTO Campus (Campus_ID, Campus_Name, Campus_Address) VALUES (?,?,?);";
        int insert_flag = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, address);
            insert_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return insert_flag;
    }

    // Delete a campus
    // Input:
    // - element_selector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 0 -- delete by id
    //   type = 1 -- delete by name
    //   type = 2 -- delete by address (ILLEGAL)
    // Ouput:
    // - return 0 if the element is not existed
    // - return 1 if we delete the element successfully
    // - return -1 if our instruction is illegal
    public int deleteCampus(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql = "DELETE FROM Campus ";
        String department_check = "SELECT * FROM Department, Campus ";
        String campus_selector;
        String campus_foreign_selector;
        int delete_flag = 0;
        if (type == 0) {
            campus_foreign_selector = "WHERE Campus.Campus_ID = ? AND Department.Department_Campus_ID = Campus" +
                                              ".Campus_ID;";
            campus_selector = "WHERE Campus_ID = ?;";
        } else if (type == 1) {
            campus_foreign_selector = "WHERE Campus.Campus_Name = ? AND Department.Department_Campus_ID = Campus" +
                                              ".Campus_ID;";
            campus_selector = "WHERE Campus_Name = ?;";
        } else {
            return -1;
        }

        // Check whether there are departments in this campus
        try {
            department_check = department_check + campus_foreign_selector;
            PreparedStatement ps = conn.prepareStatement(department_check);
            ps.setString(1, element_selector);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            if (!UnitTestSwitch.SWITCH) {
                sqle.printStackTrace();
            }
        }

        try {
            sql = sql + campus_selector;
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
    // - return an array list for your query.
    public ArrayList<Campus> queryCampus(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql;
        ArrayList<Campus> campus_list = new ArrayList<>();
        int query_flag = 0;
        if (type == -1) {
            sql = "SELECT * FROM Campus ORDER BY Campus_ID;";
        } else if (type == 0) {
            sql = "SELECT * FROM Campus WHERE Campus_ID = ? ORDER BY Campus_ID;";
        } else if (type == 1) {
            sql = "SELECT * FROM Campus WHERE Campus_Name = ? ORDER BY Campus_ID;";
        } else if (type == 2) {
            sql = "SELECT * FROM Campus WHERE Campus_Address = ? ORDER BY Campus_ID;";
        } else {
            return campus_list;
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (type != -1)
                ps.setString(1, element_selector);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Campus campus_element = new Campus();
                campus_element.set_id(rs.getString("Campus_ID"));
                campus_element.set_name(rs.getString("Campus_Name"));
                campus_element.set_address(rs.getString("Campus_Address"));
                campus_list.add(campus_element);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return campus_list;
    }

    // Modify campus information
    // - old & new type: type is used to select the meaning of old_value & new_value
    //   type = 0 -- update id (if new_type == 0, ILLEGAL)
    //   type = 1 -- update name
    //   type = 2 -- update address
    // - old & new value:
    //   old value is used to select which element to update
    //   new value is used to update the database
    // Ouput:
    // - return 0 if element is not existed
    // - return 1 if update action succeed
    // - return -1 if type is illegal
    public int updateCampus(int old_type, int new_type, String old_value, String new_value) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql;
        String sql_select_old;
        String sql_update_new;
        int query_flag = 0;

        if (old_type == 0) {
            sql_select_old = " WHERE Campus_ID = ?;";
        } else if (old_type == 1) {
            sql_select_old = " WHERE Campus_Name = ?;";
        } else if (old_type == 2) {
            sql_select_old = " WHERE Campus_Address = ?;";
        } else {
            return -1;
        }

        if (new_type == 0) {
            // sql_update_new = "SET Campus_ID = ?";
            return -1;
        } else if (new_type == 1) {
            sql_update_new = "SET Campus_Name = ?";
        } else if (new_type == 2) {
            sql_update_new = "SET Campus_Address = ?";
        } else {
            return -1;
        }

        sql = "UPDATE Campus " + sql_update_new + sql_select_old;
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

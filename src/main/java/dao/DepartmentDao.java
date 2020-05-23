package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Campus;
import utils.DButils;
import utils.UnitTestSwitch;
import model.Department;

// ACCESS SETTINGS:
// - insert: P100
// - delete: P100
// - query : P111
// - update: P100

public class DepartmentDao {
    // Insert new department element
    // Input:
    // - id: department id
    // - name: department name
    // - address: department address
    // - dean: department dean
    // - campus_id: foreiggn key from Campus.Campus_ID
    // Ouput:
    // - If insertion action succeed, return 1
    // - If insertion action is illegal, return -1
    // - If insertion action failed, return 0
    public int insertDepartment(String id, String name, String address, String dean, String campus_id) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String campus_check = "SELECT * FROM Campus WHERE Campus_ID = ?;";
        String sql = "INSERT INTO Department (Department_ID, Department_Name, Department_Address, Department_Dean, " +
                             "Department_Campus_ID) VALUES (?,?,?,?,?);";
        int insert_flag = 0;

        // Check if campus_id existed
        try {
            PreparedStatement ps = conn.prepareStatement(campus_check);
            ps.setString(1, campus_id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, dean);
            ps.setString(5, campus_id);
            insert_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return insert_flag;
    }

    // Delete a department
    // Input:
    // - element_selector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 0 -- delete by id
    //   type = 1 -- delete by name
    //   type = 2 -- delete by address (ILLEGAL)
    //   type = 3 -- delete by dean (ILLEGAL)
    //   type = 4 -- delete by campus_id
    // Ouput:
    // - return 0 if the element is not existed
    // - return 1 if we delete the element successfully
    // - return -1 if our instruction is illegal
    public int deleteDepartment(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql = "DELETE FROM Department ";
        String teacher_check = "SELECT * FROM Department, Teacher ";
        String course_check = "SELECT * FROM Department, Course ";
        String class_check = "SELECT * FROM Department, Class";

        String department_selector;
        String department_foreign_selector_teacher;
        String department_foreign_selector_course;
        String department_foreign_selector_class;

        int delete_flag = 0;

        if (type == 0) {
            department_foreign_selector_teacher = "WHERE Department.Department_ID = ? " +
                                                      "AND Department.Department_ID = Teacher.Teacher_Department_ID;";
            department_foreign_selector_course = "WHERE Department.Department_ID = ? " +
                                                     "AND Department.Department_ID = Course.Course_Department_ID;";
            department_foreign_selector_class = "WHERE Department.Department_ID = ? " +
                                                    "AND Department.Department_ID = Class.Class_Department_ID;";
            department_selector = "WHERE Department_ID = ?;";
        } else if (type == 1) {
            department_foreign_selector_teacher = "WHERE Department.Department_Name = ? " +
                                                      "AND Department.Department_ID = Teacher.Teacher_Department_ID;";
            department_foreign_selector_course = "WHERE Department.Department_Name = ? " +
                                                     "AND Department.Department_ID = Course.Course_Department_ID;";
            department_foreign_selector_class = "WHERE Department.Department_Name = ? " +
                                                    "AND Department.Department_ID = Class.Class_Department_ID;";
            department_selector = "WHERE Department_Name = ?;";
        } else if (type == 4) {
            department_foreign_selector_teacher = "WHERE Department.Department_Campus_ID = ? " +
                                                      "AND Department.Department_ID = Teacher.Teacher_Department_ID;";
            department_foreign_selector_course = "WHERE Department.Department_Campus_ID = ? " +
                                                     "AND Department.Department_ID = Course.Course_Department_ID;";
            department_foreign_selector_class = "WHERE Department.Department_Campus_ID = ? " +
                                                    "AND Department.Department_ID = Class.Class_Department_ID;";
            department_selector = "WHERE Department_Campus_ID = ?;";
        } else {
            return -1;
        }

        // Check whether there are teachers in this department
        try {
            teacher_check = teacher_check + department_foreign_selector_teacher;
            PreparedStatement ps = conn.prepareStatement(teacher_check);
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

        // Check whether there are courses opened by this department
        try {
            course_check = course_check + department_foreign_selector_course;
            PreparedStatement ps = conn.prepareStatement(course_check);
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

        // Check whether there are classes in this department
        try {
            class_check = class_check + department_foreign_selector_class;
            PreparedStatement ps = conn.prepareStatement(class_check);
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
            sql = sql + department_selector;
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

    // Query department information
    // Input:
    // - element_selector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 0 -- select by id
    //   type = 1 -- select by name
    //   type = 2 -- select by address
    //   type = 3 -- select by dean
    //   type = 4 -- select by campus_id
    //   type = -1 -- selecat all elements
    // Ouput:
    // - return an array list for your query.

    public ArrayList<Department> queryDepartment(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql;
        ArrayList<Department> department_list = new ArrayList<>();
        int query_flag = 0;
        switch (type) {
            case 0:
                sql = "SELECT * FROM Department WHERE Department_ID = ? ORDER BY Department_ID;";
                break;
            case 1:
                sql = "SELECT * FROM Department WHERE Department_Name = ? ORDER BY Department_ID;";
                break;
            case 2:
                sql = "SELECT * FROM Department WHERE Department_Address = ? ORDER BY Department_ID;";
                break;
            case 3:
                sql = "SELECT * FROM Department WHERE Department_Dean = ? ORDER BY Department_ID;";
                break;
            case 4:
                sql = "SELECT * FROM Department WHERE Department_Campus_ID = ? ORDER BY Department_ID;";
                break;

            default:
                sql = "SELECT * FROM Department ORDER BY Department_ID;";
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (type != -1)
                ps.setString(1, element_selector);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Department department_element = new Department();
                department_element.set_id(rs.getString("Department_ID"));
                department_element.set_name(rs.getString("Department_Name"));
                department_element.set_address(rs.getString("Department_Address"));
                department_element.set_dean(rs.getString("Department_Dean"));
                department_element.set_campus_id(rs.getString("Department_Campus_ID"));
                department_list.add(department_element);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return department_list;
    }

    // Modify department information
    // - old & new type: type is used to select the meaning of old_value & new_value
    //   type = 0 -- update id (if new_type == 0m ILLEGAL)
    //   type = 1 -- update name
    //   type = 2 -- update address
    //   type = 3 -- update dean
    //   type = 4 -- update campus_id (check if existed before update)
    // Ouput:
    // - return 0 if element is not existed
    // - return 1 if update action succeed
    // - return -1 if type is illegal
    public int updateDepartment(int old_type, int new_type, String old_value, String new_value) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql;
        String sql_select_old;
        String sql_update_new;
        int update_flag = 0;

        switch (old_type) {
            case 0:
                sql_select_old = " WHERE Department_ID = ?;";
                break;
            case 1:
                sql_select_old = " WHERE Department_Name = ?;";
                break;
            case 2:
                sql_select_old = " WHERE Department_Address = ?;";
                break;
            case 3:
                sql_select_old = " WHERE Department_Dean = ?;";
                break;
            case 4:
                sql_select_old = " WHERE Department_Campus_ID = ?;";
                break;

            default:
                return -1;
        }

        switch (new_type) {
            case 1:
                sql_update_new = "UPDATE Department SET Department_Name = ?";
                break;
            case 2:
                sql_update_new = "UPDATE Department SET Department_Address = ?";
                break;
            case 3:
                sql_update_new = "UPDATE Department SET Department_Dean = ?";
                break;
            case 4:
                sql_update_new = "UPDATE Department SET Department_Campus_ID = ?";
                break;

            default:
                return -1;
        }

        // Check if new campus_id exists
        if (new_type == 4) {
            String campus_foreign_selector = "SELECT * FROM Campus WHERE Campus_ID = ?;";
            try {
                PreparedStatement ps = conn.prepareStatement(campus_foreign_selector);
                ps.setString(1, new_value);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
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
        }

        sql = sql_update_new + sql_select_old;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, new_value);
            ps.setString(2, old_value);
            update_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return update_flag;
    }
}

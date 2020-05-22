package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.DButils;
import utils.UnitTestSwitch;
import model.Class;

// TODO: GONNA TEST

// ACCESS SETTINGS:
// - insert: P100
// - delete: P100
// - query : P111
// - update: P100

public class ClassDao {
    // Note: You need to make sure the department_id and the head_teacher_id of the new class element exists
    // Note: If you want to delete a class, you need to make sure the class does not have student or transaction.
    // Return value: 1: succeed; -1: illegal; 0: fail(not exist);
    // Type: 0: by id; 1: by name; 2: by date; 3: by grade; 4:by department; 5: by teacher;
    // You can only delete by id or name;

    public int insertClass(String id, String name, String date, String grade, String department_id, String head_teacher_id) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) {
            conn = DButils.getConnectionUnitTest();
        } else {
            conn = DButils.getConnection();
        }
        //Check if department_id exists
        String department_check = "SELECT * FROM Department WHERE Department_ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(department_check);
            ps.setString(1, department_id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return -1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }
        //Check if head_teacher_id exists
        String head_teacher_check = "SELECT * FROM Teacher_Identity WHERE Teacher_Teacher_ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(head_teacher_check);
            ps.setString(1, head_teacher_id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return -1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }
        //Insert the element
        String sql = "INSERT INTO Class (Class_ID, Class_Name, Class_Date, Class_Grade, Class_Department_ID," +
                " Class_Head_Teacher_ID) VALUES (?,?,?,?,?,?);";
        int insert_flag = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, date);
            ps.setString(4, grade);
            ps.setString(5, department_id);
            ps.setString(6, head_teacher_id);
            insert_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return insert_flag;
    }

    public int deleteClass(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) {
            conn = DButils.getConnectionUnitTest();
        } else {
            conn = DButils.getConnection();
        }

        String student_check = "SELECT * FROM Student, Class ";
        String transaction_check = "SELECT * FROM Transaction, Class ";
        String class_selector, class_foreign_selector_student;
        String class_foreign_selector_transaction1, class_foreign_selector_transaction2;
        if (type == 0) {
            class_selector = "WHERE Class_ID = ?;";
            class_foreign_selector_student = "WHERE Class.Class_ID = ? AND Student.Student_Class_ID = Class.Class_ID;";
            class_foreign_selector_transaction1 = "WHERE Class.Class_ID = ?"
                + " AND Transaction.Transaction_Origin_Class_ID = Class.Class_ID;";
            class_foreign_selector_transaction2 = "WHERE Class.Class_ID = ?"
                    + " AND Transaction.Transaction_Current_Class_ID = Class.Class_ID;";
        } else if (type == 1) {
            class_selector = "WHERE Class_Name = ?;";
            class_foreign_selector_student = "WHERE Class.Class_Name = ? AND Student.Student_Class_ID = Class.Class_ID;";
            class_foreign_selector_transaction1 = "WHERE Class.Class_Name = ?"
                    + " AND Transaction.Transaction_Origin_Class_ID = Class.Class_ID;";
            class_foreign_selector_transaction2 = "WHERE Class.Class_Name = ?"
                    + " AND Transaction.Transaction_Current_Class_ID = Class.Class_ID;";
        } else {
            return -1;
        }
        //Check if the class has students
        try {
            student_check = student_check + class_foreign_selector_student;
            PreparedStatement ps = conn.prepareStatement(student_check);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return -1;
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            if (!UnitTestSwitch.SWITCH) {
                sqle.printStackTrace();
            }
        }
        //Check if the class has transactions
        String transaction_check1, transaction_check2;
        try {
            transaction_check1 = transaction_check + class_foreign_selector_transaction1;
            PreparedStatement ps = conn.prepareStatement(transaction_check1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
            transaction_check2 = transaction_check + class_foreign_selector_transaction2;
            PreparedStatement ps = conn.prepareStatement(transaction_check2);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return -1;
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            if (!UnitTestSwitch.SWITCH) {
                sqle.printStackTrace();
            }
        }
        //Delete the element
        String sql = "DELETE FROM Class ";
        int delete_flag = 0;
        try {
            sql = sql + class_selector;
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

    public List<Class> queryClass(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) {
            conn = DButils.getConnectionUnitTest();
        } else {
            conn = DButils.getConnection();
        }
        String sql;
        List<Class> class_list = new ArrayList<>();
        int query_flag = 0;
        switch (type) {
            case 0:
                sql = "SELECT * FROM Class WHERE Class_ID = ? ORDER BY Class_ID;";
                break;
            case 1:
                sql = "SELECT * FROM Class WHERE Class_Name = ? ORDER BY Class_ID;";
                break;
            case 2:
                sql = "SELECT * FROM Class WHERE Class_Date = ? ORDER BY Class_ID;";
                break;
            case 3:
                sql = "SELECT * FROM Class WHERE Class_Grade = ? ORDER BY Class_ID;";
                break;
            case 4:
                sql = "SELECT * FROM Class WHERE Class_Department_ID = ? ORDER BY Class_ID;";
                break;
            case 5:
                sql = "SELECT * FROM Class WHERE Class_Head_Teacher_ID = ? ORDER BY Class_ID;";
                break;
            default:
                sql = "SELECT * from Class ORDER BY Class_ID;";
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (type != -1) {
                ps.setString(1, element_selector);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Class new_element = new Class();
                new_element.set_id(rs.getString("Class_ID"));
                new_element.set_name(rs.getString("Class_Name"));
                new_element.set_date(rs.getString("Class_Date"));
                new_element.set_grade(rs.getString("Class_Grade"));
                new_element.set_department_id(rs.getString("Class_Department_ID"));
                new_element.set_head_teacher_id(rs.getString("Class_Head_Teacher_ID"));
                class_list.add(new_element);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return class_list;
    }

    public int updateClass(int old_type, int new_type, String old_value, String new_value) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) {
            conn = DButils.getConnectionUnitTest();
        } else {
            conn = DButils.getConnection();
        }
        String sql, sql_select_old, sql_update_new;
        int query_flag = 0;

        switch (old_type) {
            case 0:
                sql_select_old = " WHERE Class_ID = ?;";
                break;
            case 1:
                sql_select_old = " WHERE Class_Name = ?;";
                break;
            case 2:
                sql_select_old = " WHERE Class_Date = ?;";
                break;
            case 3:
                sql_select_old = " WHERE Class_Grade = ?;";
                break;
            case 4:
                sql_select_old = " WHERE Class_Department_ID = ?;";
                break;
            case 5:
                sql_select_old = " WHERE Class_Head_Teacher_ID = ?;";
                break;
            default:
                return -1;
        }
        switch (new_type) {
            case 1:
                sql_update_new = "UPDATE Class SET Class_Name = ?";
                break;
            case 2:
                sql_update_new = "UPDATE Class SET Class_Date = ?";
                break;
            case 3:
                sql_update_new = "UPDATE Class SET Class_Grade = ?";
                break;
            case 4:
                sql_update_new = "UPDATE Class SET Class_Department_ID = ?";
                break;
            case 5:
                sql_update_new = "UPDATE Class SET Class_Head_Teacher_ID = ?";
                break;
            default:
                return -1;
        }
        if (new_type == 4) {
            String department_foreign_selector = "SELECT * FROM Department WHERE Department_ID = ?;";
            try {
                PreparedStatement ps = conn.prepareStatement(department_foreign_selector);
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
        } else if (new_type == 5) {
            String teacher_foreign_selector = "SELECT * FROM Teacher WHERE Teacher_ID = ?;";
            try {
                PreparedStatement ps = conn.prepareStatement(teacher_foreign_selector);
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

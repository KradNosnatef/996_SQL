package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.DButils;
import utils.UnitTestSwitch;
import model.Teacher;
import model.Person;

// TODO: GONNA TEST
// ACCESS SETTINGS:
// - insert: P100
// - delete: P100
// - query : P111
// - update: P100

public class TeacherDao {
    // Note: You need to make sure the department_id of the new teacher element exists
    // Note: If you want to delete a teacher, you need to make sure the teacher does not have class or course.
    // Return value: 1: succeed; -1: illegal; 0: fail(not exist);
    // Type: 0: by Teacher_id; 1: by ID_card_number;

    // To 黄业琦：使用两个flag感觉怪怪的
    public int insertTeacher(String teacher_id, String date, String department_id, String title, String id_number,
                             Boolean id_type, String name, Boolean gender, String birth, String nationality, String address,
                             String address_id, String address_phone_number) {
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
        //insert the element
            //To the teacher table
        String sql_teacher = "INSERT INTO Teacher (Teacher_ID, Teacher_Enroll_Date, Teacher_Department_ID, Teacher_Title)"
                        + " VALUES (?,?,?,?);";
        int insert_flag_teacher = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql_teacher);
            ps.setString(1, teacher_id);
            ps.setString(2, date);
            ps.setString(3, department_id);
            ps.setString(4, title);
            insert_flag_teacher = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
            //To the person table
        String sql_person = "INSERT INTO Person (ID_Card_Number, ID_Card_Type, Name, Gender, Birth, Nationality, Address,"
                            + " Address_ID, Address_Phone_Number) VALUES (?,?,?,?,?,?,?,?,?);";
        int insert_flag_person = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql_person);
            ps.setString(1, id_number);
            ps.setBoolean(2, id_type);
            ps.setString(3, name);
            ps.setBoolean(4, gender);
            ps.setString(5, birth);
            ps.setString(6, nationality);
            ps.setString(7, address);
            ps.setString(8, address_id);
            ps.setString(9, address_phone_number);
            insert_flag_person = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return insert_flag_person & insert_flag_teacher;
    }
    public int deleteTeacher(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) {
            conn = DButils.getConnectionUnitTest();
        } else {
            conn = DButils.getConnection();
        }
        String class_check = "SELECT * FROM Class ";
        String course_check = "SELECT * FROM Course ";
        String teacher_foreign_selector_class = "WHERE Class_Head_Teacher_ID = ?;";
        String teacher_foreign_selector_course = "WHERE Course_Teacher_ID = ?;";
        String teacher_id, person_id;
        //select teacher_id and person_id
        if (type == 0) {
            teacher_id = element_selector;
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Teacher WHERE Teacher_ID = ?;");
                ps.setString(1, teacher_id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    person_id = rs.getString("Person_ID_Card_Number");
                } else {
                    return -1;
                }
                rs.close();
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                return -1;
            }
        } else if (type == 1) {
            person_id = element_selector;
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Teacher WHERE Person_ID_Card_Number = ?;");
                ps.setString(1, person_id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    teacher_id = rs.getString("Teacher_ID");
                } else {
                    return -1;
                }
                rs.close();
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                return -1;
            }
        } else {
            return -1;
        }
        //Check if the teacher has class
        try {
            class_check = class_check + teacher_foreign_selector_class;
            PreparedStatement ps = conn.prepareStatement(class_check);
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
        //Check if the teacher has course
        try {
            course_check = course_check + teacher_foreign_selector_course;
            PreparedStatement ps = conn.prepareStatement(course_check);
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
            //Delete teacher
            String sql_teacher = "DELETE FROM Teacher WHERE Teacher_ID = ?;";
            int delete_flag_teacher = 0;
            try {
                PreparedStatement ps = conn.prepareStatement(sql_teacher);
                ps.setString(1, teacher_id);
                delete_flag_teacher = ps.executeUpdate();
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            //Delete person
            String sql_person = "DELETE FROM Person WHERE ID_Card_Number = ?;";
            int delete_flag_person = 0;
            try {
                PreparedStatement ps = conn.prepareStatement(sql_person);
                ps.setString(1, person_id);
                delete_flag_person = ps.executeUpdate();
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } finally {
                DButils.closeConnection(conn);
            }
            return delete_flag_person & delete_flag_teacher;
    }
    //
    public List<Teacher> queryTeacher(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) {
            conn = DButils.getConnectionUnitTest();
        } else {
            conn = DButils.getConnection();
        }
        String sql;
        if (type == 0) {
            sql = "SELECT * FROM Teacher WHERE Teacher_ID = ?;";
        } else if (type == 1) {
            sql = "SELECT * FROM Teacher WHERE Person_ID_Card_Number = ?;";
        } else {
            sql = "SELECT * FROM Teacher;";
        }
        List<Teacher> teacher_list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (type == 0 || type == 1)
                ps.setString(1, element_selector);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Teacher new_element = new Teacher();
                new_element.set_id("Teacher_ID");
                new_element.set_department_id("Teacher_Department_ID");
                new_element.set_enrollment_date("Teacher_Enroll_Date");
                new_element.set_teacher_title("Teacher_Title");
                teacher_list.add(new_element);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return teacher_list;
    }
    //注意：本函数查询Person表，只能通过Person_ID_Card_Number查询
    public Person queryPerson(String Person_ID_Card_Number) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) {
            conn = DButils.getConnectionUnitTest();
        } else {
            conn = DButils.getConnection();
        }
        Person person_element = new Person();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Person WHERE ID_Card_Number = ?;");
            ps.setString(1, Person_ID_Card_Number);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                person_element.set_id_card_number(rs.getString("ID_Card_Number"));
                person_element.set_card_type(rs.getBoolean("ID_Card_Type"));
                person_element.set_name(rs.getString("Name"));
                person_element.set_gender(rs.getBoolean("Gender"));
                person_element.set_birthdate(rs.getString("Birth"));
                person_element.set_nationnality(rs.getString("Nationality"));
                person_element.set_address(rs.getString("Address"));
                person_element.set_address_postal_code(rs.getString("Address_ID"));
                person_element.set_address_postal_code(rs.getString("Address_Phone_Number"));
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return person_element;
    }
    //Type: 0: Teacher_ID; 1: Teacher_Enroll_Date; 2: Teacher_Department_ID; 3: Teacher_Title.
    public int updateTeacher(int old_type, int new_type, String old_value, String new_value) {
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
                sql_select_old = " WHERE Teacher_ID = ?;";
                break;
            case 1:
                sql_select_old = " WHERE Teacher_Enroll_Date = ?;";
                break;
            case 2:
                sql_select_old = " WHERE Teacher_Department_ID = ?;";
                break;
            case 3:
                sql_select_old = " WHERE Teacher_Title = ?;";
                break;
            default:
                return -1;
        }
        switch (new_type) {
            case 1:
                sql_update_new = "UPDATE Teacher SET Teacher_Enroll_Date = ?;";
                break;
            case 2:
                sql_update_new = "UPDATE Teacher SET Teacher_Department_ID = ?;";
                break;
            case 3:
                sql_update_new = "UPDATE Teacher SET Teacher_Title = ?;";
                break;
            default:
                return -1;
        }
        //Check if new department_id exists
        if (new_type == 2) {
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

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Student;
import utils.DButils;
import utils.Datautils;
import utils.UnitTestSwitch;
import model.Teacher;

public class TeacherDao {
    // Insert a new teacher
    // Input (Teacher Info) AND (Person Info)
    public int insertTeacher(String id, String enrollment_date, String department_id, String teacher_title,
            String id_card_number, boolean card_type, String name, boolean gender, String birthdate, String nationality,
            String address, String address_postal_code, String address_phone_number) {
        Connection conn;
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();

        enrollment_date = Datautils.DateFormat(enrollment_date);
        birthdate = Datautils.DateFormat(birthdate);
        int insert_flag = 0;

        // Check if this person is exsited in the Person table
        String check_person = "SELECT * FROM Person WHERE ID_Card_Number = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(check_person);
            ps.setString(1, id_card_number);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        // Check if this person is exsited in the Student table
        String student_check = "SELECT * FROM Teacher WHERE Teacher_ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(student_check);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        // Check if the department is existed in the Department table
        String department_check = "SELECT * FROM Department WHERE Department_ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(department_check);
            ps.setString(1, department_id);
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
        }

        // Now, let's insert the student
        // Insert the Person first
        String insert_person_sql = "INSERT INTO Person (ID_Card_Number, ID_Card_Type, Name, Gender, Birth, "
                + "Nationality, Address, Address_Phone_Number, Address_Postal_Code) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement ps = conn.prepareStatement(insert_person_sql);
            ps.setString(1, id_card_number);
            ps.setBoolean(2, card_type);
            ps.setString(3, name);
            ps.setBoolean(4, gender);
            ps.setString(5, birthdate);
            ps.setString(6, nationality);
            ps.setString(7, address);
            ps.setString(8, address_postal_code);
            ps.setString(9, address_phone_number);
            insert_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        // Then Insert the Teacher
        String insert_student_sql = "INSERT INTO Teacher (Teacher_ID, Teacher_Enroll_Date, Teacher_Department_ID, "
                + "Teacher_Title, Person_ID_Card_Number) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(insert_student_sql);
            ps.setString(1, id);
            ps.setString(2, enrollment_date);
            ps.setString(3, department_id);
            ps.setString(4, teacher_title);
            ps.setString(5, id_card_number);
            insert_flag &= ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }

        return insert_flag;
    }

    // Delete an existed teacher
    // We support 2 types to delete a teacher
    // type = 0 : delete by teacher_id
    // type = 1 : delete by id_card_number
    public int deleteTeacher(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();
        int delete_flag = 1;
        if (type == 0) {
            String get_id_card_number_sql = "SELECT * FROM Teacher WHERE Teacher_ID = ?;";
            String id_card_number;
            String delete_teacher_sql = "DELETE FROM Teacher WHERE Person_ID_Card_Number=?;";
            String delete_person_sql = "DELETE FROM Person WHERE ID_Card_Number=?;";
            try {
                PreparedStatement ps = conn.prepareStatement(get_id_card_number_sql);
                ps.setString(1, element_selector);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    rs.close();
                    ps.close();
                    return -1;
                }
                id_card_number = rs.getString("Person_ID_Card_Number");
                rs.close();
                ps.close();
                ps = conn.prepareStatement(delete_teacher_sql);
                ps.setString(1, id_card_number);
                delete_flag = ps.executeUpdate();
                ps.close();
                ps = conn.prepareStatement(delete_person_sql);
                ps.setString(1, id_card_number);
                delete_flag &= ps.executeUpdate();
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } finally {
                DButils.closeConnection(conn);
            }
        } else {
            String delete_teacher_sql = "DELETE FROM Teacher WHERE Person_ID_Card_Number=?;";
            String delete_person_sql = "DELETE FROM Person WHERE ID_Card_Number=?;";
            try {
                PreparedStatement ps = conn.prepareStatement(delete_teacher_sql);
                ps.setString(1, element_selector);
                delete_flag = ps.executeUpdate();
                ps = conn.prepareStatement(delete_person_sql);
                ps.setString(1, element_selector);
                delete_flag &= ps.executeUpdate();
                ps.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } finally {
                DButils.closeConnection(conn);
            }
        }
        return delete_flag;
    }

    // Query teacher
    // type = -1 : List out all the teachers
    // type = 0 : use Teacher_ID
    // type = 1 : use ID_card_nubmer
    // type = 2 : use name
    public ArrayList<Teacher> queryTeacher(String element_selector, int type) {
        String sql = "";

        Connection conn;
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();

        ArrayList<Teacher> teacher_list = new ArrayList<>();
        if (type == -1) {
            sql = "SELECT * FROM Teacher ORDER BY Teacher_ID;";
        } else if (type == 0) {
            sql = "SELECT * FROM Teacher WHERE Teacher_ID = ? ORDER BY Teacher_ID;";
        } else if (type == 1) {
            sql = "SELECT * FROM Teacher WHERE Person_ID_Card_Number=? ORDER BY Teacher_ID;";
        } else if (type == 2) {
            sql = "SELECT * FROM Teacher, Person WHERE Teacher.Person_ID_Card_Number=Person.ID_Card_Number AND Person.Name = "
                    + "? ORDER BY Teacher_ID;";
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (type != -1) {
                ps.setString(1, element_selector);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Teacher teacher_element = new Teacher();
                teacher_element.set_id(rs.getString("Teacher_ID"));
                teacher_element.set_enrollment_date(rs.getString("Teacher_Enroll_Date"));
                teacher_element.set_department_id(rs.getString("Teacher_Department_ID"));
                teacher_element.set_teacher_title(rs.getString("Teacher_Title"));
                teacher_element.set_id_card_number(rs.getString("Person_ID_Card_Number"));
                teacher_list.add(teacher_element);
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

    // Update teacher
    // type = 1 : update enroll date
    // type = 2 : update department id
    // type = 3 : update title
    public int updateTeacher(String id, int type, String info) {
        String sql;

        Connection conn;
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();

        int update_flag = 0;

        if (type == 2) {
            // Check if the department is existed in the Department table
            String department_check = "SELECT * FROM Department WHERE Department_ID = ?;";
            try {
                PreparedStatement ps = conn.prepareStatement(department_check);
                ps.setString(1, info);
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
            }
        }

        if (type == 1) {
            sql = "UPDATE Teacher SET Teacher_Enroll_Date = ? WHERE Teacher_ID = ?;";
        } else if (type == 2) {
            sql = "UPDATE Teacher SET Teacher_Department_ID = ? WHERE Teacher_ID = ?;";
        } else if (type == 3) {
            sql = "UPDATE Teacher SET Teacher_Title = ? WHERE Teacher_ID = ?;";
        } else {
            return -1;
        }
        ;

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, info);
            ps.setString(2, id);
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

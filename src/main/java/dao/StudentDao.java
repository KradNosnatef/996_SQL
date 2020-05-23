package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.DButils;
import utils.Datautils;
import utils.UnitTestSwitch;
import model.Student;

public class StudentDao {
    // Insert a new student
    // Input (Student Info) AND (Person Info)
    // TODO INITIAL WITH TRANSACTION NEED ADDITIONAL CHECK
    public int insertStudent(String id, String enrollment_date, String email, String class_id,
                             String id_card_number, boolean card_type, String name, boolean gender, String birthdate,
                             String nationality, String address, String address_postal_code,
                             String address_phone_number) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();

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
        String student_check = "SELECT * FROM Student WHERE Student_ID = ?;";
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

        // Check if the class is existed in the Class table
        String class_check = "SELECT * FROM Class WHERE Class_ID = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(class_check);
            ps.setString(1, class_id);
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
        String insert_person_sql = "INSERT INTO Person (ID_Card_Number, ID_Card_Type, Name, Gender, Birth, " +
                                           "Nationality, Address, Address_Phone_Number, Address_Postal_Code) VALUES " +
                                           "(?, ?, ?, ?, ?, ?, ?, ?, ?);";

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

        // Then Insert the Student
        String insert_student_sql = "INSERT INTO Student (Student_ID, Student_Enroll_Date, Student_Email, " +
                                            "Student_Class_ID, Person_ID_Card_Number) VALUES" +
                                            " (?, ?, ?, ?, ?);";
        try {
            PreparedStatement ps = conn.prepareStatement(insert_student_sql);
            ps.setString(1, id);
            ps.setString(2, enrollment_date);
            ps.setString(3, email);
            ps.setString(4, class_id);
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

    // Delete an existed student
    // We support 2 types to delete a student
    // type = 0 : delete by student_id
    // type = 1 : delete by id_card_number
    public int deleteStudent(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        int delete_flag = 1;
        if (type == 0) {
            String get_id_card_number_sql = "SELECT * FROM Student WHERE Student_ID = ?;";
            String id_card_number;
            String delete_student_sql = "DELETE FROM Student WHERE Person_ID_Card_Number=?;";
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
                ps = conn.prepareStatement(delete_student_sql);
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
            String delete_person_sql = "DELETE FROM Person WHERE ID_Card_Number=?;";
            String delete_student_sql = "DELETE FROM Student WHERE Person_ID_Card_Number=?;";
            try {
                PreparedStatement ps = conn.prepareStatement(delete_student_sql);
                ps.setString(1, element_selector);
                delete_flag = ps.executeUpdate();
                ps.close();
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

    // Query student
    // type = -1 : list out all the students
    // type = 0 : use Student_ID
    // type = 1 : use ID_card_nubmer
    // type = 2 : use name
    public ArrayList<Student> queryStudent(String element_selector, int type) {
        String sql = "";

        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();

        ArrayList<Student> student_list = new ArrayList<>();
        if (type == -1) {
            sql = "SELECT * FROM Student ORDER BY Student_ID;";
        } else if (type == 0) {
            sql = "SELECT * FROM Student WHERE Student_ID = ? ORDER BY Student_ID;";
        } else if (type == 1) {
            sql = "SELECT * FROM Student WHERE Person_ID_Card_Number=? ORDER BY Student_ID;";
        } else if (type == 2) {
            sql = "SELECT * FROM Student, Person WHERE Student.Person_ID_Card_Number=Person.ID_Card_Number AND Person.Name = ? ORDER BY Student_ID;";
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (type != -1) {
                ps.setString(1, element_selector);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student student_element = new Student();
                student_element.set_id(rs.getString("Student_ID"));
                student_element.set_enrollment_date(rs.getString("Student_Enroll_Date"));
                student_element.set_email(rs.getString("Student_Email"));
                student_element.set_class_id(rs.getString("Student_Class_ID"));
                student_element.set_transaction_id(rs.getString("Student_Transaction_ID"));
                student_element.set_id_card_number(rs.getString("Person_ID_Card_Number"));
                student_list.add(student_element);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }

        return student_list;
    }

    // Update student
    // type = 1 : update enroll date
    // type = 2 : update class id
    // type = 3 : update email
    public int updateStudent(String id, int type, String info) {
        String sql;

        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();

        int update_flag = 0;

        if (type == 2) {
            // Check if the class is existed in the Class table
            String class_check = "SELECT * FROM Class WHERE Class_ID = ?;";
            try {
                PreparedStatement ps = conn.prepareStatement(class_check);
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
            sql = "UPDATE Student SET Student_Enroll_Date = ? WHERE Student_ID = ?;";
        } else if (type == 2) {
            sql = "UPDATE Student SET Student_Class_ID = ? WHERE Student_ID = ?;";
        } else if (type == 3) {
            sql = "UPDATE Student SET Student_Email = ? WHERE Student_ID = ?;";
        } else {
            return -1;
        };

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

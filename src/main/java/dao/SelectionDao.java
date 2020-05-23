package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Department;
import utils.DButils;
import utils.Datautils;
import utils.UnitTestSwitch;
import model.Course;
import model.Student;

// ACCESS SETTINGS:
// ACCESS SETTING FORMATINON P(XYZ)
// X = 1 can be accessed by admin, 0 cannot be accessed by admin
// Y = 1 can be accessed by teacher, 0 cannot be accessed by teacher
// Z = 1 can be accessed by student, 0 cannot be accessed by student

// - insert: P101
// - delete: P101
// - query : P111
// - update: P000

public class SelectionDao {
    // Insert a new course selecction information
    // student_id & course_id are foreign keys from other table
    // date is fetch from browser
    // Ouput:
    // - If insertion action succeed, return 1
    // - If insertion action is illegal, return -1
    // - If insertion action failed, return 0
    public int insertSelection(String student_id, String course_id, String date) {
        Connection conn;
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();

        int insert_flag = 0;
        date = Datautils.DateFormat(date);

        String student_check;
        String course_check;
        String duplicating_check;

        student_check = "SELECT * FROM Student WHERE Student_ID = ?;";
        course_check = "SELECT * FROM Course WHERE Course_ID = ?;";
        duplicating_check = "SELECT * FROM CourseSelection WHERE CourseSelection_Student_ID = ? AND CourseSelection_Course_ID = ?;";

        // Check if student_id existed
        try {
            PreparedStatement ps = conn.prepareStatement(student_check);
            ps.setString(1, student_id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return -1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }

        // Check if course_id existed
        try {
            PreparedStatement ps = conn.prepareStatement(course_check);
            ps.setString(1, course_id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return -1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }

        // Check if duplicating selection
        try {
            PreparedStatement ps = conn.prepareStatement(duplicating_check);
            ps.setString(1, course_id);
            ps.setString(2, student_id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // If find the student has already selected this course in another time, return
                // invalid.
                return -1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }

        String sql = "INSERT INTO CourseSelection (CourseSelection_Course_ID, CourseSelection_Student_ID, CourseSelection_Date) VALUES (?,?,?);";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, course_id);
            ps.setString(2, student_id);
            ps.setString(3, date);
            insert_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return insert_flag;
    }

    // Delete Selection
    // We have 3 deletion mode in total
    // type = 0 -- give student_id & course_id to delete a single selection
    // information
    // type = 1 -- give course_id only
    // type = 2 -- give student_id only
    public int deleteSelection(String student_id, String course_id, int type) {
        Connection conn;
        System.out.println("Type = " + type);
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();

        int delete_flag = 0;

        String sql = "DELETE FROM CourseSelection WHERE ";
        if (type == 0)
            sql = sql + "CourseSelection_Student_ID = ? AND CourseSelection_Course_ID = ?;";
        else if (type == 1)
            sql = sql + "CourseSelection_Course_ID = ?;";
        else if (type == 2)
            sql = sql + "CourseSelection_Student_ID = ?;";
        else
            return -1;

        System.out.println("sql = " + sql);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            if (type == 0) {
                ps.setString(1, student_id);
                ps.setString(2, course_id);
            } else if (type == 1)
                ps.setString(1, course_id);
            else
                ps.setString(1, student_id);

            delete_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }

        return delete_flag;
    }

    // Query selection information
    // I am going to give out 2 methods here to query:
    // 1. Use student_id to query all the course he chose.
    // 2. Use course_id to quert all the student who chose this class.
    public ArrayList<Course> queryByStudent(String student_id) {
        Connection conn;
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();
        String sql;
        ArrayList<Course> course_list = new ArrayList<>();
        sql = "SELECT * FROM CourseSelection WHERE CourseSelection_Student_ID = ? ;";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, student_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course_element = new Course();
                course_element.set_id(rs.getString("Course_ID"));
                course_element.set_name(rs.getString("Course_Name"));
                course_element.set_department_id(rs.getString("Course_Department_ID"));
                course_element.set_teacher_id(rs.getString("Course_Teacher_ID"));
                course_element.set_exam_type(rs.getString("Course_Exam_Type"));
                course_element.set_year(rs.getString("Course_Year"));
                course_element.set_semester(rs.getString("Course_Semester"));
                course_element.set_time(rs.getString("Course_Time"));
                course_list.add(course_element);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return course_list;
    }

    public ArrayList<Student> queryByCourse(String course_id) {
        Connection conn;
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();
        String sql;
        ArrayList<Student> student_list = new ArrayList<>();
        sql = "SELECT * FROM CourseSelection WHERE CourseSelection_Course_ID =;";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, course_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student_element = new Student();
                student_element.set_id(rs.getString("Student_ID"));
                student_element.set_enrollment_date(rs.getString("Student_Enroll_Date"));
                student_element.set_email(rs.getString("Student_Email"));
                student_element.set_class_id(rs.getString("Student_Class_ID"));
                student_element.set_transaction_id(rs.getString("Student_Transaction_ID"));
                student_element.set_id_card_number(rs.getString("Student_ID_Card_Number"));
                student_list.add(student_element);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return student_list;
    }
}

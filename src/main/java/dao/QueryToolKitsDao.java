package dao;

import model.Course;
import model.CourseSelection;
import model.Student;
import utils.DButils;
import utils.UnitTestSwitch;

import javax.persistence.criteria.Selection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryToolKitsDao {
    // Query course Selected by studentID
    // Input:
    // - id: student id
    // output:
    // - return a list of the course you want to query
    public ArrayList<Course> queryCourseSelectedByStudentID(String studentID) {
        Connection connection;
        if (UnitTestSwitch.SWITCH)
            connection = DButils.getConnectionUnitTest();
        else
            connection = DButils.getConnection();
        String sql;
        ArrayList<Course> courseArrayList = new ArrayList<>();
        sql = "SELECT *,CourseSelection_Student_ID FROM Course INNER JOIN CourseSelection\n"
                + "ON Course_ID=CourseSelection_Course_ID WHERE CourseSelection_Student_ID = ? ;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.set_id(resultSet.getString("Course_ID"));
                course.set_name(resultSet.getString("Course_Name"));
                course.set_department_id(resultSet.getString("Course_Department_ID"));
                course.set_teacher_id(resultSet.getString("Course_Teacher_ID"));
                course.set_exam_type(resultSet.getString("Course_Exam_Type"));
                course.set_semester(resultSet.getString("Course_Semester"));
                course.set_year(resultSet.getString("Course_Year"));
                course.set_time(resultSet.getString("Course_Time"));
                courseArrayList.add(course);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DButils.closeConnection(connection);
        }
        return courseArrayList;
    }

    // Query student Selected by courseID
    // Input:
    // - id: course id
    // output:
    // - return a list of the student you want to query
    public ArrayList<Student> queryStudentSelectedByCourseID(String courseID) {
        Connection connection;
        if (UnitTestSwitch.SWITCH)
            connection = DButils.getConnectionUnitTest();
        else
            connection = DButils.getConnection();
        String sql;
        ArrayList<Student> studentArrayList = new ArrayList<>();
        sql = "SELECT *,CourseSelection_Course_ID FROM Student INNER JOIN CourseSelection\n"
                + "ON Student_ID=CourseSelection_Student_ID WHERE CourseSelection_Course_ID = ? ;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.set_id(resultSet.getString("Student_ID"));
                student.set_enrollment_date(resultSet.getDate("Student_Enroll_Date").toString());
                student.set_email(resultSet.getString("Student_Email"));
                student.set_class_id(resultSet.getString("Student_Class_ID"));
                student.set_transaction_id(resultSet.getString("Student_Transaction_ID"));
                student.set_id_card_number(resultSet.getString("Person_ID_Card_Number"));
                studentArrayList.add(student);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DButils.closeConnection(connection);
        }
        return studentArrayList;
    }

    // Query course Selected by teacherID
    // Input:
    // - id: teacher id
    // output:
    // - return a list of the course you want to query
    public ArrayList<Course> queryCourseSelectedByTeacherID(String teacherID) {
        Connection connection;
        if (UnitTestSwitch.SWITCH)
            connection = DButils.getConnectionUnitTest();
        else
            connection = DButils.getConnection();
        String sql;
        ArrayList<Course> courseArrayList = new ArrayList<>();
        sql = "SELECT *,Teacher_ID FROM Course INNER JOIN Teacher\n"
                + "ON Course_Teacher_ID=Teacher_ID WHERE Teacher_ID = ? ;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacherID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.set_id(resultSet.getString("Course_ID"));
                course.set_name(resultSet.getString("Course_Name"));
                course.set_department_id(resultSet.getString("Course_Department_ID"));
                course.set_teacher_id(resultSet.getString("Course_Teacher_ID"));
                course.set_exam_type(resultSet.getString("Course_Exam_Type"));
                course.set_semester(resultSet.getString("Course_Semester"));
                course.set_year(resultSet.getString("Course_Year"));
                course.set_time(resultSet.getString("Course_Time"));
                courseArrayList.add(course);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            DButils.closeConnection(connection);
        }
        return courseArrayList;
    }

    //Query Score Selected by classID
    //input: - classID
    //output: - return a list of the courseSelection include the score and StudentID you want
    public ArrayList<CourseSelection> queryScoreSelectedByClassID(String classID){
        Connection connection;
        if (UnitTestSwitch.SWITCH)
            connection = DButils.getConnectionUnitTest();
        else
            connection = DButils.getConnection();
        String sql="SELECT *,Student_Class_ID FROM CourseSelection INNER JOIN Student\n"
                +"ON CourseSelection_Student_ID=Student_ID WHERE Student_Class_ID= ? ;";
        ArrayList<CourseSelection> courseselectionArrayList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,classID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                CourseSelection courseSelection=new CourseSelection();
                courseSelection.setCourseSelectionID(resultSet.getInt("CourseSelection_ID"));
                courseSelection.setCourseSelectionCourseID(resultSet.getString("CourseSelection_Course_ID"));
                courseSelection.setCourseSelectionStudentID(resultSet.getString("CourseSelection_Student_ID"));
                courseSelection.setCourseSelectionDate(resultSet.getString("CourseSelection_Date"));
                courseSelection.setCourseSelectionSemester(resultSet.getString("CourseSelection_Semester"));
                courseSelection.setScore(resultSet.getInt("Score"));
                courseselectionArrayList.add(courseSelection);
            }
            resultSet.close();
            preparedStatement.close();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }finally {
            DButils.closeConnection(connection);
        }
        return courseselectionArrayList;
    }
}

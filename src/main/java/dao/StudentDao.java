package dao;

import utils.DButils;
import utils.UnitTestSwitch;
import utils.DButils;
import utils.UnitTestSwitch;
import model.Person;
import model.Student;

import java.sql.*;

// ACCESS SETTINGS:
// ACCESS SETTING FORMATINON P(XYZ)
// X = 1 can be accessed by admin, 0 cannot be accessed by admin
// Y = 1 can be accessed by teacher, 0 cannot be accessed by teacher
// Z = 1 can be accessed by student, 0 cannot be accessed by student

// - insert: P100
// - delete: P100
// - query : P111
// - update: P100

public class StudentDao {
    public int insertStudent(
                              String studentID, Date studentEnrollDate,
                              String stuentEmail, String studentClassID,
                              String studentTransactionID,String personIDCardNumber){
        Connection connection;
        if(UnitTestSwitch.SWITCH)connection= DButils.getConnectionUnitTest();
        else connection=DButils.getConnection();
        String sql = "INSERT INTO Student (Student_ID, Student_Enroll_Date, Stuent_Email, " +
                "Student_Class_ID, Student_Transaction_ID, Person_ID_Card_Number) VALUES (?,?,?,?,?,?)";
        int insertFlag = 0;
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, studentID);
            ps.setDate(2, studentEnrollDate);
            ps.setString(3, stuentEmail);
            ps.setString(4, studentClassID);
            ps.setString(5, studentTransactionID);
            ps.setString(6, personIDCardNumber);
            insertFlag=ps.executeUpdate();
            ps.close();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(connection);
        }
        return insertFlag;
    }
}

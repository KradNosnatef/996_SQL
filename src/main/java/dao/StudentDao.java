package dao;

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
    public int insertStudent(String idCardNumber,int idCardType,
                             String name,int gender,Date birth,
                             String nationality,String address,
                             String addressID,String addressPhoneNumber,
                             String studentID,String studentEnrollDate,
                             String studentEmail,String studentClassID){

    }
}

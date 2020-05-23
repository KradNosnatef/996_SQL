package dao;

import model.Person;
import model.Student;

import java.sql.Date;

public class StudentDaoTest {
    void outputStudent(Student student){
        System.out.println("StudentID="+student.get_id());
        System.out.println("\nStudentEnrollDate="+student.get_enrollment_date());
        System.out.println("\nStudentEmail="+student.get_email());
        System.out.println("\nStudentClassID="+student.get_class_id());
        System.out.println("\nStudentTransactionID="+student.get_transaction_id());
        System.out.println("\nStudentPersonIDCardNumber="+student.get_id_card_number());
    }

    @org.junit.jupiter.api.Test
    void StudentDaoTestCase1(){//this case design for insert
    }
}

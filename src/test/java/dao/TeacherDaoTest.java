package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import model.Teacher;

class TeacherDaoTest {
    @org.junit.jupiter.api.Test
    void TeacherDaoTest1() {
        String id = "PB17000000";
        String enrollment_date = "2000-01.10";
        String department_id = "11";
        String teacher_title = "professor";
        String id_card_number = "333333200001100000";
        Boolean card_type = false;
        String name = "teacher";
        Boolean gender = true;
        String birth_date = "2000 1 1";
        String nationality = "China";
        String address="";
        String address_phone_number = "";
        String address_postal_code = "";

        TeacherDao teacher_dao = new TeacherDao();
        teacher_dao.insertTeacher(
                id,
                enrollment_date,
                department_id,
                teacher_title,
                id_card_number,
                card_type,
                name,
                gender,
                birth_date,
                nationality,
                address,
                address_postal_code,
                address_phone_number);
    }

    @org.junit.jupiter.api.Test
    void TeacherDaoTest2() {
        String id = "TE17123000";
        String enrollment_date = "2010-01.10";
        String department_id = "11";
        String teacher_title = "professor";
        String id_card_number = "333333230001100000";
        Boolean card_type = false;
        String name = "weilai";
        Boolean gender = true;
        String birth_date = "2000 1 1";
        String nationality = "China";
        String address="";
        String address_phone_number = "";
        String address_postal_code = "";

        TeacherDao teacher_dao = new TeacherDao();
        teacher_dao.insertTeacher(
                id,
                enrollment_date,
                department_id,
                teacher_title,
                id_card_number,
                card_type,
                name,
                gender,
                birth_date,
                nationality,
                address,
                address_postal_code,
                address_phone_number);
    }

    void putoutTeacher(Teacher teacher_element) {
        System.out.println ("ID = " + teacher_element.get_id());
    }

    @org.junit.jupiter.api.Test
    void TeacherDaoTest3() {
        ArrayList<Teacher> clist;
        TeacherDao teacher_dao = new TeacherDao();
        clist = teacher_dao.queryTeacher("333333230001100000", -1);
        System.out.println("Date set 1:");
        for (Teacher teacher_element : clist) {
            putoutTeacher(teacher_element);
        }
        System.out.println ("");

        teacher_dao.deleteTeacher("333333230001100000", 1);
        clist = teacher_dao.queryTeacher("", -1);
        System.out.println("Date set 2:");
        for (Teacher teacher_element : clist) {
            putoutTeacher(teacher_element);
        }
        System.out.println ("");

        clist = teacher_dao.queryTeacher("teacher",2);
        System.out.println("Date set 3:");
        for (Teacher teacher_element : clist) {
            putoutTeacher(teacher_element);
        }
        System.out.println ("");
    }
}
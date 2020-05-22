package dao;

import static org.junit.jupiter.api.Assertions.*;

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

}
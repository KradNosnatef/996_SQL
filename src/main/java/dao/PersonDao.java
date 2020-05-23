package dao;

import model.Person;
import utils.DButils;
import utils.UnitTestSwitch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonDao {
    public Person queryStudentPerson(String id) {
        Connection connection;
        if (UnitTestSwitch.SWITCH)
            connection = DButils.getConnectionUnitTest();
        else
            connection = DButils.getConnection();

        Person person_element = new Person();
        return person_element;
    }

    public Person queryTeacherPerson(String id) {
        Connection connection;
        if (UnitTestSwitch.SWITCH)
            connection = DButils.getConnectionUnitTest();
        else
            connection = DButils.getConnection();

        Person person_element = new Person();
        return person_element;
    }

    public int updatePerson(String id_card_number, String name, Boolean gender, String birth,
                            String nationality, String address, String address_postal_code,
                            String address_phone_number) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();

        int update_flag = 0;

        String sql = "UPDATE Transaction "
                + "SET Name=? "
                + "SET Gender=? "
                + "SET Birth=? "
                + "SET Nationality=? "
                + "SET Address=? "
                + "SET Address_Postal_Code=? "
                + "SET Address_Phone_Number=? WHERE ID_Card_Number=?;";

        // TODO Check Birth legal or not by checking ID_Card_Number
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setBoolean(2, gender);
            ps.setString(3, birth);
            ps.setString(4, nationality);
            ps.setString(5, address);
            ps.setString(6, address_postal_code);
            ps.setString(7, address_phone_number);
            ps.setString(8, id_card_number);
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

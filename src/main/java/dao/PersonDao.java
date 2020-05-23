package dao;

import model.Person;
import utils.DButils;
import utils.UnitTestSwitch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDao {
    public Person queryStudentPerson(String id) {
        Connection conn;
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();

        Person person_element = new Person();

        String get_id_card_number_sql = "SELECT * FROM Student WHERE Student_ID = ?;";
        String id_card_number;
        String sql = "SELECT * FROM Person WHERE Person_ID_Card_Number=?;";

        try {
            PreparedStatement ps = conn.prepareStatement(get_id_card_number_sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return null;
            }
            id_card_number = rs.getString("Person_ID_Card_Number");
            rs.close();
            ps.close();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id_card_number);
            rs = ps.executeQuery();
            if (rs.next()) {
                person_element.set_id_card_number(rs.getString("ID_Card_Number"));
                person_element.set_card_type(rs.getBoolean("ID_Card_Type"));
                person_element.set_name(rs.getString("Name"));
                person_element.set_gender(rs.getBoolean("Gender"));
                person_element.set_birthdate(rs.getString("Birth"));
                person_element.set_nationnality(rs.getString("Nationality"));
                person_element.set_address(rs.getString("Address"));
                person_element.set_address_postal_code("Address_Postal_Code");
                person_element.set_address_phone_number("Address_Phone_Number");
            }
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }

        return person_element;
    }

    public Person queryTeacherPerson(String id) {
        Connection conn;
        if (UnitTestSwitch.SWITCH)
            conn = DButils.getConnectionUnitTest();
        else
            conn = DButils.getConnection();

        Person person_element = new Person();

        String get_id_card_number_sql = "SELECT * FROM Teacher WHERE Teacher_ID = ?;";
        String id_card_number;
        String sql = "SELECT * FROM Person WHERE Person_ID_Card_Number=?;";

        try {
            PreparedStatement ps = conn.prepareStatement(get_id_card_number_sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return null;
            }
            id_card_number = rs.getString("Person_ID_Card_Number");
            rs.close();
            ps.close();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id_card_number);
            rs = ps.executeQuery();
            if (rs.next()) {
                person_element.set_id_card_number(rs.getString("ID_Card_Number"));
                person_element.set_card_type(rs.getBoolean("ID_Card_Type"));
                person_element.set_name(rs.getString("Name"));
                person_element.set_gender(rs.getBoolean("Gender"));
                person_element.set_birthdate(rs.getString("Birth"));
                person_element.set_nationnality(rs.getString("Nationality"));
                person_element.set_address(rs.getString("Address"));
                person_element.set_address_postal_code("Address_Postal_Code");
                person_element.set_address_phone_number("Address_Phone_Number");
            }
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }

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

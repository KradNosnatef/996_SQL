package dao;

import utils.DButils;
import utils.UnitTestSwitch;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonDao {
    public int insertPerson(String IDCardNumber, boolean idCardType,
                             String name, boolean gender, Date birth,
                             String nationality, String address,
                             String addressID, String addressPhoneNumber){
        Connection connection;
        if(UnitTestSwitch.SWITCH)connection= DButils.getConnectionUnitTest();
        else connection=DButils.getConnection();
        String sql = "INSERT INTO Person (ID_Card_Number, ID_Card_Type, Name," +
                " Gender, Birth, Nationality, Address, Address_ID, Address_Phone_Number) VALUES (?,?,?,?,?,?,?,?,?);";
        int insertFlag = 0;
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,IDCardNumber);
            ps.setBoolean(2,idCardType);
            ps.setString(3,name);
            ps.setBoolean(4,gender);
            ps.setDate(5,birth);
            ps.setString(6,nationality);
            ps.setString(7,address);
            ps.setString(8,addressID);
            ps.setString(9,addressPhoneNumber);
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

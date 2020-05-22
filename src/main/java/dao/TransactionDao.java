package dao;

import utils.DButils;
import utils.UnitTestSwitch;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDao {
    public int insertTransaction(
            String ID, boolean type, Date date,
            String originClassID,String currentClassID,
            String leagueMember,boolean reason
            ){
        Connection connection;
        if(UnitTestSwitch.SWITCH)connection= DButils.getConnectionUnitTest();
        else connection=DButils.getConnection();
        String sql="INSERT INTO Transaction(Transaction_ID, Transaction_Type, " +
                "Transaction_Date, Transaction_Origin_Class_ID," +
                "Transaction_Current_Class_ID,Transaction_League_Member,Transaction_Reason)VALUES(?,?,?,?,?,?,?)";
        int insertFlag=0;
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);
            preparedStatement.setBoolean(2,type);
            preparedStatement.setDate(3,date);
            preparedStatement.setString(4,originClassID);
            preparedStatement.setString(5,currentClassID);
            preparedStatement.setString(6,leagueMember);
            preparedStatement.setBoolean(7,reason);
            insertFlag=preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }finally {
            DButils.closeConnection(connection);
        }
        return insertFlag;
    }

}

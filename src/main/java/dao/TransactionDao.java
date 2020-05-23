package dao;

import model.Transaction;
import model.Student;
import utils.DButils;
import utils.UnitTestSwitch;

import java.sql.*;
import java.util.ArrayList;

// ACCESS SETTINGS:
// ACCESS SETTING FORMATINON P(XYZ)
// X = 1 can be accessed by admin, 0 cannot be accessed by admin
// Y = 1 can be accessed by teacher, 0 cannot be accessed by teacher
// Z = 1 can be accessed by student, 0 cannot be accessed by student

// - insert: P100
// - delete: P100
// - query : P111
// - update: P100

public class TransactionDao {
    // Insert new transaction element
    // Input:
    // - id: transaction id
    // - type: transaction type
    // - date: transaction date
    // - and so on
    // Ouput:
    // - If insertion action succeed, return a number, which stands for the row count for SQL Data Manipulation
    // Language (DML) statements. Otherwise, it will return 0
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

    // Delete a transaction
    // Input:
    // - id: transaction id
    // Ouput:
    // - return 0 if the element is not existed
    // - return 1 if we delete the element successfully
    public int deleteTransaction(String ID){
        Connection connection;
        if(UnitTestSwitch.SWITCH)connection= DButils.getConnectionUnitTest();
        else connection=DButils.getConnection();
        String sql = "DELETE FROM Transaction WHERE Transaction_ID = ?;";
        int deleteFlag=0;
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);
            deleteFlag=preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }finally {
            DButils.closeConnection(connection);
        }
        return deleteFlag;
    }

    // Query traction information
    // Input:
    // - elementSelector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 0 -- select by transactionID
    //   type = 1 -- select by studentID
    //   type = -1 -- selecat all elements
    // Ouput:
    // - return an array list for your query.
    public ArrayList<Transaction> queryTransaction(String elementSelector, int type){
        Connection connection;
        if(UnitTestSwitch.SWITCH)connection= DButils.getConnectionUnitTest();
        else connection=DButils.getConnection();
        String sql;
        ArrayList<Transaction> transactionArrayList=new ArrayList<>();
        if(type==-1){
            sql="SELECT * FROM Transaction ORDER BY Transaction_ID";
        }else if(type==0){
            sql="SELECT * FROM Transaction WHERE Transaction_ID = ? ORDER BY Transaction_ID";
        }else if(type==1){
            sql="SELECT *,Student_ID FROM Transaction INNER JOIN Student\n" +
                    "ON TRANSACTION_ID=STUDENT_TRANSACTION_ID WHERE Student_ID = ? ";
        }else{
            return transactionArrayList;
        }
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            if(type!=-1)preparedStatement.setString(1,elementSelector);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.set_id(resultSet.getString("Transaction_ID"));
                transaction.set_type(resultSet.getBoolean("Transaction_Type"));
                transaction.set_date(resultSet.getDate("Transaction_Date").toString());
                transaction.set_original_class_id(resultSet.getString("Transaction_Origin_Class_ID"));
                transaction.set_current_class_id(resultSet.getString("Transaction_Current_Class_ID"));
                transaction.set_league_member(resultSet.getString("Transaction_League_Member"));
                transaction.set_reasons(Boolean.toString(resultSet.getBoolean("Transaction_Reason")));
                transactionArrayList.add(transaction);
            }
            resultSet.close();
            preparedStatement.close();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }finally {
            DButils.closeConnection(connection);
        }
        return transactionArrayList;
    }

    // Modify campus information
    // - row & column selector: used to select the value you want to update , use id to select row
    // - columnSelector:
    //      0 -- update type
    //      1 -- update date
    //      2 -- update originClassID
    //      3 -- update CurrentClassID
    //      4 -- update LeagueMember
    //      5 -- update Reason
    // - value:
    //      used to update the database
    // Ouput:
    // - return 0 if element is not existed
    // - return 1 if update action succeed
    // - return -1 if action is illegal
    public int updateTransaction(String rowSelector,int columnSelector,String value){
        Connection connection;
        if(UnitTestSwitch.SWITCH)connection= DButils.getConnectionUnitTest();
        else connection=DButils.getConnection();
        String sql,sql1,sql2;
        int updateFlag=0;
        sql1="SET Transaction_";
        sql2="WHERE Transaction_ID = ?";
        if(columnSelector==0){
            sql1+="Type = ? ";
        }else if(columnSelector==1){
            sql1+="Date = ? ";
        }else if(columnSelector==2){
            sql1+="Origin_Class_ID = ? ";
        }else if(columnSelector==3){
            sql1+="Current_Class_ID = ? ";
        }else if(columnSelector==4){
            sql1+="League_Member = ? ";
        }else if(columnSelector==5){
            sql1+="Reason = ? ";
        }else return -1;
        sql="UPDATE Transaction "+sql1+sql2;
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,value);
            preparedStatement.setString(2,rowSelector);
            updateFlag = preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            updateFlag=-1;
        }finally {
            DButils.closeConnection(connection);
        }
        return updateFlag;
    }
}

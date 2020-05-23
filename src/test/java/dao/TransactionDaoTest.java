package dao;

import model.Transaction;

import java.sql.Date;
import java.util.ArrayList;

public class TransactionDaoTest {
    void outputTransaction(Transaction transaction){
        System.out.print("id="+transaction.get_id());
        System.out.print(",type="+transaction.get_type());
        System.out.print(",Date="+transaction.get_date());
        System.out.print(",Origin="+transaction.get_original_class_id());
        System.out.print(",Current="+transaction.get_current_class_id());
        System.out.print(",League="+transaction.get_league_member());
        System.out.println(",Reason="+transaction.get_reasons());
    }

    @org.junit.jupiter.api.Test
    void TransactionDaoTestCase1() {//this case design for insert
        TransactionDao transactionDao=new TransactionDao();
        String transactionID="1";
        String studentID="1";
        Boolean type=true;
        String date="1970-01-01";
        String originClassID="17";
        String currentClassID="18";
        String leagueMember="nope";
        String reason="1";
        int flag= transactionDao.insertTransaction(transactionID,studentID,type,date,originClassID,currentClassID,leagueMember,reason);
        if(flag==0)System.out.println("Error when inserting");
        System.exit(0);
    }

    @org.junit.jupiter.api.Test
    void TransactionDaoTestCase2(){//this case design for delete
        TransactionDao transactionDao=new TransactionDao();
        String ID="1";
        int flag=transactionDao.deleteTransaction(ID);
        if(flag==0)System.out.println("Error when deleting");
        System.exit(0);
    }
    @org.junit.jupiter.api.Test
    void TransactionDaoTestCase3() {//this case design for Query
        TransactionDao transactionDao=new TransactionDao();
        ArrayList<Transaction> transactionArrayList
                = transactionDao.queryTransaction("1737",1);
        for(Transaction value : transactionArrayList){
            outputTransaction(value);
        }
    }
    @org.junit.jupiter.api.Test
    void TransactionDaoTestCase4() {//this case design for Modify
        TransactionDao transactionDao=new TransactionDao();
        String ID="1";
        int type=3;
        String value="18";
        int flag=transactionDao.updateTransaction(ID,type,value);
        if(flag==-1)System.out.println("Error when updating");
        else if (flag==0)System.out.println("ID not existed");
        else System.out.println("update successfully");
        System.exit(0);
    }
}

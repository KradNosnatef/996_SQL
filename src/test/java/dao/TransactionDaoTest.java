package dao;

import model.Transaction;

import java.sql.Date;

public class TransactionDaoTest {
    void outputTransaction(Transaction transaction){
    }

    @org.junit.jupiter.api.Test
    void TransactionDaoTestCase1() {//this case design for insert
        TransactionDao transactionDao=new TransactionDao();
        String ID="02";
        Boolean type=true;
        Date date=new Date(0);
        String originClassID="17";
        String currentClassID="17";
        String leagueMember="nope";
        Boolean reason=true;
        int flag= transactionDao.insertTransaction(ID,type,date,originClassID,currentClassID,leagueMember,reason);
        if(flag==0)System.out.println("Error when inserting");
        System.exit(0);
    }
}

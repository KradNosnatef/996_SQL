package service;

import dao.UserDao;
import model.User;
import utils.DButils;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class LoginServletTest {
    @org.junit.jupiter.api.Test
    void dbTest1() {
        UserDao userdao = new UserDao();
        String username="PB17000144";
        String password="123456";
        User user = userdao.loginUnitTest(username, password);
        if (user != null)
            System.out.println("login!");
    }
}
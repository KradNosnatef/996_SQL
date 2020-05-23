package dao;

import static org.junit.jupiter.api.Assertions.*;
import model.User;
import model.Campus;

import java.util.ArrayList;

class UserDaoTest {
    void putoutUser(User user_element) {
        System.out.print("User_name : " + user_element.get_username());
        System.out.println("   User_type : " + user_element.get_usertype());
    }

    @org.junit.jupiter.api.Test
    void UserDaoTest1() {
        UserDao userdao = new UserDao();
        ArrayList<User> user_list = userdao.queryUser();

        for (User user_element : user_list) {
            putoutUser(user_element);
        }
    }
}
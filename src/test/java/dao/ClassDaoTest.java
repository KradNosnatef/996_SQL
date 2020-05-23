package dao;

import static org.junit.jupiter.api.Assertions.*;

import model.Class;

import java.util.ArrayList;

class ClassDaoTest {
    void putoutClass(Class class_element) {
        System.out.println(class_element.get_id());
    }

    @org.junit.jupiter.api.Test
    void ClassDaoTest1() {
        ArrayList<Class> classes = new ArrayList<>();
        classes = new ClassDao().queryClass("", -1);
        for (Class class_element : classes) {
            putoutClass(class_element);
        }
    }
}
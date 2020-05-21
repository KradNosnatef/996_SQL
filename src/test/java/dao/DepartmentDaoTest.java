package dao;

import model.Campus;
import model.Department;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDaoTest {
    void outputDepartment(Department department_element) {
        System.out.print("id = " + department_element.get_id());
        System.out.print(", name = " + department_element.get_name());
        System.out.print(", dean = " + department_element.get_dean());
        System.out.print(", campus_id = " + department_element.get_campus_id());
        System.out.println(", address = " + department_element.get_address());
    }

    @org.junit.jupiter.api.Test
    void DepartmentDaoTest1() {
        DepartmentDao department_dao = new DepartmentDao();

        String department1_id = "11";
        String department1_name = "CS";
        String department1_address = "xixiaoqu";
        String department1_dean = "xiangyang li";
        String department1_campus_id = "USTC-002";

        String department2_id = "01";
        String department2_name = "Math";
        String department2_address = "dongxiaoqu";
        String department2_dean = "wobuzhidao";
        String department2_campus_id = "USTC-001";

        department_dao.insertDepartment(department1_id, department1_name, department1_address, department1_dean,
                department1_campus_id);
        department_dao.insertDepartment(department2_id, department2_name, department2_address, department2_dean,
                department2_campus_id);
    }

    @org.junit.jupiter.api.Test
    void DepartmentDaoTest2() {
        DepartmentDao department_dao = new DepartmentDao();

        String department1_id = "11";
        String department1_name = "CS";
        String department1_address = "xixiaoqu";
        String department1_dean = "xiangyang li";
        String department1_campus_id = "USTC-002";

        String department2_id = "01";
        String department2_name = "Math";
        String department2_address = "dongxiaoqu";
        String department2_dean = "wobuzhidao";
        String department2_campus_id = "USTC-001";

        ArrayList<Department> clist = department_dao.queryDepartment("all",-1);
        for (Department department_element : clist) {
            outputDepartment(department_element);
        }
    }

    @org.junit.jupiter.api.Test
    void DepartmentDaoTest3() {
        DepartmentDao department_dao = new DepartmentDao();
        System.out.println("Before Deletion");
        ArrayList<Department> clist = department_dao.queryDepartment("all",-1);
        for (Department department_element : clist) {
            outputDepartment(department_element);
        }

        String department1_id = "11";
        String department1_name = "CS";
        String department1_address = "xixiaoqu";
        String department1_dean = "xiangyang li";
        String department1_campus_id = "USTC-002";

        String department2_id = "01";
        String department2_name = "Math";
        String department2_address = "dongxiaoqu";
        String department2_dean = "wobuzhidao";
        String department2_campus_id = "USTC-001";

        department_dao.deleteDepartment(department1_id, 0);
        System.out.println("Deletion 1:");
        clist = department_dao.queryDepartment("all",-1);
        for (Department department_element : clist) {
            outputDepartment(department_element);
        }

        System.out.println("Deletion 2:");
        department_dao.deleteDepartment(department2_name, 1);
        clist = department_dao.queryDepartment("all",-1);
        for (Department department_element : clist) {
            outputDepartment(department_element);
        }
    }

    @org.junit.jupiter.api.Test
    void DepartmentDaoTest4() {
        DepartmentDao department_dao = new DepartmentDao();
        System.out.println("Before Alter");
        ArrayList<Department> clist = department_dao.queryDepartment("all",-1);
        for (Department department_element : clist) {
            outputDepartment(department_element);
        }

        String department1_id = "11";
        String department1_name = "CS";
        String department1_address = "xixiaoqu";
        String department1_dean = "xiangyang li";
        String department1_campus_id = "USTC-002";

        String department2_id = "01";
        String department2_name = "Math";
        String department2_address = "dongxiaoqu";
        String department2_dean = "wobuzhidao";
        String department2_campus_id = "USTC-001";

        department_dao.updateDepartment(0,4,department1_id, department2_campus_id);
        department_dao.updateDepartment(1,2,department2_name, "hahaha");
        System.out.println("alter:");
        clist = department_dao.queryDepartment("all",-1);
        for (Department department_element : clist) {
            outputDepartment(department_element);
        }
    }
}
package dao;

import model.User;
import model.Campus;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CampusDaoTest {
    void outputCampus(Campus campus_element) {
        System.out.print("id = " + campus_element.get_id());
        System.out.print(", name = " + campus_element.get_name());
        System.out.println(", address = " + campus_element.get_address());
    }

    @org.junit.jupiter.api.Test
    void CampusDaoTestCase1() {
        CampusDao campus_dao = new CampusDao();

        String id1 = "USTC-001";
        String name1 = "EAST-CAMPUS";
        String address1 = "RUARUARUA";

        String id2 = "USTC-002";
        String name2 = "WEST-CAMPUS";
        String address2 = "HAHAHA";

        int flag1 = campus_dao.insertCampus(id1, name1, address1);
        int flag2 = campus_dao.insertCampus(id2, name2, address2);

        if (flag1 == 0) {
            System.out.println("Error when inserting EAST");
            // System.exit(0);
        }
        if (flag2 == 0) {
            System.out.println("Error when inserting WEST");
            // System.exit(0);
        }
        System.exit(0);
    }

    @org.junit.jupiter.api.Test
    void CampusDaoTestCase2() {
        CampusDao campus_dao = new CampusDao();

        System.out.println("Search list 1:");
        ArrayList<Campus> clist = campus_dao.queryCampus("all",-1);
        for (Campus campus : clist) {
            outputCampus(campus);
        }

        String id1 = "USTC-001";
        String name1 = "EAST-CAMPUS";
        String address1 = "RUARUARUA";

        String id2 = "USTC-002";
        String name2 = "WEST-CAMPUS";
        String address2 = "HAHAHA";

        System.out.println("Search list 2:");
        clist = campus_dao.queryCampus(id1,0);
        for (Campus campus : clist) {
            outputCampus(campus);
        }
        System.exit(0);
    }

    @org.junit.jupiter.api.Test
    void CampusDaoTestCase3() {
        CampusDao campus_dao = new CampusDao();

        System.out.println("Search list 1:");
        ArrayList<Campus> clist = campus_dao.queryCampus("all",-1);
        for (Campus value : clist) {
            outputCampus(value);
        }

        String id1 = "USTC-001";
        String name1 = "EAST-CAMPUS";
        String address1 = "RUARUARUA";

        String id2 = "USTC-002";
        String name2 = "WEST-CAMPUS";
        String address2 = "HAHAHA";
        campus_dao.deleteCampus(name1,1);
        campus_dao.deleteCampus(id2,0);

        System.out.println("Search list 2:");
        clist = campus_dao.queryCampus("all",-1);
        for (Campus campus : clist) {
            outputCampus(campus);
        }
    }

    @org.junit.jupiter.api.Test
    void CampusDaoTestCase4() {
        CampusDao campus_dao = new CampusDao();

        System.out.println("Search list 1:");
        ArrayList<Campus> clist = campus_dao.queryCampus("all",-1);
        for (Campus campus : clist) {
            outputCampus(campus);
        }

        String id1 = "USTC-001";
        String name1 = "EAST-CAMPUS";
        String address1 = "RUARUARUA";

        String id2 = "USTC-002";
        String name2 = "WEST-CAMPUS";
        String address2 = "HAHAHA";
        campus_dao.updateCampus(0,1,id1,"MIDDLE-CAMPUS");

        System.out.println("Search list 2:");
        clist = campus_dao.queryCampus("all",-1);
        for (Campus campus : clist) {
            outputCampus(campus);
        }
    }
}
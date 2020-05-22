package dao;

import model.Campus;
import model.Course;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CourseDaoTest {
    void outputCourse(Course course_element) {
        System.out.print("id = " + course_element.get_id());
        System.out.print(", name = " + course_element.get_name());
        System.out.print(", department_id = " + course_element.get_department_id());
        System.out.print(", teacher_id = " + course_element.get_teacher_id());
        System.out.print(", exam_type = " + course_element.get_exam_type());
        System.out.print(", semester = " + course_element.get_semester());
        System.out.print(", year = " + course_element.get_year());
        System.out.print(", time = " + course_element.get_time() + "\n");
    }

    @Test
    void insertCourse() {
        CourseDao course_dao = new CourseDao();

        String id1 = "001";
        String name1 = "db";
        String department_id1 = "011";
        String exam_type1 = "test";

        String id2 = "002";
        String name2 = "ca";
        String department_id2 = "011";
        String exam_type2 = "test";

        int flag1 = course_dao.insertCourse(id1,name1,department_id1,exam_type1);
        int flag2 = course_dao.insertCourse(id2,name2,department_id2,exam_type2);

        if (flag1 == 0) {
            System.out.println("Error when inserting 001");
            // System.exit(0);
        }
        if (flag2 == 0) {
            System.out.println("Error when inserting 002");
            // System.exit(0);
        }
        ArrayList<Course> clist = course_dao.queryCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }
        System.exit(0);
    }

    @Test
    void deleteCourse() {
        CourseDao course_dao = new CourseDao();
        System.out.println("Search list 1:\n");
        ArrayList<Course> clist = course_dao.queryCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }

        /*String id1 = "001";
        String name1 = "db";
        String department_id1 = "011";
        String exam_type1 = "test";

        String id2 = "002";
        String name2 = "ca";
        String department_id2 = "011";
        String exam_type2 = "test";*/

        int flag = course_dao.deleteCourse("011",2);
        System.out.println("Search list 2:" + flag + "\n");
        clist = course_dao.queryCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }
    }

    @Test
    void queryCourse() {
        CourseDao course_dao = new CourseDao();
        System.out.println("Search list 1:\n");
        ArrayList<Course> clist = course_dao.queryCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }

        /*String id1 = "001";
        String name1 = "db";
        String department_id1 = "011";
        String exam_type1 = "test";

        String id2 = "002";
        String name2 = "ca";
        String department_id2 = "011";
        String exam_type2 = "test";*/

        System.out.println("Search list 2:\n");
        clist = course_dao.queryCourse("test",3);
        for (Course course : clist){
            outputCourse(course);
        }
    }

    @Test
    void updateCourse() {
        CourseDao course_dao = new CourseDao();
        System.out.println("Search list 1:\n");
        ArrayList<Course> clist = course_dao.queryCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }

        /*String id1 = "001";
        String name1 = "db";
        String department_id1 = "011";
        String exam_type1 = "test";

        String id2 = "002";
        String name2 = "ca";
        String department_id2 = "011";
        String exam_type2 = "test";*/

        int flag = course_dao.updateCourse(0,2,"001","0011");
        System.out.println("Search list 2:" + flag + "\n");
        clist = course_dao.queryCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }
    }

    @Test
    void insertStartCourse() {
        CourseDao course_dao = new CourseDao();
        System.out.println("Search list 1:\n");
        ArrayList<Course> clist = course_dao.queryStartCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }

        /*String id1 = "001";
        String name1 = "db";
        String department_id1 = "011";
        String exam_type1 = "test";*/
        String teacher_id1 = "001";
        String semester1 = "spring";
        String year1 = "2020";
        String time1 = "24";
        /*String id2 = "002";
        String name2 = "ca";
        String department_id2 = "011";
        String exam_type2 = "test";*/
        String teacher_id2 = "002";
        String semester2 = "spring";
        String year2 = "2021";
        String time2 = "15";

        int flag1 = course_dao.insertStartCourse("001",teacher_id1,semester1,year1,time1);
        int flag2 = course_dao.insertStartCourse("002",teacher_id2,semester2,year2,time2);
        System.out.println("Search list 2:" + flag1 + flag2 + "\n");
        clist = course_dao.queryStartCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }
    }

    @Test
    void deleteStartCourse() {
        CourseDao course_dao = new CourseDao();
        System.out.println("Search list 1:\n");
        ArrayList<Course> clist = course_dao.queryStartCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }

        /*String id1 = "001";
        String name1 = "db";
        String department_id1 = "011";
        String exam_type1 = "test";
        String teacher_id1 = "001";
        String semester1 = "spring";
        String year1 = "2020";
        String time1 = "24";
        String id2 = "002";
        String name2 = "ca";
        String department_id2 = "011";
        String exam_type2 = "test";
        String teacher_id2 = "002";
        String semester2 = "spring";
        String year2 = "2021";
        String time2 = "15";*/

        int flag = course_dao.deleteStartCourse("001",0);
        System.out.println("Search list 2:" + flag + "\n");
        clist = course_dao.queryStartCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }
    }

    @Test
    void queryStartCourse() {
        CourseDao course_dao = new CourseDao();
        System.out.println("Search list 1:\n");
        ArrayList<Course> clist = course_dao.queryStartCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }

        /*String id1 = "001";
        String name1 = "db";
        String department_id1 = "011";
        String exam_type1 = "test";
        String teacher_id1 = "001";
        String semester1 = "spring";
        String year1 = "2020";
        String time1 = "24";
        String id2 = "002";
        String name2 = "ca";
        String department_id2 = "011";
        String exam_type2 = "test";
        String teacher_id2 = "002";
        String semester2 = "spring";
        String year2 = "2021";
        String time2 = "15";*/

        System.out.println("Search list 2:\n");
        clist = course_dao.queryStartCourse("2021",4);
        for (Course course : clist){
            outputCourse(course);
        }
    }

    @Test
    void updateStartCourse() {
        CourseDao course_dao = new CourseDao();
        System.out.println("Search list 1:\n");
        ArrayList<Course> clist = course_dao.queryStartCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }

        /*String id1 = "001";
        String name1 = "db";
        String department_id1 = "011";
        String exam_type1 = "test";
        String teacher_id1 = "001";
        String semester1 = "spring";
        String year1 = "2020";
        String time1 = "24";
        String id2 = "002";
        String name2 = "ca";
        String department_id2 = "011";
        String exam_type2 = "test";
        String teacher_id2 = "002";
        String semester2 = "spring";
        String year2 = "2021";
        String time2 = "15";*/

        int flag = course_dao.updateStartCourse(1,5,"db","12345");
        System.out.println("Search list 2:" + flag + "\n");
        clist = course_dao.queryStartCourse("all",-1);
        for (Course course : clist){
            outputCourse(course);
        }
    }
}
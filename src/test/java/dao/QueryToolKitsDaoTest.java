package dao;

import model.Course;

import java.util.ArrayList;

public class QueryToolKitsDaoTest {
    void outputCourse(Course course){
        System.out.print("id="+course.get_id());
        System.out.print(",name="+course.get_name());
        System.out.print(",dpID="+course.get_department_id());
        System.out.print(",tchID="+course.get_teacher_id());
        System.out.print(",exType="+course.get_exam_type());
        System.out.print(",smter="+course.get_semester());
        System.out.print(",year="+course.get_year());
        System.out.println(",time="+course.get_time());
    }

    @org.junit.jupiter.api.Test
    void queryCourseSelectedByStudentID_TB(){
        QueryToolKitsDao queryToolKitsDao=new QueryToolKitsDao();
        String ID="1735";
        ArrayList<Course> courseArrayList
                =queryToolKitsDao.queryCourseSelectedByStudentID(ID);
        for(Course value : courseArrayList){
            outputCourse(value);
        }
    }
}

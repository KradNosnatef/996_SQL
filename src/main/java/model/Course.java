package model;

import java.io.Serializable;

// Define Course Class
// id: class id information
// name: name of class
// department_id: foreign key in Department table
// teacher_id: foreign key in Teacher table
// semester: 'AUTUMN' or 'SPRING'
// year: which year this couerse is opening
// time: TODO
//      this part is to be done, need a standard format to represent a time in a week
//      the easiest way is '{DAY}-{NUM}', {DAY} = {MON, TUE, ..., SUN}, {NUM} = {1, 2, 3, 4, 5}
//      **BUT WE NEED A TIME-FORMAT HERE**, you can take dateFormat in java/utils/Dateutils.java as reference.

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String department_id;
    private String teacher_id;
    private String exam_type;
    private String semester;
    private String year;
    private String time;

    public String get_id() { return id; }

    public String get_name() { return name; }

    public String get_department_id() { return department_id; }

    public String get_teacher_id() { return teacher_id; }

    public String get_exam_type() { return exam_type; }

    public String get_semester() {return semester; }

    public String get_year() { return year; }

    public String get_time() {return time;}

    public void set_id(String new_id) { id = new_id; }

    public void set_name(String new_name) { name = new_name; }

    public void set_department_id(String new_department_id) { department_id = new_department_id; }

    public void set_teacher_id(String new_tester_id) { teacher_id = new_tester_id; }

    public void set_exam_type(String new_exam_type) { exam_type = new_exam_type;}

    public void set_semester(String new_semester) { semester = new_semester;}

    public void set_year(String new_year) {year = new_year;}

    public void set_time(String new_time) {time = new_time;}
}

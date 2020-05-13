package model;

import java.io.Serializable;

// Define Class Class (Hmmm, maybe sound a little strange)
// id: class id
// date: date of enrollment
// grade: grade of class, it is also the grade information for all the student in this class
// department_id: foreign key from Department table
// head_teacher_id: foreign key from Teacher table
public class Class implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String date;
    private String grade;
    private String department_id;
    private String head_teacher_id;

    public String get_id() {
        return id;
    }

    public String get_name() { return name; }

    public String get_date() { return date; }

    public String get_grade() { return grade; }

    public String get_department_id() { return department_id; }

    public String get_head_teacher_id() { return head_teacher_id; }

    public void set_id(String new_id) { id = new_id; }

    public void set_name(String new_name) { name = new_name; }

    public void set_date(String new_date) { date = new_date; }

    public void set_grade(String new_grade) { grade = new_grade; }

    public void set_department_id(String new_department_id) { department_id = new_department_id; }

    public void set_head_teacher_id(String new_head_teacher_id) {
        head_teacher_id = new_head_teacher_id;
    }
}

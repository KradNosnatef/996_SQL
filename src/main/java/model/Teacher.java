package model;

import java.io.Serializable;

// Define Teacher Class
// id: teacher id
// enrollment_date: enrollment date of a teacher
// department_id: foreign key in Department table
// teacher_title: such as 'Professor'. *Some teacher might have no titles*
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String enrollment_date;
    private String department_id;
    private String teacher_title;
    private String id_card_number;

    public String get_id() { return id; }

    public String get_enrollment_date() { return enrollment_date; }

    public String get_department_id() {return department_id;}

    public String get_teacher_title() { return teacher_title;}

    public String get_id_card_number () { return id_card_number; }

    public void set_id(String new_id) { id = new_id; }

    public void set_enrollment_date(String new_enrollment_date) { enrollment_date = new_enrollment_date; }

    public void set_department_id(String new_department_id) { department_id = new_department_id;}

    public void set_teacher_title(String new_teacher_title) { teacher_title = new_teacher_title;}

    public void set_id_card_number(String new_id_card_number) { id_card_number = new_id_card_number; }
}

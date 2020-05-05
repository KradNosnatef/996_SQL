package model;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String enrollment_date;
    private String email;
    private String class_id;
    private String transaction_id;

    public String get_id() {
        return id;
    }

    public String get_enrollment_date() {
        return enrollment_date;
    }

    public String get_email() {
        return email;
    }

    public String get_class_id() {
        return class_id;
    }

    public String get_transaction_id() {
        return transaction_id;
    }

    public void set_id(String new_id) {
        id = new_id;
    }

    public void set_enrollment_date(String new_enrollment_date) {
        enrollment_date = new_enrollment_date;
    }

    public void set_email(String new_email) {
        email = new_email;
    }

    public void set_class_id(String new_class_id) {
        class_id = new_class_id;
    }

    public void set_transaction_id(String new_transaction_id) {
        transaction_id = new_transaction_id;
    }
}

package model;

import java.io.Serializable;

// Transaction Class
// !!!CAUTION!!!
// Whenever a transaciton element is created, information in `Class` table, `Student` table will also be altered.
// type: 0 - Suspension of school, 1 - other reasons
// reasons will record in detail
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private boolean type;
    private String date;
    private String original_class_id;
    private String current_class_id;
    private String league_member;
    private String reasons;

    public String get_id() { return id; }

    public boolean get_type() { return type; }

    public String get_date() {
        return date;
    }

    public String get_original_class_id() { return original_class_id; }

    public String get_current_class_id() { return current_class_id; }

    public String get_league_member() { return league_member; }

    public String get_reasons() { return reasons; }

    public void set_id(String new_id) { id = new_id; }

    public void set_type(boolean new_type) { type = new_type; }

    public void set_date(String new_date) { date = new_date; }

    public void set_original_class_id(String new_original_class_id) { original_class_id = new_original_class_id; }

    public void set_current_class_id(String new_current_class_id) { current_class_id = new_current_class_id; }

    public void set_league_member(String new_league_member) { league_member = new_league_member; }

    public void set_reasons(String new_reasons) { reasons = new_reasons; }
}

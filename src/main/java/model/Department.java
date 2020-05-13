package model;

import java.io.Serializable;

// Define Deparetment Class
// id: id of a department
// name: name of a department
// address: address of a department
// dean: name of the department's dean
// campus_id: foreign key from Campus table
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String address;
    private String dean;
    private String campus_id;

    public String get_id() { return id; }

    public String get_name() { return name; }

    public String get_address() { return address; }

    public String get_dean() { return dean; }

    public String get_campus_id() { return campus_id; }

    public void set_id(String new_id) { id = new_id; }

    public void set_name(String new_name) { name = new_name; }

    public void set_address(String new_address) { address = new_address; }

    public void set_dean(String new_dean) { dean = new_dean; }

    public void set_campus_id(String new_campus_id) { campus_id = new_campus_id; }
}

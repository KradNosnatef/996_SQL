package model;

import java.io.Serializable;

public class Campus implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String address;

    public String get_id() { return id; }
    public String get_name() { return name; }
    public String get_address() { return address; }

    public void set_id(String new_id) {id = new_id;}
    public void set_name(String new_name) {name = new_name;}
    public void set_address(String new_address) {address = new_address;}
}

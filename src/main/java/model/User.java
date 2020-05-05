package model;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String username;
    private String password;
    private int usertype;
    private String foreignid;

    public int get_id() { return id; }

    public String get_username() { return username; }

    public String get_password() { return password; }

    public int get_usertype() { return usertype; }

    public String get_foreignid() { return foreignid; }

    public void set_id(int new_id) {id = new_id; }

    public void set_username(String new_username) { username = new_username; }

    public void set_password(String new_password) { password = new_password; }

    public void set_usertype(int new_usertype) { usertype = new_usertype; }

    public void set_foreignid(String new_foreignid) { foreignid = new_foreignid; }
}

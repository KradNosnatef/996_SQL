package model;

import java.io.Serializable;

// Define User Class
// id: auto increasement in database
// username: username of user
// password: password of user
//      TODO we should store password as a Hash value in database, but this part can be delayed as we finished all
//       other parts
// usertype: 3 types
//      0 - admin user
//      1 - teacher user
//      other - student user (in normal case, we set `2` for student user)
// foreignid:
//      for admin user - this value is useless
//      for student user - this value is foreign key, which is from student_id
//      for teacher user - this value is foreign key, which is from teacher_id
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

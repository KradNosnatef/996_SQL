package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;
import model.*;
import model.Class;

public class AdminDao extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String action;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        action = request.getParameter("action");

        switch (action) {
            case "query_all_user":
                query_all_user(request, response);
                break;
            case "insert_user":
                insert_user(request, response);
                break;
            case "delete_user":
                delete_user(request, response);
                break;
            case "update_user":
                update_user(request, response);
                break;

            case "query_all_campus":
                query_all_campus(request, response);
                break;
            case "insert_campus":
                insert_campus(request, response);
                break;
            case "delete_campus":
                delete_campus(request, response);
                break;
            case "update_campus":
                update_campus(request, response);
                break;

            case "query_all_department":
                query_all_department(request, response);
                break;
            case "insert_department":
                insert_department(request, response);
                break;
            case "delete_department":
                delete_department(request, response);
                break;
            case "update_department":
                update_department(request, response);
                break;

            case "query_all_class":
                query_all_class(request, response);
                break;
            case "insert_class":
                insert_class(request, response);
                break;
            case "delete_class":
                delete_class(request, response);
                break;
            case "update_class":
                update_class(request, response);
                break;

            case "query_all_student":
                query_all_student(request, response);
                break;
            case "insert_student":
                insert_student(request, response);
                break;
            case "delete_student":
                delete_student(request, response);
                break;
            case "update_student":
                update_student(request, response);
                break;

            case "query_all_teacher":
                query_all_teacher(request, response);
                break;
            case "insert_teacher":
                insert_teacher(request, response);
                break;
            case "delete_teacher":
                delete_teacher(request, response);
                break;
            case "update_teacher":
                update_teacher(request, response);
                break;

            case "query_all_course":
                query_all_course(request, response);
                break;
            case "insert_course":
                insert_course(request, response);
                break;
            case "delete_course":
                delete_course(request, response);
                break;
            case "update_course":
                update_course(request, response);
                break;

            case "query_all_transaction":
                query_all_transaction(request, response);
                break;
            case "insert_transaction":
                insert_transaction(request, response);
                break;
            case "delete_transaction":
                delete_transaction(request, response);
                break;
            case "update_transaction":
                update_transaction(request, response);
                break;

            case "insert_selection":
                insert_selection(request, response);
                break;
            case "delete_selection":
                delete_selection(request, response);
                break;

            case "infoquery_student":
                infoquery_student(request, response);
                break;
            case "infoquery_teacher":
                infoquery_teacher(request, response);
                break;
            case "infoquery_studentcourse":
                infoquery_studentcourse(request, response);
                break;
            case "infoquery_coursestudent":
                infoquery_coursestudent(request, response);
                break;
            case "infoquery_teachercourse":
                infoquery_teachercourse(request, response);
                break;
            case "infoquery_studenttransaction":
                infoquery_studenttransaction(request, response);
                break;
        }
    }

    // Pack up HTTP Request
    // User Info
    protected void query_all_user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        UserDao user_dao = new UserDao();

        ArrayList<User> user_query_list = user_dao.queryUser();
        PrintWriter out = response.getWriter();

        if (user_query_list != null) {
            out.write("<div class='all'>");
            out.write("<div><span>用户名</span><span>密码</span><span>权限级别</span><span>工号</span></div>");
            for (User user_element : user_query_list) {
                out.write("<div>");
                out.write("<span>" + user_element.get_username() + "</span>");
                out.write("<span>" + user_element.get_password() + "</span>");
                out.write("<span>" + user_element.get_usertype() + "</span>");
                out.write("<span>" + user_element.get_foreignid() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void insert_user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String user_type_string = request.getParameter("usertype");
        String user_foreign_id = request.getParameter("foreignid");
        int user_type = Integer.parseInt(user_type_string);

        int flag = new UserDao().insertUser(username, password, user_type, user_foreign_id);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "用户插入成功！";
        } else {
            info = "错误：用户插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void delete_user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");

        int flag = new UserDao().deleteUser(username);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "成功删除名为" + username + "用户！";
        } else {
            info = "错误：删除用户失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void update_user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String user_type_string = request.getParameter("user_type");
        String foreign_id = request.getParameter("foreignid");

        int flag = new UserDao().updateUser(username, password, user_type_string, foreign_id);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "名为" + username + "用户信息修改成功！";
        } else {
            info = "错误：修改用户失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    // Campus Info
    protected void query_all_campus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        CampusDao campus_dao = new CampusDao();

        ArrayList<Campus> campus_list = campus_dao.queryCampus("", -1);
        PrintWriter out = response.getWriter();

        if (campus_list != null) {
            out.write("<div class='all'>");
            out.write("<div><span>ID</span><span>名称</span><span>地址</span></div>");
            for (Campus campus_element : campus_list) {
                out.write("<div>");
                out.write("<span>" + campus_element.get_id() + "</span>");
                out.write("<span>" + campus_element.get_name() + "</span>");
                out.write("<span>" + campus_element.get_address() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void insert_campus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        int flag = new CampusDao().insertCampus(id, name, address);
        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "校区插入成功！";
        } else {
            info = "错误：校区插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void delete_campus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");

        int flag = 0;
        if (!id.equals("")) {
            flag |= new CampusDao().deleteCampus(id, 0);
        } else if (!name.equals("")) {
            flag |= new CampusDao().deleteCampus(name, 1);
        } else if (!address.equals("")) {
            flag |= new CampusDao().deleteCampus(address, 2);
        }

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "校区删除成功！";
        } else {
            info = "错误：校区删除失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void update_campus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id_old = request.getParameter("id_old");
        String name_old = request.getParameter("name_old");

        String name_new = request.getParameter("name_new");
        String address_new = request.getParameter("address_new");

        int flag = 0;
        int old_type;
        String old_info;

        if (!id_old.equals("")) {
            old_type = 0;
            old_info = id_old;
        } else {
            old_type = 1;
            old_info = name_old;
        }

        if (!name_new.equals(""))
            flag |= new CampusDao().updateCampus(old_type, 1, old_info, name_new);
        if (!address_new.equals(""))
            flag |= new CampusDao().updateCampus(old_type, 2, old_info, address_new);

        String info;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "用户信息修改成功！";
        } else {
            info = "错误：修改用户失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    // Department Info
    protected void query_all_department(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        DepartmentDao department_dao = new DepartmentDao();

        ArrayList<Department> department_list = department_dao.queryDepartment("", -1);
        PrintWriter out = response.getWriter();

        if (department_list != null) {
            out.write("<div class='all'>");
            out.write("<div><span>ID</span><span>名称</span><span>地址</span><span>院长</span><span>所在校区ID</span></div>");
            for (Department department_element : department_list) {
                out.write("<div>");
                out.write("<span>" + department_element.get_id() + "</span>");
                out.write("<span>" + department_element.get_name() + "</span>");
                out.write("<span>" + department_element.get_address() + "</span>");
                out.write("<span>" + department_element.get_dean() + "</span>");
                out.write("<span>" + department_element.get_campus_id() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void insert_department(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String dean = request.getParameter("dean");
        String campus_id = request.getParameter("campus_id");

        int flag = new DepartmentDao().insertDepartment(id, name, address, dean, campus_id);

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "学院插入成功！";
        } else {
            info = "错误：学院插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void delete_department(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String campus_id = request.getParameter("campus_id");

        int flag = 0;
        if (!id.equals("")) {
            flag |= new DepartmentDao().deleteDepartment(id, 0);
        } else if (!name.equals("")) {
            flag |= new DepartmentDao().deleteDepartment(name, 1);
        } else if (!campus_id.equals("")) {
            flag |= new DepartmentDao().deleteDepartment(campus_id, 4);
        }

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "学院删除成功！";
        } else {
            info = "错误：学院删除失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void update_department(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id_old = request.getParameter("id_old");
        String name_old = request.getParameter("name_old");

        String name_new = request.getParameter("name_new");
        String address_new = request.getParameter("address_new");
        String dean_new = request.getParameter("dean_new");
        String campus_id_new = request.getParameter("campus_id_new");

        int flag = 0;
        int old_type = -1;
        String old_info = "";

        if (!id_old.equals("")) {
            old_type = 0;
            old_info = id_old;
        } else if (!name_old.equals("")) {
            old_type = 1;
            old_info = name_old;
        }

        if (!name_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type, 1, old_info, name_new);
        }
        if (!address_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type, 2, old_info, address_new);
        }
        if (!dean_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type, 3, old_info, dean_new);
        }
        if (!campus_id_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type, 4, old_info, campus_id_new);
        }

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "学院修改成功！";
        } else {
            info = "错误：学院修改失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    // Class Info
    protected void query_all_class(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        ClassDao class_dao = new ClassDao();

        ArrayList<Class> class_list = class_dao.queryClass("", -1);
        PrintWriter out = response.getWriter();

        if (class_list != null) {
            out.write("<div class='all'>");
            out.write(
                    "<div><span>ID</span><span>名称</span><span>建立日期</span><span>年级</span><span>学院编号</span><span>班主任工号</span></div>");

            for (Class class_element : class_list) {
                out.write("<div>");
                out.write("<span>" + class_element.get_id() + "</span>");
                out.write("<span>" + class_element.get_name() + "</span>");
                out.write("<span>" + class_element.get_date() + "</span>");
                out.write("<span>" + class_element.get_grade() + "</span>");
                out.write("<span>" + class_element.get_department_id() + "</span>");
                out.write("<span>" + class_element.get_head_teacher_id() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void insert_class(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String grade = request.getParameter("grade");
        String department_id = request.getParameter("department_id");
        String head_teacher_id = request.getParameter("head_teacher_id");

        int flag = new ClassDao().insertClass(id, name, date, grade, department_id, head_teacher_id);

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "班级插入成功！";
        } else {
            info = "错误：班级插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void delete_class(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");

        int flag = 0;
        if (!id.equals("")) {
            flag |= new ClassDao().deleteClass(id, 0);
        } else if (!name.equals("")) {
            flag |= new ClassDao().deleteClass(name, 1);
        }

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "班级删除成功！";
        } else {
            info = "错误：班级删除失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void update_class(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id_old = request.getParameter("id_old");
        String name_old = request.getParameter("name_old");

        String name_new = request.getParameter("name_new");
        String date_new = request.getParameter("date_new");
        String grade_new = request.getParameter("grade_new");
        String department_id_new = request.getParameter("department_id_new");
        String head_teacher_id_new = request.getParameter("head_teacher_id_new");

        int flag = 0;
        int old_type = -1;
        String old_info = "";

        if (!id_old.equals("")) {
            old_type = 0;
            old_info = id_old;
        } else if (!name_old.equals("")) {
            old_type = 1;
            old_info = name_old;
        }

        if (!name_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type, 1, old_info, name_new);
        }
        if (!date_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type, 2, old_info, date_new);
        }
        if (!grade_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type, 3, old_info, grade_new);
        }
        if (!department_id_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type, 4, old_info, department_id_new);
        }
        if (!head_teacher_id_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type, 5, old_info, head_teacher_id_new);
        }

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "班级修改成功！";
        } else {
            info = "错误：班级修改失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    // Student Info
    protected void query_all_student(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        StudentDao student_dao = new StudentDao();

        ArrayList<Student> student_list = student_dao.queryStudent("", -1);
        PrintWriter out = response.getWriter();

        if (student_list != null) {
            out.write("<div class='all'>");
            out.write(
                    "<div><span>ID</span><span>注册日期</span><span>email</span><span>班级ID</span><span>身份证号</span></div>");

            for (Student student_element : student_list) {
                out.write("<div>");
                out.write("<span>" + student_element.get_id() + "</span>");
                out.write("<span>" + student_element.get_enrollment_date() + "</span>");
                out.write("<span>" + student_element.get_email() + "</span>");
                out.write("<span>" + student_element.get_class_id() + "</span>");
                out.write("<span>" + student_element.get_id_card_number() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void insert_student(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String enrollment_date = request.getParameter("enrollment_date");
        String email = request.getParameter("email");
        String class_id = request.getParameter("class_id");
        String id_card_number = request.getParameter("id_card_number");
        String card_type_string = request.getParameter("card_type");
        Boolean card_type;
        if (Integer.parseInt(card_type_string) > 0)
            card_type = true;
        else
            card_type = false;
        String name = request.getParameter("name");
        String gender_string = request.getParameter("gender");
        Boolean gender;
        if (Integer.parseInt(gender_string) > 0)
            gender = true;
        else
            gender = false;
        String birthdate = request.getParameter("birthdate");
        String nationality = request.getParameter("nationality");
        String address = request.getParameter("address");
        String address_postal_code = request.getParameter("address_postal_code");
        String address_phone_number = request.getParameter("address_phone_number");

        int flag = new StudentDao().insertStudent(id, enrollment_date, email, class_id, //
                id_card_number, card_type, name, gender, birthdate, nationality, address, address_postal_code,
                address_phone_number);

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "学生插入成功！";
        } else {
            info = "错误：学生插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void delete_student(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String id_card_number = request.getParameter("id_card_number");

        int flag = 0;
        if (!id.equals("")) {
            flag |= new StudentDao().deleteStudent(id, 0);
        } else if (!id_card_number.equals("")) {
            flag |= new StudentDao().deleteStudent(id_card_number, 1);
        }

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "学生删除成功！";
        } else {
            info = "错误：学生删除失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void update_student(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id_old = request.getParameter("id_old");

        String strinfo1 = request.getParameter("strinfo1");
        String strinfo2 = request.getParameter("strinfo2");
        String strinfo3 = request.getParameter("strinfo3");

        int flag = 0;

        if (!strinfo1.equals(""))
            flag |= new StudentDao().updateStudent(id_old, 1, strinfo1);
        if (!strinfo2.equals(""))
            flag |= new StudentDao().updateStudent(id_old, 2, strinfo2);
        if (!strinfo3.equals(""))
            flag |= new StudentDao().updateStudent(id_old, 3, strinfo3);

        String info;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "用户信息修改成功！";
        } else {
            info = "错误：修改用户失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    // Teacher Info
    protected void query_all_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        TeacherDao teacher_dao = new TeacherDao();

        ArrayList<Teacher> teacher_list = teacher_dao.queryTeacher("", -1);
        PrintWriter out = response.getWriter();

        if (teacher_list != null) {
            out.write("<div class='all'>");
            out.write("<div><span>ID</span><span>注册日期</span><span>所在院系ID</span><span>职务</span><span>身份证号</span></div>");

            for (Teacher teacher_element : teacher_list) {
                out.write("<div>");
                out.write("<span>" + teacher_element.get_id() + "</span>");
                out.write("<span>" + teacher_element.get_enrollment_date() + "</span>");
                out.write("<span>" + teacher_element.get_department_id() + "</span>");
                out.write("<span>" + teacher_element.get_teacher_title() + "</span>");
                out.write("<span>" + teacher_element.get_id_card_number() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void insert_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String enrollment_date = request.getParameter("enrollment_date");
        String department_id = request.getParameter("department_id");
        String teacher_title = request.getParameter("teacher_title");
        String id_card_number = request.getParameter("id_card_number");
        String card_type_string = request.getParameter("card_type");
        Boolean card_type;
        if (Integer.parseInt(card_type_string) > 0)
            card_type = true;
        else
            card_type = false;
        String name = request.getParameter("name");
        String gender_string = request.getParameter("gender");
        Boolean gender;
        if (Integer.parseInt(gender_string) > 0)
            gender = true;
        else
            gender = false;
        String birthdate = request.getParameter("birthdate");
        String nationality = request.getParameter("nationality");
        String address = request.getParameter("address");
        String address_postal_code = request.getParameter("address_postal_code");
        String address_phone_number = request.getParameter("address_phone_number");

        int flag = new TeacherDao().insertTeacher(id, enrollment_date, department_id, teacher_title, //
                id_card_number, card_type, name, gender, birthdate, nationality, address, address_postal_code,
                address_phone_number);

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "教师插入成功！";
        } else {
            info = "错误：教师插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void delete_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String id_card_number = request.getParameter("id_card_number");

        int flag = 0;
        if (!id.equals("")) {
            flag |= new TeacherDao().deleteTeacher(id, 0);
        } else if (!id_card_number.equals("")) {
            flag |= new TeacherDao().deleteTeacher(id_card_number, 1);
        }

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "教师删除成功！";
        } else {
            info = "错误：教师删除失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void update_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id_old = request.getParameter("id_old");

        String strinfo1 = request.getParameter("strinfo1");
        String strinfo2 = request.getParameter("strinfo2");
        String strinfo3 = request.getParameter("strinfo3");

        int flag = 0;

        if (!strinfo1.equals(""))
            flag |= new TeacherDao().updateTeacher(id_old, 1, strinfo1);
        if (!strinfo2.equals(""))
            flag |= new TeacherDao().updateTeacher(id_old, 2, strinfo2);
        if (!strinfo3.equals(""))
            flag |= new TeacherDao().updateTeacher(id_old, 3, strinfo3);

        String info;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "用户信息修改成功！";
        } else {
            info = "错误：修改用户失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    // Course Info
    protected void query_all_course(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        CourseDao course_dao = new CourseDao();

        ArrayList<Course> course_list = course_dao.queryCourse("", -1);
        PrintWriter out = response.getWriter();

        if (course_list != null) {
            out.write("<div class='all'>");
            out.write("<div><span>ID</span><span>课程名</span><span>开课院系</span><span>任课老师工号</span><span>学期</span><span"
                    + ">开课年份</span><span>上课时间</span></div>");
            for (Course course_element : course_list) {
                out.write("<div>");
                out.write("<span>" + course_element.get_id() + "</span>");
                out.write("<span>" + course_element.get_name() + "</span>");
                out.write("<span>" + course_element.get_department_id() + "</span>");
                out.write("<span>" + course_element.get_teacher_id() + "</span>");
                out.write("<span>" + course_element.get_semester() + "</span>");
                out.write("<span>" + course_element.get_year() + "</span>");
                out.write("<span>" + course_element.get_time() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void insert_course(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String department_id = request.getParameter("department_id");
        String exam_type = request.getParameter("exam_type");

        int flag = new CourseDao().insertCourse(id, name, department_id, exam_type);

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "课程插入成功！";
        } else {
            info = "错误：课程插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void delete_course(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String department_id = request.getParameter("department_id");

        int flag = 0;
        if (!id.equals("")) {
            flag |= new CourseDao().deleteCourse(id, 0);
        } else if (!name.equals("")) {
            flag |= new CourseDao().deleteCourse(name, 1);
        } else if (!department_id.equals("")) {
            flag |= new CourseDao().deleteCourse(department_id, 2);
        }

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "课程删除成功！";
        } else {
            info = "错误：课程删除失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void update_course(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        String teacher_id = request.getParameter("teacher_id");
        String semester = request.getParameter("semester");
        String year = request.getParameter("year");
        String time = request.getParameter("time");

        int flag = new CourseDao().insertStartCourse(id, teacher_id, semester, year, time);

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "课程修改成功！";
        } else {
            info = "错误：课程修改失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    // Transaction Info
    protected void query_all_transaction(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void insert_transaction(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void delete_transaction(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void update_transaction(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    // CourseSelection Info Maintain
    protected void insert_selection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String student_id = request.getParameter("student_id");
        String course_id = request.getParameter("course_id");
        String date = request.getParameter("date");

        int flag = new SelectionDao().insertSelection(student_id, course_id, date);

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "选课成功！";
        } else {
            info = "错误：选课失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void delete_selection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String student_id = request.getParameter("student_id");
        String course_id = request.getParameter("course_id");

        int flag = 0;

        if (!student_id.equals("") && !course_id.equals(""))
            flag = new SelectionDao().deleteSelection(student_id, course_id, 0);
        else if (!student_id.equals(""))
            flag = new SelectionDao().deleteSelection(student_id, course_id, 2);
        else
            flag = new SelectionDao().deleteSelection(student_id, course_id, 1);

        String info = null;
        PrintWriter out = response.getWriter();
        if (flag == 1) {
            info = "成功删除！";
        } else {
            info = "错误：删除失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>" + info + "</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    // infoquery
    protected void infoquery_student(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
    }

    protected void infoquery_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
    }

    protected void infoquery_studentcourse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String student_id = request.getParameter("student_id");
        ArrayList<Course> course_list = new QueryToolKitsDao().queryCourseSelectedByStudentID(student_id);
        PrintWriter out = response.getWriter();

        if (course_list != null) {
            out.write("<div class='all'>");
            out.write("<div><span>ID</span><span>课程名</span><span>开课院系</span><span>任课老师工号</span><span>学期</span><span"
                    + ">开课年份</span><span>上课时间</span></div>");
            for (Course course_element : course_list) {
                out.write("<div>");
                out.write("<span>" + course_element.get_id() + "</span>");
                out.write("<span>" + course_element.get_name() + "</span>");
                out.write("<span>" + course_element.get_department_id() + "</span>");
                out.write("<span>" + course_element.get_teacher_id() + "</span>");
                out.write("<span>" + course_element.get_semester() + "</span>");
                out.write("<span>" + course_element.get_year() + "</span>");
                out.write("<span>" + course_element.get_time() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void infoquery_coursestudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String course_id = request.getParameter("course_id");
        ArrayList<Student> student_list = new QueryToolKitsDao().queryStudentSelectedByCourseID(course_id);
        PrintWriter out = response.getWriter();

        if (student_list != null) {
            out.write("<div class='all'>");
            out.write(
                    "<div><span>ID</span><span>注册日期</span><span>email</span><span>班级ID</span><span>身份证号</span></div>");

            for (Student student_element : student_list) {
                out.write("<div>");
                out.write("<span>" + student_element.get_id() + "</span>");
                out.write("<span>" + student_element.get_enrollment_date() + "</span>");
                out.write("<span>" + student_element.get_email() + "</span>");
                out.write("<span>" + student_element.get_class_id() + "</span>");
                out.write("<span>" + student_element.get_id_card_number() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void infoquery_teachercourse(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String teacher_id = request.getParameter("teacher_id");
        ArrayList<Course> course_list = new QueryToolKitsDao().queryCourseSelectedByTeacherID(teacher_id);
        PrintWriter out = response.getWriter();

        if (course_list != null) {
            out.write("<div class='all'>");
            out.write("<div><span>ID</span><span>课程名</span><span>开课院系</span><span>任课老师工号</span><span>学期</span><span"
                    + ">开课年份</span><span>上课时间</span></div>");
            for (Course course_element : course_list) {
                out.write("<div>");
                out.write("<span>" + course_element.get_id() + "</span>");
                out.write("<span>" + course_element.get_name() + "</span>");
                out.write("<span>" + course_element.get_department_id() + "</span>");
                out.write("<span>" + course_element.get_teacher_id() + "</span>");
                out.write("<span>" + course_element.get_semester() + "</span>");
                out.write("<span>" + course_element.get_year() + "</span>");
                out.write("<span>" + course_element.get_time() + "</span>");
                out.write("</div>");
            }
            out.write("</div>");
        }

        out.flush();
        out.close();
    }

    protected void infoquery_studenttransaction(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
    }
}

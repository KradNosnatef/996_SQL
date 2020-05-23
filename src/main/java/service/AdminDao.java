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
            //用户操作
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
        }
        else {
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
            flag |= new DepartmentDao().updateDepartment(old_type,1, old_info, name_new);
        }
        if (!address_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type,2, old_info, address_new);
        }
        if (!dean_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type,3, old_info, dean_new);
        }
        if (!campus_id_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type,4, old_info, campus_id_new);
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
            out.write("<div><span>ID</span><span>名称</span><span>建立日期</span><span>年级</span><span>学院编号</span><span>班主任工号</span></div>");

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
            flag |= new DepartmentDao().updateDepartment(old_type,1, old_info, name_new);
        }
        if (!date_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type,2, old_info, date_new);
        }
        if (!grade_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type,3, old_info, grade_new);
        }
        if (!department_id_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type,4, old_info, department_id_new);
        }
        if (!head_teacher_id_new.equals("")) {
            flag |= new DepartmentDao().updateDepartment(old_type,5, old_info, head_teacher_id_new);
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
        ClassDao class_dao = new ClassDao();

        ArrayList<Class> class_list = class_dao.queryClass("", -1);
        PrintWriter out = response.getWriter();

        if (class_list != null) {
            out.write("<div class='all'>");
            out.write("<div><span>ID</span><span>名称</span><span>建立日期</span><span>年级</span><span>学院编号</span><span>班主任工号</span></div>");

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

    protected void insert_student(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void delete_student(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void update_student(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    // Teacher Info
    protected void query_all_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void insert_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void delete_teacher(HttpServletRequest request, HttpServletRequest response) throws IOException {

    }

    protected void update_teacher(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    // Course Info
    protected void query_all_course(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        CourseDao course_dao = new CourseDao();

        ArrayList<Course> course_list = course_dao.queryCourse("", -1);
        PrintWriter out = response.getWriter();

        if (course_list != null) {
            out.write("<div class='all'>");
            out.write("<div><span>ID</span><span>课程名</span><span>开课院系</span><span>任课老师工号</span><span>学期</span><span" +
                              ">开课年份</span><span>上课时间</span></div>");
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

    }

    protected void delete_course(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    protected void update_course(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
}


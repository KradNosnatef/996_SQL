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
        }
    }

    // Pack up HTTP Requeest
    // Query All The Users
    protected void query_all_user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        UserDao userDao = new UserDao();

        ArrayList<User> user_query_list = userDao.queryUser();
        PrintWriter out = response.getWriter();

        if(user_query_list != null){
            out.write("<div class='all'>");
            out.write("<div><span>用户名</span><span>密码</span><span>权限级别</span><span>工号</span></div>");
            for(User user_element : user_query_list){
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

        int flag =  new UserDao().insertUser(username, password, user_type, user_foreign_id);
        String info = null;
        PrintWriter out =  response.getWriter();
        if(flag == 1){
            info = "用户插入成功！";
        }else{
            info = "错误：用户插入失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>"+info+"</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }

    protected void delete_user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String username = request.getParameter("username");

        int flag =  new UserDao().deleteUser(username);
        String info = null;
        PrintWriter out =  response.getWriter();
        if(flag == 1){
            info = "成功删除名为"+username+"用户！";
        }else{
            info = "错误：删除用户失败！";
        }
        out.write("<div class='error'>");
        out.write("<div>"+info+"</div>");
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

        int flag =  new UserDao().updateUser(username,password,user_type_string,foreign_id);
        String info = null;
        PrintWriter out =  response.getWriter();
        if(flag == 1){
            info = "名为"+username+"用户信息修改成功！";
        }else{
            info = "错误：修改用户失败!";
        }
        out.write("<div class='error'>");
        out.write("<div>"+info+"</div>");
        out.write("</div>");
        out.flush();
        out.close();
    }
}

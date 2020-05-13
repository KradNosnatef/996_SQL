package service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import model.User;

public class LoginServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Post Request
        request.setCharacterEncoding("utf-8");
        // Fetch data (username & password)
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDao userdao = new UserDao();
        User user = userdao.login(username, password);

        if (user != null) {
            int usertype = user.get_usertype();
            if (usertype == 0) {
                // admin user login
                request.getSession().setAttribute("admin",user);
                request.getRequestDispatcher("admin.jsp").forward(request, response);
            } else if (usertype == 1) {
                // teacher user login
                request.getSession().setAttribute("teacher", user);
                request.getRequestDispatcher("teacher.jsp").forward(request, response);
            } else {
                // student user login
                request.getSession().setAttribute("student", user);
                request.getRequestDispatcher("student.jsp").forward(request, response);
            }
        } else  {
            request.setAttribute("info"," 错误:用户名或密码错误！");
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
    }
}

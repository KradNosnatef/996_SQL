<%--
  Created by IntelliJ IDEA.
  User: chivier
  Date: 5/5/20
  Time: 5:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page import="model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>管理员操作界面</title>
    <link rel="stylesheet" type="text/css" href="css/user&admin.css">
    <link rel="icon" type="image/x-ico" href="images/stu.ico">
</head>

<body>
<%
    //获取登录成功的用户信息
    User user = (User) session.getAttribute("admin");
    //判断用户是否登录
    if (user != null) {
        String user_type_string;
        if (user.get_usertype() == 0) {
            user_type_string = "管理员";
        } else if (user.get_usertype() == 1) {
            user_type_string = "教师";
        } else {
            user_type_string = "学生";
        }
%>
<header>
    <div class="title">
        <span>管理员操作界面</span>
    </div>
    <nav>
        <div class="userinfo">
            <ul>
                <li><%=user.get_username() %>
                </li>
                <li><%=user_type_string %>
                </li>
                <li><a href="UserExitServlet">退出登录</a></li>
                <li><a href="login.html">返回首页</a></li>
            </ul>
        </div>
    </nav>
</header>

<main>
    <%
        } else {
            response.sendRedirect("login.html");
        }
    %>
    <div class="container">
        <div class="select">
            <h3>请选择操作</h3>
            <ul id="accordion" class="accordion">
                <li>
                    <div id="user-info" class="link">用户信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="queryRequest('user')">查看所有用户</a></li>
                        <li><a onclick="showInsertUser()">新增用户信息</a></li>
                        <li><a onclick="showDeleteUser()">删除指定用户</a></li>
                        <li><a onclick="showUpdateUser()">修改用户信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">校区信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="queryRequest('campus')">查看所有校区</a></li>
                        <li><a onclick="showInsertCampus()">新增校区信息</a></li>
                        <li><a onclick="showDeleteCampus()">删除指定校区</a></li>
                        <li><a onclick="showUpdateCampus()">修改校区信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">院系信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="queryRequest('department')">查看所有院系</a></li>
                        <li><a onclick="showInsertDepartment()">新增院系信息</a></li>
                        <li><a onclick="showDeleteDepartment()">删除指定院系</a></li>
                        <li><a onclick="showUpdateDepartment()">修改院系信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">班级信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="queryRequest('class')">查看所有班级</a></li>
                        <li><a onclick="showInsertClass()">新增班级信息</a></li>
                        <li><a onclick="showDeleteClass()">删除指定班级</a></li>
                        <li><a onclick="showUpdateClass()">修改班级信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">学生信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="queryRequest('student')">查看所有学生</a></li>
                        <li><a onclick="showInsertStudent()">新增学生信息</a></li>
                        <li><a onclick="showDeleteStudent()">删除指定学生</a></li>
                        <li><a onclick="showUpdateStudent()">修改学生信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">教师信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="queryRequest('teacher')">查看所有教师</a></li>
                        <li><a onclick="showInsertTeacher()">新增教师信息</a></li>
                        <li><a onclick="showDeleteTeacher()">删除指定教师</a></li>
                        <li><a onclick="showUpdateTeacher()">修改教师信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">课程信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="queryRequest('course')">查看所有课程</a></li>
                        <li><a onclick="showInsertCourse()">新增课程信息</a></li>
                        <li><a onclick="showDeleteCourse()">删除课程信息</a></li>
                        <li><a onclick="showUpdateCourse()">修改课程信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">异动信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="queryRequest('transaction')">查看所有异动</a></li>
                        <li><a onclick="showInsertCourse()">新增异动信息</a></li>
                        <li><a onclick="showDeleteCourse()">删除异动信息</a></li>
                        <li><a onclick="showUpdateCourse()">修改异动信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">选课信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="showInsertSelection()">新增选课信息</a></li>
                        <li><a onclick="showDeleteSelection()">删除选课信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">个人基本信息管理</div>
                    <ul class="submenu">
                        <li><a onclick="showUpdatePerson()">修改个人信息</a></li>
                    </ul>
                </li>
                <li>
                    <div class="link">普通查询</div>
                    <ul class="submenu">
                        <li><a onclick="infoQurey('student')">指定学生查询</a></li>
                        <li><a onclick="infoQurey('teacher')">指定教师查询</a></li>
                        <li><a onclick="infoQurey('course')">指定课程查询</a></li>
                        <li><a onclick="infoQurey('transaction')">指定异动查询</a></li>
                    </ul>
                </li>

                <li>
                    <div class="link">组合信息查询</div>
                    <ul class="submenu">
                        <li><a onclick="infoQurey('student_course')">指定学生查询课程</a></li>
                        <li><a onclick="infoQurey('course_student')">指定课程查询学生</a></li>
                        <li><a onclick="infoQurey('teacher_course')">指定教师查询课程</a></li>
                        <li><a onclick="infoQurey('student_transaction')">指定学生查询异动</a></li>
                    </ul>
                </li>
            </ul>
        </div>

        <div id="result" class="result">
            <p class="welcome">欢迎使用学生信息管理系统！</p>
        </div>
    </div>
    </div>
</main>

<footer>
    <div class="copyright">
        &copy; Copyright. All rights reserved. Design by <a href="http://www.github.com/Chivier/">Chivier @ USTC</a>
    </div>
</footer>

<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/admin.js"></script>
</body>
</html>
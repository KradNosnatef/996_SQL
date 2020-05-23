package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Campus;
import utils.DButils;
import utils.UnitTestSwitch;
import model.Department;
import model.Course;

// ACCESS SETTINGS:
// - insertCourse: P100
// - deleteCourse: P100
// - queryCourse : P111
// - updateCourse: P100
// - insertStartCourse: P110
// - deleteStartCourse: P110
// - queryStartCourse : P111
// - updateStartCourse: P110

public class CourseDao {
    // Insert new course element
    // Input:
    // - id: course id
    // - name: course name
    // - department_id: foreiggn key from Department.Department_ID
    // - exam_type: course exam type
    // Ouput:
    // - If insertion action succeed, return 1
    // - If insertion action is illegal, return -1
    // - If insertion action failed, return 0
    public int insertCourse(String id, String name, String department_id, String exam_type){
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String department_check = "SELECT * FROM Department WHERE Department_ID = ?;";
        String sql = "INSERT INTO Course (Course_ID, Course_Name, Course_Department_ID, Course_Teacher_ID, " +
                "Course_Exam_Type, Course_Semester, Course_Year, Course_Time) VALUES (?,?,?,?,?,?,?,?);";

        int insert_flag = 0;

        // Check if department_id existed
        try {
            PreparedStatement ps = conn.prepareStatement(department_check);
            ps.setString(1, department_id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return -1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, department_id);
            ps.setString(4, null);
            ps.setString(5, exam_type);
            ps.setString(6, null);
            ps.setString(7, null);
            ps.setString(8, null);
            insert_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return insert_flag;
    }

    // Delete a course
    // Input:
    // - element_selector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 0 -- delete by id
    //   type = 1 -- delete by name
    //   type = 2 -- delete by department_id
    //   type = 3 -- delete by exam_type (ILLEGAL)
    // Ouput:
    // - return 0 if the element is not existed
    // - return 1 if we delete the element successfully
    // - return -1 if our instruction is illegal
    public int deleteCourse(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql = "DELETE FROM Course ";
        String start_course_check = "SELECT * FROM Course ";

        String course_selector;

        int delete_flag = 0;

        if (type == 0) {
            course_selector = "WHERE Course_ID = ?;";
        } else if (type == 1) {
            course_selector = "WHERE Course_Name = ?;";
        } else if (type == 2) {
            course_selector = "WHERE Course_Department_ID = ?;";
        } else {
            return -1;
        }

        // Check whether the course is started
        try {
            start_course_check = start_course_check + course_selector;
            PreparedStatement ps = conn.prepareStatement(start_course_check);
            ps.setString(1,element_selector);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getString("Course_Teacher_ID") != null ||
                        rs.getString("Course_Semester") != null ||
                        rs.getString("Course_Year") != null ||
                        rs.getString("Course_Time") != null) {
                    return -1;
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            if (!UnitTestSwitch.SWITCH) {
                sqle.printStackTrace();
            }
        }

        try {
            sql = sql + course_selector;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, element_selector);
            delete_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return delete_flag;
    }

    // Query course information
    // Input:
    // - element_selector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 0 -- select by id
    //   type = 1 -- select by name
    //   type = 2 -- select by department_id
    //   type = 3 -- select by exam_type
    //   type = -1 -- selecat all elements
    // Ouput:
    // - return an array list for your query.
    public ArrayList<Course> queryCourse(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql;
        ArrayList<Course> course_list = new ArrayList<>();
        int query_flag = 0;
        switch (type) {
            case 0:
                sql = "SELECT * FROM Course WHERE Course_ID = ? ORDER BY Course_ID;";
                break;
            case 1:
                sql = "SELECT * FROM Course WHERE Course_Name = ? ORDER BY Course_ID;";
                break;
            case 2:
                sql = "SELECT * FROM Course WHERE Course_Department_ID = ? ORDER BY Course_ID;";
                break;
            case 3:
                sql = "SELECT * FROM Course WHERE Course_Exam_Type = ? ORDER BY Course_ID;";
                break;

            default:
                sql = "SELECT * FROM Course ORDER BY Course_ID;";
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (type != -1)
                ps.setString(1, element_selector);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course_element = new Course();
                course_element.set_id(rs.getString("Course_ID"));
                course_element.set_name(rs.getString("Course_Name"));
                course_element.set_department_id(rs.getString("Course_Department_ID"));
                course_element.set_exam_type(rs.getString("Course_Exam_Type"));
                course_element.set_teacher_id(rs.getString("Course_Teacher_ID"));
                course_element.set_semester(rs.getString("Course_Semester"));
                course_element.set_year(rs.getString("Course_Year"));
                course_element.set_time(rs.getString("Course_Time"));
                course_list.add(course_element);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return course_list;
    }

    // Modify course information
    // - old & new type: type is used to select the meaning of old_value & new_value
    //   type = 0 -- update id (if new_type == 0m ILLEGAL)
    //   type = 1 -- update name (if new_type == 1 ILLEGAL)
    //   type = 2 -- update department_id (check if existed before update)
    //   type = 3 -- update exam_type
    // Ouput:
    // - return 0 if element is not existed
    // - return 1 if update action succeed
    // - return -1 if type is illegal
    public int updateCourse(int old_type, int new_type, String old_value, String new_value) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql;
        String sql_select_old;
        String sql_update_new;
        int query_flag = 0;

        switch (old_type) {
            case 0:
                sql_select_old = " WHERE Course_ID = ?;";
                break;
            case 1:
                sql_select_old = " WHERE Course_Name = ?;";
                break;
            case 2:
                sql_select_old = " WHERE Course_Department_ID = ?;";
                break;
            case 3:
                sql_select_old = " WHERE Course_Exam_Type = ?;";
                break;

            default:
                return -1;
        }

        switch (new_type) {
            case 2:
                sql_update_new = "UPDATE Course SET Course_Department_ID = ?";
                break;
            case 3:
                sql_update_new = "UPDATE Course SET Course_Exam_Type = ?";
                break;

            default:
                return -1;
        }

        // Check if new department_id exists
        if (new_type == 2) {
            String department_foreign_selector = "SELECT * FROM Department WHERE Department_ID = ?;";
            try {
                PreparedStatement ps = conn.prepareStatement(department_foreign_selector);
                ps.setString(1, new_value);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    rs.close();
                    ps.close();
                    return -1;
                }
                rs.close();
                ps.close();
            } catch (SQLException sqle) {
                if (!UnitTestSwitch.SWITCH) {
                    sqle.printStackTrace();
                }
            }
        }

        sql = sql_update_new + sql_select_old;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, new_value);
            ps.setString(2, old_value);
            query_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return query_flag;
    }

    // Insert new start course information
    // Input:
    // - id: course id
    // - name: course name
    // - department_id: foreiggn key from Department.Department_ID
    // - exam_type: course exam type
    // Ouput:
    // - If insertion action succeed, return 1
    // - If insertion action is illegal, return -1
    // - If insertion action failed, return 0
    public int insertStartCourse(String id, String teacher_id, String semester, String year, String time){
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String teacher_check = "SELECT * FROM Teacher WHERE Teacher_ID = ?;";
        String sql = "UPDATE Course SET  Course_Teacher_ID = ?, Course_Semester = ?, Course_Year = ?, Course_Time = ? WHERE  Course_ID = ?;";
        int insert_flag = 0;

        // Chech if teacher_id existed
        try {
            PreparedStatement ps = conn.prepareStatement(teacher_check);
            ps.setString(1, teacher_id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return -1;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return -1;
        }

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, teacher_id);
            ps.setString(2, semester);
            ps.setString(3, year);
            ps.setString(4, time);
            ps.setString(5, id);
            insert_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return insert_flag;
    }

    // Delete a start course information
    // Input:
    // - element_selector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 0 -- delete by id
    //   type = 1 -- delete by name
    //   type = 2 -- delete by department_id
    //   type = 3 -- delete by exam_type (ILLEGAL)
    //   type = 4 -- delete by teacher_id
    //   type = 5 -- delete by semester
    //   type = 6 -- delete by year
    //   type = 7 -- delete by time
    // Ouput:
    // - return 0 if the element is not existed
    // - return 1 if we delete the element successfully
    // - return -1 if our instruction is illegal
    public int deleteStartCourse(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql = "UPDATE Course SET Course_Teacher_ID = null, Course_Semester = null, Course_Year = null," +
                " Course_Time = null ";
        String course_selection_check = "SELECT * FROM Course, CourseSelection ";

        String course_selector;
        String course_foreign_selector_course_selection;

        int delete_flag = 0;

        if (type == 0) {
            course_foreign_selector_course_selection  = "WHERE Course.Course_ID = ? " +
                    "AND Course.Course_ID = CourseSelection.CourseSelection_Course_ID;";
            course_selector = "WHERE Course_ID = ?;";
        } else if (type == 1) {
            course_foreign_selector_course_selection  = "WHERE Course.Course_Name = ? " +
                    "AND Course.Course_ID = CourseSelection.CourseSelection_Course_ID;";
            course_selector = "WHERE Course_Name = ?;";
        } else if (type == 2) {
            course_foreign_selector_course_selection  = "WHERE Course.Course_Department_ID = ? " +
                    "AND Course.Course_ID = CourseSelection.CourseSelection_Course_ID;";
            course_selector = "WHERE Course_Department_ID = ?;";
        } else if (type == 4) {
            course_foreign_selector_course_selection  = "WHERE Course.Course_Teacher_ID = ? " +
                    "AND Course.Course_ID = CourseSelection.CourseSelection_Course_ID;";
            course_selector = "WHERE Course_Teacher_ID = ?;";
        } else if (type == 5) {
            course_foreign_selector_course_selection  = "WHERE Course.Course_Semester = ? " +
                    "AND Course.Course_ID = CourseSelection.CourseSelection_Course_ID;";
            course_selector = "WHERE Course_Semester = ?;";
        } else if (type == 6) {
            course_foreign_selector_course_selection  = "WHERE Course.Course_Year = ? " +
                    "AND Course.Course_ID = CourseSelection.CourseSelection_Course_ID;";
            course_selector = "WHERE Course_Year = ?;";
        } else if (type == 7) {
            course_foreign_selector_course_selection  = "WHERE Course.Course_Time = ? " +
                    "AND Course.Course_ID = CourseSelection.CourseSelection_Course_ID;";
            course_selector = "WHERE Course_Time = ?;";
        } else {
            return -1;
        }

        // Check whether the course is selected
        try {
            course_selection_check = course_selection_check + course_foreign_selector_course_selection;;
            PreparedStatement ps = conn.prepareStatement(course_selection_check);
            ps.setString(1,element_selector);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return  -1;
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            if (!UnitTestSwitch.SWITCH) {
                sqle.printStackTrace();
            }
        }

        try {
            sql = sql + course_selector;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, element_selector);
            delete_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return delete_flag;
    }

    // Query start course information
    // Input:
    // - element_selector: a string used to select element
    // - type: type is used to select the meaning of element_selector
    //   type = 0 -- select by id
    //   type = 1 -- select by name
    //   type = 2 -- select by teacher_id
    //   type = 3 -- select by semester
    //   type = 4 -- select by year
    //   type = 5 -- select by time
    //   type = -1 -- selecat all elements
    // Ouput:
    // - return an array list for your query.
    public ArrayList<Course> queryStartCourse(String element_selector, int type) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql;
        ArrayList<Course> course_list = new ArrayList<>();
        int query_flag = 0;
        switch (type) {
            case 0:
                sql = "SELECT * FROM Course WHERE Course_ID = ? ORDER BY Course_ID;";
                break;
            case 1:
                sql = "SELECT * FROM Course WHERE Course_Name = ? ORDER BY Course_ID;";
                break;
            case 2:
                sql = "SELECT * FROM Course WHERE Course_Teacher_ID = ? ORDER BY Course_ID;";
                break;
            case 3:
                sql = "SELECT * FROM Course WHERE Course_Semester = ? ORDER BY Course_ID;";
                break;
            case 4:
                sql = "SELECT * FROM Course WHERE Course_Year = ? ORDER BY Course_ID;";
                break;
            case 5:
                sql = "SELECT * FROM Course WHERE Course_Time = ? ORDER BY Course_ID;";
                break;

            default:
                sql = "SELECT * FROM Course ORDER BY Course_ID;";
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            if (type != -1)
                ps.setString(1, element_selector);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course_element = new Course();
                course_element.set_id(rs.getString("Course_ID"));
                course_element.set_name(rs.getString("Course_Name"));
                course_element.set_department_id(rs.getString("Course_Department_ID"));
                course_element.set_exam_type(rs.getString("Course_Exam_Type"));
                course_element.set_teacher_id(rs.getString("Course_Teacher_ID"));
                course_element.set_semester(rs.getString("Course_Semester"));
                course_element.set_year(rs.getString("Course_Year"));
                course_element.set_time(rs.getString("Course_Time"));
                course_list.add(course_element);
            }
            rs.close();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return course_list;
    }

    // Modify start course information
    // - old & new type: type is used to select the meaning of old_value & new_value
    //   type = 0 -- update id (if new_type == 0m ILLEGAL)
    //   type = 1 -- update name (if new_type == 1 ILLEGAL)
    //   type = 2 -- update teacher_id (check if existed before update)
    //   type = 3 -- update semester
    //   type = 4 -- update year
    //   type = 5 -- update time
    // Ouput:
    // - return 0 if element is not existed
    // - return 1 if update action succeed
    // - return -1 if type is illegal
    public int updateStartCourse(int old_type, int new_type, String old_value, String new_value) {
        Connection conn;
        if (UnitTestSwitch.SWITCH) conn = DButils.getConnectionUnitTest();
        else conn = DButils.getConnection();
        String sql;
        String sql_select_old;
        String sql_update_new;
        int query_flag = 0;

        switch (old_type) {
            case 0:
                sql_select_old = " WHERE Course_ID = ?;";
                break;
            case 1:
                sql_select_old = " WHERE Course_Name = ?;";
                break;
            case 2:
                sql_select_old = " WHERE Course_Teacher_ID = ?;";
                break;
            case 3:
                sql_select_old = " WHERE Course_Semester = ?;";
                break;
            case 4:
                sql_select_old = " WHERE Course_Year = ?;";
                break;
            case 5:
                sql_select_old = " WHERE Course_Time = ?;";
                break;

            default:
                return -1;
        }

        switch (new_type) {
            case 2:
                sql_update_new = "UPDATE Course SET Course_Teacher_ID = ?";
                break;
            case 3:
                sql_update_new = "UPDATE Course SET Course_Semester = ?";
                break;
            case 4:
                sql_update_new = "UPDATE Course SET Course_Year = ?";
                break;
            case 5:
                sql_update_new = "UPDATE Course SET Course_Time = ?";
                break;

            default:
                return -1;
        }

        // Chech if new teacher_id exists
        if (new_type == 2) {
            String teacher_foreign_selector = "SELECT * FROM Teacher WHERE Teacher_ID = ?;";
            try {
                PreparedStatement ps = conn.prepareStatement(teacher_foreign_selector);
                ps.setString(1, new_value);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    rs.close();
                    ps.close();
                    return -1;
                }
                rs.close();
                ps.close();
            } catch (SQLException sqle) {
                if (!UnitTestSwitch.SWITCH) {
                    sqle.printStackTrace();
                }
            }
        }

        sql = sql_update_new + sql_select_old;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, new_value);
            ps.setString(2, old_value);
            query_flag = ps.executeUpdate();
            ps.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            DButils.closeConnection(conn);
        }
        return query_flag;
    }
}

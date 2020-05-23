package model;

public class CourseSelection {
    private static final long serialVersionUID = 1L;
    private int courseSelectionID;
    private String courseSelectionCourseID;
    private String courseSelectionStudentID;
    private String courseSelectionDate;
    private String courseSelectionSemester;
    private int score;

    public void setCourseSelectionCourseID(String courseSelectionCourseID) {
        this.courseSelectionCourseID = courseSelectionCourseID;
    }
    public String getCourseSelectionCourseID() {
        return courseSelectionCourseID;
    }

    public void setCourseSelectionDate(String courseSelectionDate) {
        this.courseSelectionDate = courseSelectionDate;
    }
    public String getCourseSelectionDate() {
        return courseSelectionDate;
    }

    public void setCourseSelectionID(int courseSelectionID) {
        this.courseSelectionID = courseSelectionID;
    }
    public int getCourseSelectionID() {
        return courseSelectionID;
    }

    public void setCourseSelectionSemester(String courseSelectionSemester) {
        this.courseSelectionSemester = courseSelectionSemester;
    }
    public String getCourseSelectionSemester() {
        return courseSelectionSemester;
    }

    public void setCourseSelectionStudentID(String courseSelectionStudentID) {
        this.courseSelectionStudentID = courseSelectionStudentID;
    }
    public String getCourseSelectionStudentID() {
        return courseSelectionStudentID;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }
}

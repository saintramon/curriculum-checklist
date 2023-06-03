/**
 * @author Jasmin, Ramon Emmiel
 * @author Domingo, Diamond Darrel
 * @author Rabang, Gebreyl Isaac
 * @author Baltazar, Rudyard Lans
 * @author Urbiztondo, Karl Jasper
 * @author Bumanglag, AU
 */

public class Course {
    private int yearLevel;
    private int term;
    private String courseNumber;
    private String courseTitle;
    private double courseUnits;
    private String courseGrade;
    private boolean isCourseTaken;
    private boolean isBeingTaken;

    public Course(){
        this.yearLevel = 0;
        this.term = 0;
        this.courseNumber = "CS 000";
        this.courseTitle = "COMPUTER SCIENCE";
        this.courseUnits = 0.0;
        this.courseGrade = "Not Yet Taken";
        this.isCourseTaken = false;
        this.isBeingTaken = false;
    }

    public Course(int yearLevel, int term, String courseNumber, String courseTitle, double courseUnits){
        this.yearLevel = yearLevel;
        this.term = term;
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
        this.courseUnits = courseUnits;
        this.courseGrade = "Not Yet Taken";
        this.isCourseTaken = false;
        this.isBeingTaken = false;
    }

    public Course(int yearLevel, int term, String courseNumber, String courseTitle, double courseUnits, String courseGrade, boolean isCourseTaken, boolean isBeingTaken){
        this.yearLevel = yearLevel;
        this.term = term;
        this.courseNumber = courseNumber;
        this.courseTitle = courseTitle;
        this.courseUnits = courseUnits;
        this.courseGrade = courseGrade;
        this.isCourseTaken = isCourseTaken;
        this.isBeingTaken = isBeingTaken;
    }

    public int getYearLevel() {
        return yearLevel;
    }
    public int getTerm() {
        return term;
    }
    public String getCourseNumber() {
        return courseNumber;
    }
    public String getCourseTitle() {
        return courseTitle;
    }
    public double getCourseUnits() {
        return courseUnits;
    }
    public String getCourseGrade() {
        return courseGrade;
    }
    public boolean isBeingTaken() {
        return isBeingTaken;
    }
    public boolean isCourseTaken() {
        return isCourseTaken;
    }

    public void setYearLevel(int yearLevel){
        this.yearLevel = yearLevel;
    }
    public void setTerm(int term) {
        this.term = term;
    }
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
    public void setCourseUnits(double courseUnits) {
        this.courseUnits = courseUnits;
    }
    public void setIsBeingTaken(boolean isBeingTaken) {
        this.isBeingTaken = isBeingTaken;
    }
    public void setIsCourseTaken(boolean isCourseTaken){
        this.isCourseTaken = isCourseTaken;
    }
    public void setCourseGrade(String courseGrade) {
        this.courseGrade = courseGrade;
    }


    public String toString(){
        String result="";
        result = yearLevel+","+term+","+courseNumber+","+courseTitle+","+courseUnits+","+courseGrade+","+isCourseTaken+","+isBeingTaken;
        return result;
    }
}

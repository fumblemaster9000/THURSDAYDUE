package university;

public class Course {
    private String courseName; //data attributes are good
    private String courseCode;
    private String subjectName;
    private String sectionNumber;
    private String teacherName;
    private int capacity;
    private String lectureTime;
    private String finalExamDateTime;
    private String location;

    public Course(String courseName, String courseCode, String subjectName, String sectionNumber, int capacity, String lectureTime, String finalExamDateTime, String location, String teacherName) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.subjectName = subjectName;
        this.sectionNumber = sectionNumber;
        this.teacherName = teacherName;
        this.capacity = capacity;
        this.lectureTime = lectureTime;
        this.finalExamDateTime = finalExamDateTime; //constructor
        this.location = location;
    }

    // Getters and setters for all attributes
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(String sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getFinalExamDateTime() {
        return finalExamDateTime;
    }

    public void setFinalExamDateTime(String finalExamDateTime) {
        this.finalExamDateTime = finalExamDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Course [Name=" + courseName + ", Code=" + courseCode + ", Subject=" + subjectName +
                ", Section=" + sectionNumber + ", Teacher=" + teacherName + ", Capacity=" + capacity +
                ", Lecture Time=" + lectureTime + ", Final Exam=" + finalExamDateTime + ", Location=" + location + "]";
    }
}

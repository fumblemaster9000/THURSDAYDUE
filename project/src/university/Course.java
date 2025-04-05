package university;

public class Course {
    private String courseCode; // Unique course code
    private String courseName; // Name of the course
    private String subjectCode; // Subject code associated with the course
    private String sectionNumber; // Section number for the course
    private String teacherName; // Name of the teacher/instructor
    private int capacity; // Maximum number of students allowed in the course
    private String lectureTime; // Scheduled time for lectures
    private String finalExamDateTime; // Scheduled date and time for the final exam
    private String location; // Location of the course lectures or final exam

    // Constructor to initialize a Course object with all attributes provided.
    public Course(String courseCode, String courseName, String subjectCode, String sectionNumber, int capacity, String lectureTime, String finalExamDateTime, String location, String teacherName) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjectCode = subjectCode;
        this.sectionNumber = sectionNumber;
        this.teacherName = teacherName;
        this.capacity = capacity;
        this.lectureTime = lectureTime;
        this.finalExamDateTime = finalExamDateTime; //constructor
        this.location = location;
    }

    // Getters and setters for all attributes
    public String getCourseName() {return courseName;}
    public void setCourseName(String courseName) {this.courseName = courseName;}

    public String getCourseCode() {return courseCode;}
    public void setCourseCode(String courseCode) {this.courseCode = courseCode;}

    public String getSubjectCode() {return subjectCode;}
    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public String getSectionNumber() {return sectionNumber;}
    public void setSectionNumber(String sectionNumber) {this.sectionNumber = sectionNumber;}

    public String getTeacherName() {return teacherName;}
    public void setTeacherName(String teacherName) {this.teacherName = teacherName;}

    public int getCapacity() {return capacity;}
    public void setCapacity(int capacity) {this.capacity = capacity;}

    public String getLectureTime() {return lectureTime;}
    public void setLectureTime(String lectureTime) {this.lectureTime = lectureTime;}

    public String getFinalExamDateTime() {return finalExamDateTime;}
    public void setFinalExamDateTime(String finalExamDateTime) {this.finalExamDateTime = finalExamDateTime;}

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    @Override
    public String toString() {
        return "Course [Code=" + courseCode + ", Name=" + courseName +  ", Subject=" + subjectCode +
                ", Section=" + sectionNumber + ", Teacher=" + teacherName + ", Capacity=" + capacity +
                ", Lecture Time=" + lectureTime + ", Final Exam=" + finalExamDateTime + ", Location=" + location + "]";
    }
}

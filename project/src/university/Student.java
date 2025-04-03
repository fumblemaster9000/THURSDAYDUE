package university;

import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User {
    private String address;
    private String Telephone;
    private String AcademicLevel;
    private String CurrentSemester; // Converted from int to String
    private String ProfilePhoto;
    private String subjectsregistered; // Converted from ArrayList to String
    private String thesistitle;
    private String progress;
    private String tuition; // Converted from double to String
    private String Grades;  // Already String
    private String Rolledcourses;

    // Constructor with all fields as Strings
    public Student(String userId, String name, String address, String Telephone, String email,
            String AcademicLevel, String CurrentSemester, String ProfilePhoto,
            String subjectsregistered, String thesistitle, String progress,
            String password, String tuition, String Grades, String Rolledcourses) {

        super(userId, password, name, email); // Matches superclass fields
        this.address = address;
        this.Telephone = Telephone;
        this.AcademicLevel = AcademicLevel;
        this.CurrentSemester = CurrentSemester;
        this.ProfilePhoto = ProfilePhoto;
        this.subjectsregistered = subjectsregistered;
        this.thesistitle = thesistitle;
        this.progress = progress;
        this.tuition = tuition;
        this.Grades = Grades;
        this.Rolledcourses = Rolledcourses;
    }

    // Getters
    public String getname(){return super.getName();}

    public String getpassword(){
        return super.getPassword();
    }

    public String getStudentID() {
        return super.getUserId();
    }

    public String getemail() {
        return super.getEmail();
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return Telephone;
    }

    public String getAcademicLevel() {
        return AcademicLevel;
    }

    public String getCurrentSemester() {
        return CurrentSemester;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public String getSubjectsRegistered() {
        return subjectsregistered;
    }

    public String getThesisTitle() {
        return thesistitle;
    }

    public String getProgress() {
        return progress;
    }

    public String getTuition() {
        return tuition;
    }

    public String getGrades() {
        return Grades;
    }

    public String getRolledCourses(){
        return Rolledcourses;
    }

    //SETTERS
    public void setRolledcourses(String Rolledcourses){
        this.Rolledcourses = Rolledcourses;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }

    public void setAcademicLevel(String AcademicLevel) {
        this.AcademicLevel = AcademicLevel;
    }

    public void setCurrentSemester(String CurrentSemester) {
        this.CurrentSemester = CurrentSemester;
    }

    public void setProfilePhoto(String ProfilePhoto) {
        this.ProfilePhoto = ProfilePhoto;
    }

    public void setSubjectsRegistered(String subjectsregistered) {
        this.subjectsregistered = subjectsregistered;
    }

    public void setThesisTitle(String thesistitle) {
        this.thesistitle = thesistitle;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setTuition(String tuition) {
        this.tuition = tuition;
    }

    public void setGrades(String Grades) {
        this.Grades = Grades;
    }

    public void setID (String ID){
        super.setUserId(ID);
    }

    public void setPassword (String password){
        super.setPassword(password);
    }

    public void setStudentName (String name){
        super.setName(name);
    }

    public void setStudentEmail (String email){
        super.setEmail(email);
    }


    @Override
    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        testmethod(); //testing student manager methods
        while (true) {
            System.out.println("Student Options for " + name + ":");
            System.out.println("1. View all Subjects");
            System.out.println("2. View Faculty Profiles");
            System.out.println("3. View Courses that you are enrolled in"); //works fine
            System.out.println("4. View Events (Including Registered)"); //students already registered will get not registered message
            System.out.println("5. Register for Events");
            System.out.println("6. Exit");//works
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    SubjectManager subjectManager = new SubjectManager();
                    subjectManager.viewSubjects();
                    break;
                case 2:
                    StudentManager studentManager = new StudentManager();
                    studentManager.viewFacultyProfiles(this);
                    break;
                case 3:
                    CourseManager courseManager = new CourseManager();
                    courseManager.viewCourses(); //works fine displaying all details
                    break;
                case 4:
                    viewEventsIncludingRegistered();
                    break;

                case 5:
                    EventManager eventManager = new EventManager();
                    System.out.println("Enter event code:");
                    String eventCode = scanner.nextLine();
                    eventManager.manageRegistrations(eventCode, userId, name, "TextData/Student.txt"); //supposedly adds events
                    break;

                case 6:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void testmethod(){
        StudentManager studentManager = new StudentManager();
//        studentManager.viewprofile(this);
//        studentManager.viewenrolledcourses(subjectsregistered);
//        studentManager.viewgrades(Grades);
//        studentManager.viewtutionstatus(tuition);
//        studentManager.editprofile(this);
        studentManager.viewFacultyProfiles(this);
    }

    // Method to view all events and highlight registered events
    public void viewEventsIncludingRegistered() { //just pass it the student name
        EventManager eventManager = new EventManager();
        eventManager.loadEventsFromFile();
        boolean found = false;

        System.out.println("Events:");
        for (Event event : eventManager.getEvents()) {
            boolean isRegistered = event.getRegisteredStudents().contains(name);
            System.out.println(event + (isRegistered ? "\n(Registered)" : ""));
            found = found || isRegistered;
        }

        if (!found) {
            System.out.println("You are not registered for any events.");
        }
    }
}
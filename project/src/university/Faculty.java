package university;

import java.util.ArrayList;
import java.util.Scanner;

public class Faculty extends User {
    private String Degree;
    private String ResearchInterest;
    private String OfficeLocation;
    private String CoursesOffered;
    private String ProfilePhoto; //implement later



    public Faculty(String userId, String name, String Degree, String ResearchInterest, String email,
                   String OfficeLocation, String CoursesOffered, String password) {

        super(userId, password, name, email); // Matches superclass fields
        this.Degree = Degree;
        this.ResearchInterest = ResearchInterest;
        this.OfficeLocation = OfficeLocation;
        this.CoursesOffered = CoursesOffered;


    }

    // Getters
    public String getName(){
        return super.getName();
    }

    public String getpassword(){
        return super.getPassword();
    }

    public String getFacultyID() {
        return super.getUserId();
    }

    public String getemail() {
        return super.getEmail();
    }

    public String getDegree() {
        return Degree;
    }

    public String getResearchInterest() {
        return ResearchInterest;
    }

    public String getOfficeLocation() {
        return OfficeLocation;
    }

    public String getCoursesOffered() {
        return CoursesOffered;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    //SETTERS
    public void setPassword (String password){
        super.setPassword(password);
    }

    public void setFacultytName (String name){
        super.setName(name);
    }

    public void setFacultyID(String ID){
        super.setUserId(ID);
    }

    public void setFacultyEmail(String email){
        super.setEmail(email);
    }

    public void setDegree(String Degree) {
        this.Degree = Degree;
    }

    public void setResearchInterest(String ResearchInterest) {
        this.ResearchInterest = ResearchInterest;
    }

    public void setOfficeLocation(String OfficeLocation) {
        this.OfficeLocation = OfficeLocation;
    }

    public void setCoursesOffered(String CoursesOffered) {
        this.CoursesOffered = CoursesOffered;
    }

    public void setProfilePhoto(String ProfilePhoto) {
        this.ProfilePhoto = ProfilePhoto;
    }

    @Override
    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        //testmethod();
        while (true) {
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. View all Subjects");
            System.out.println("4. View Courses");
            System.out.println("5. View The Courses you manage and all of the student who are registered in it");
            System.out.println("6. View Events (Including Registered)"); //students already registered will get not registered message
            System.out.println("7. Register for Events");
            System.out.println("8. Exit");//works
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    FacultyManager facultyManager = new FacultyManager();
                    Set_Get_Details getDetails = new Set_Get_Details();
                    facultyManager.viewprofile(getDetails.getFacultyDetails(getemail()));
                    break;
                case 2:
                    facultyManager = new FacultyManager();
                    Set_Get_Details editDetails = new Set_Get_Details();
                    facultyManager.editprofile(editDetails.getFacultyDetails(getemail()));
                case 3:
                    SubjectManager subjectManager = new SubjectManager();
                    subjectManager.viewSubjects();
                    break;
                case 4:
                    CourseManager courseManager = new CourseManager();
                    courseManager.viewCourses();
                    break;
                case 5:
                    facultyManager = new FacultyManager();
                    System.out.println("This are the courses you managing:" + getCoursesOffered());
                    facultyManager.viewStudentInformation(this);
                    break;

                case 6:
                    viewEventsIncludingRegistered();
                    break;

                case 7:
                    EventManager eventManager = new EventManager();
                    System.out.println("Enter event code:");
                    String eventCode = scanner.nextLine();
                    eventManager.manageRegistrations(eventCode, userId, name, "TextData/Faculty.txt"); //supposedly adds events
                    break;

                case 8:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

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

    public void testmethod(){
        FacultyManager facultyManager = new FacultyManager();
        facultyManager.viewprofile(this);
        facultyManager.editprofile(this);
    }

}



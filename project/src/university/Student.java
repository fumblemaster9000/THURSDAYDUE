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

    // Getter methods for student attributes
    public String getname(){return super.getName();}
    public String getpassword(){return super.getPassword();}
    public String getStudentID() {return super.getUserId();}
    public String getemail() {return super.getEmail();}
    public String getAddress() {return address;}
    public String getTelephone() {return Telephone;}
    public String getAcademicLevel() {return AcademicLevel;}
    public String getCurrentSemester() {return CurrentSemester;}
    public String getProfilePhoto() {return ProfilePhoto;}
    public String getSubjectsRegistered() {return subjectsregistered;}
    public String getThesisTitle() {return thesistitle;}
    public String getProgress() {return progress;}
    public String getTuition() {return tuition;}
    public String getGrades() {return Grades;}
    public String getRolledCourses(){return Rolledcourses;}

    // Setter methods to update student attributes
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

    // Displays student-specific menu options
    // by overriding a method in the parent class (User)
    @Override
    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        // Keep displaying the menu until the user chooses to exit
        while (true) {
            // Display the menu options
            System.out.println("Student Options for " + name + ":");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. View all Subjects");
            System.out.println("4. View Faculty Profiles");
            System.out.println("5. View Courses that you are enrolled in");
            System.out.println("6. View Events (Including Registered)"); //students already registered will get not registered message
            System.out.println("7. Register for Events");
            System.out.println("8. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1: // View Profile
                    // Uses `StudentManager` and `Set_Get_Details` to fetch and display the profile
                    StudentManager studentManager = new StudentManager();
                    Set_Get_Details getDetails = new Set_Get_Details();
                    studentManager.viewprofile(getDetails.getStudentDetails(getStudentID()));
                    break;
                case 2: // Edit Profile
                    // Allows the student to edit their profile
                    studentManager = new StudentManager();
                    Set_Get_Details editDetails = new Set_Get_Details();
                    studentManager.editprofile(editDetails.getStudentDetails(getStudentID()));
                    break;
                case 3: // View All Subjects
                    // Displays all subjects the student can view, using `SubjectManager`
                    SubjectManager subjectManager = new SubjectManager(); //add subject manager to only show enrolled courses
                    subjectManager.viewSubjects();
                    break;
                case 4: // View Faculty Profiles
                    // Uses `StudentManager` to view faculty profiles
                    studentManager = new StudentManager();
                    studentManager.viewFacultyProfiles(this);
                    break;
                case 5: // View Enrolled Courses
                    // Displays courses the student is enrolled in, using `CourseManager`
                    CourseManager courseManager = new CourseManager();
                    courseManager.viewCourses(); //display all details
                    break;
                case 6: // View Events (Including Registered)
                    // Displays all events and highlights the ones the student is registered for
                    viewEventsIncludingRegistered();
                    break;

                case 7: // Register for Events
                    // Allows the student to register for events, using `EventManager`
                    EventManager eventManager = new EventManager();
                    System.out.println("Enter event code:");
                    String eventCode = scanner.nextLine();
                    eventManager.manageRegistrations(eventCode, userId, name, "TextData/Student.txt"); //supposedly adds events
                    break;

                case 8: // Exit
                    // Exit the menu
                    System.out.println("Exiting...");
                    return;

                default: // Invalid Input
                    // Prompt the user to enter a valid choice
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to view all events and highlight registered events
    public void viewEventsIncludingRegistered() { //just pass it the student name
        EventManager eventManager = new EventManager();
        eventManager.loadEventsFromFile();
        boolean found = false;

        System.out.println("Events:");
        for (Event event : eventManager.getEvents()) {
            // Check if the student's name is in the list of registered students for the current event
            boolean isRegistered = event.getRegisteredStudents().contains(name);
            System.out.println(event + (isRegistered ? "\n(Registered)" : ""));
            found = found || isRegistered;
        }
        // If the student is not registered for any events, display an appropriate message
        if (!found) {
            System.out.println("You are not registered for any events.");
        }
    }
}
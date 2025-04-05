package university;

import java.util.ArrayList;
import java.util.Scanner;
// Faculty is a subclass of the User
public class Faculty extends User {
    private String Degree;
    private String ResearchInterest;
    private String OfficeLocation;
    private String CoursesOffered;
    private String ProfilePhoto;


    // Constructor to initializes a faculty object
    public Faculty(String userId, String name, String Degree, String ResearchInterest, String email,
                   String OfficeLocation, String CoursesOffered, String password) {

        super(userId, password, name, email); // Matches superclass fields
        this.Degree = Degree;
        this.ResearchInterest = ResearchInterest;
        this.OfficeLocation = OfficeLocation;
        this.CoursesOffered = CoursesOffered;


    }

    // Getter methods for faculty attributes
    public String getName() {return super.getName();}
    public String getpassword() {return super.getPassword();}
    public String getFacultyID() {return super.getUserId();}
    public String getemail() {return super.getEmail();}
    public String getDegree() {return Degree;}
    public String getResearchInterest() {return ResearchInterest;}
    public String getOfficeLocation() {return OfficeLocation;}
    public String getCoursesOffered() {return CoursesOffered;}
    public String getProfilePhoto() {return ProfilePhoto;}

    // Setter methods to update faculty attributes
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

    // Displays faculty-specific menu options
    // by overriding a method in the parent class (User)
    @Override
    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        // Continuously show the menu until the faculty chooses to exit
        while (true) {
            // Display the menu options
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. View all Subjects");
            System.out.println("4. View Courses");
            System.out.println("5. View The Courses you manage and all the student who are registered in it");
            System.out.println("6. View Events (Including Registered)"); //students already registered will get not registered message
            System.out.println("7. Register for Events");
            System.out.println("8. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            // Handle each chosen option using a switch-case structure
            switch (choice) {
                case 1:// View faculty profile
                    // Uses `FacultyManager` and `Set_Get_Details` to fetch and display the profile
                    FacultyManager facultyManager = new FacultyManager();
                    Set_Get_Details getDetails = new Set_Get_Details();
                    facultyManager.viewprofile(getDetails.getFacultyDetails(getemail()));
                    break;
                case 2: // Edit Profile
                    // Allows the faculty to edit their profile
                    facultyManager = new FacultyManager();
                    Set_Get_Details editDetails = new Set_Get_Details();
                    facultyManager.editprofile(editDetails.getFacultyDetails(getemail()));
                case 3:// View All Subjects
                    // Displays all subjects the faculty can view, using `SubjectManager`
                    SubjectManager subjectManager = new SubjectManager();
                    subjectManager.viewSubjects();
                    break;
                case 4:// View Courses
                    // Displays all courses, using `CourseManager`
                    CourseManager courseManager = new CourseManager();
                    courseManager.viewCourses();
                    break;
                case 5: // View The Courses you manage and all the student who are registered in it
                    //Uses `FacultyManager` to view the courses the faculty manage and the students who are in it
                    facultyManager = new FacultyManager();
                    System.out.println("This are the courses you managing:" + getCoursesOffered());
                    facultyManager.viewStudentInformation(this);
                    break;

                case 6:// View Events (Including Registered)
                    // Displays all events and highlights the ones the faculty is registered for
                    viewEventsIncludingRegistered();
                    break;

                case 7: // Register for Events
                    // Allows the student to register for events, using `EventManager`
                    EventManager eventManager = new EventManager();
                    System.out.println("Enter event code:");
                    String eventCode = scanner.nextLine();
                    eventManager.manageRegistrations(eventCode, userId, name, "TextData/Faculty.txt"); //supposedly adds events
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

    // Allows the faculty to view and manage events that they have registered for
    public void viewEventsIncludingRegistered() { //just pass it a name
        EventManager eventManager = new EventManager();
        eventManager.loadEventsFromFile();
        boolean found = false;

        System.out.println("Events:");
        for (Event event : eventManager.getEvents()) {
            // Check if the faculty's name is in the list of registered faculties for the current event
            boolean isRegistered = event.getRegisteredStudents().contains(name);
            System.out.println(event + (isRegistered ? "\n(Registered)" : ""));
            found = found || isRegistered;
        }
        // If the faculty is not registered for any events, display an appropriate message
        if (!found) {
            System.out.println("You are not registered for any events.");
        }
    }
}



package university;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Admin extends User {
    private LoadFile fileload = new LoadFile();
    private SubjectManager subjectManager = new SubjectManager();
    private CourseManager courseManager = new CourseManager();
    private EventManager eventManager = new EventManager();
    private StudentManager studentManager = new StudentManager();
    private FacultyManager facultyManager = new FacultyManager();

    public Admin(String userId, String password, String name, String email) {
        super(userId, password, name, email);
        courseManager.loadCoursesFromFile(); // Load courses when Admin is instantiated
    }

    @Override
    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Options for " + super.getName() + ":");
            System.out.println("1. Manage Subjects");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Events");
            System.out.println("4. Manage Students");
            System.out.println("5. Manage Facultys");
            System.out.println("6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    handleSubjectOptions(); // Handle subject-related options
                    break;
                case 2:
                    handleCourseOptions(scanner, courseManager); // Handle course-related options
                    break;
                case 3:
                    handleEventOptions(scanner, eventManager); // Handle event-related options
                    break;
                case 4:
                    handleStudentOptions(scanner, studentManager); // Handle student-related options
                    break;
                case 5:
                    handleFacultyOptions(scanner, facultyManager); // Handle faculty-related options
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }

    private void handleSubjectOptions(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Course Management Options for " + super.getName() + ":");
        System.out.println("1. Add Subject");
        System.out.println("2. Edit Subject");
        System.out.println("3. Delete Subjects");
        System.out.println("4.View Subjects");
        int choice = scan.nextInt();
        switch (choice){
            case 1:
                System.out.print("Enter new subject code: ");
                scan.nextLine();

                String subjcode = scan.nextLine();
                System.out.print("Enter new subject name: ");

                String subjname = scan.nextLine();
                subjectManager.addSubject(subjname, subjcode);
                break;
            case 2:
                System.out.print("Enter the name of the subject you would like to change: ");
                scan.nextLine();

                String replacesubjname = scan.nextLine();
                System.out.println("What attribute would you like to edit about the subject?");
                System.out.println("1.Subject Name, 2.Subject Code");
                int choicereplace = scan.nextInt();
                if(choicereplace == 1){
                    System.out.println("Enter the new subject name:");
                    scan.nextLine();
                    fileload.writeToFile("TextData/Subjects.txt", "Subject Name", replacesubjname, scan.nextLine());
                } else if (choicereplace == 2) {
                    System.out.println("Enter the new subject code:");
                    scan.nextLine();
                    subjcode = scan.nextLine();
                    fileload.writeToFile("TextData/Subjects.txt", "Subject Code", replacesubjname, subjcode);
                }
                else{
                    System.out.println("Invalid choice");
                }
                break;
            case 3:
                System.out.print("Enter the code of the subject you would like to atomize: ");
                scan.nextLine();
                subjectManager.deleteSubject(scan.nextLine());
            case 4:
                subjectManager.viewSubjects();
                break;
        }
    }

    private void handleStudentOptions(Scanner scanner, StudentManager studentManager){
        System.out.println("Event Management Options for " + super.getName() + ":");
        System.out.println("1. Add Student");
        System.out.println("2. Edit Student");
        System.out.println("3. Delete Student");
        System.out.println("4. View Student Profile");
        System.out.println("5. Manage Enrollments");
        System.out.println("6. Academic Progress Tracking");
        System.out.println("7. Tuition Management:");
        System.out.println("8. Back to Main Menu");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                Student newStudent = setStudentDetails(scanner);
                studentManager.addStudent(newStudent);
                break;
            case 2:
                System.out.println("Enter the ID of student");
                String studentID = scanner.nextLine();
                Student updatedStudentProfile = setStudentDetails(scanner);
                studentManager.editStudent(studentID, updatedStudentProfile);
                break;
            case 3:
                System.out.println("Enter the ID of student");
                studentID = scanner.nextLine();
                studentManager.deleteStudent(studentID);
                break;
            case 4:
                System.out.println("Enter the ID of student");
                studentID = scanner.nextLine();
                Student newStudentDetails = getStudentDetails(studentID);
                studentManager.viewprofile(newStudentDetails);
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private Student getStudentDetails(String studentID){
        String name = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Name");
        String address = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Address");
        String Telephone = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Telephone");
        String email = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Email");
        String AcademicLevel = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Academic Level");
        String CurrentSemester = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Current Semester");
        String ProfilePhoto = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Profile Photo");
        String subjectsregistered = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Subjects Registered");
        String thesistitle = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Thesis Title");
        String progress = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Progress");
        String password = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Password");
        String tuition =  "$5000";
        if (AcademicLevel.equals("Undergraduate")) {
            tuition = "$5000";
        } else if(AcademicLevel.equals("Graduate") ) {
            tuition = "$4000";
        } else {tuition = "$0";}
        
        String Grades = "A";

        return new Student(studentID, name, address, Telephone,
                email, AcademicLevel, CurrentSemester, ProfilePhoto,
                subjectsregistered, thesistitle, progress,
                password,tuition,Grades);
    }

    private Student setStudentDetails(Scanner scanner){
        LoadFile fileload = new LoadFile();
        int[][] arr = fileload.arraydimension("TextData/Student.txt");
        String checker = "";
        String randID = "";
        while(true) {
            Random random = new Random();
            int randomNumber = random.nextInt(10000);
            if (randomNumber >= 1000) {
                randID = "S2025" + randomNumber; // For numbers 1000 to 9999
            } else if (randomNumber >= 100) {
                randID = "S20250" + randomNumber; // For numbers 100 to 999
            } else if (randomNumber >= 10) {
                randID = "S202500" + randomNumber; // For numbers 10 to 99
            } else {
                randID = "S2025000" + randomNumber; // For numbers 0 to 9
            }

            ArrayList<String> array = fileload.fetchAll("TextData/Student.txt", checker, "Student ID");
            if (array.size() == 0){
                break;
            }
        }
        String studentID = randID;
        System.out.println("Enter student's name");
        String name = scanner.nextLine();
        System.out.println("Enter the address of the student");
        String address = scanner.nextLine();
        System.out.println("Enter the student's Telephone");
        String Telephone = scanner.nextLine();
        System.out.println("Enter the student's Email");
        String email = scanner.nextLine();
        System.out.println("Enter the student's academic level");
        String AcademicLevel = scanner.nextLine();
        System.out.println("Enter the current semester the student is in");
        String CurrentSemester = scanner.nextLine();
        System.out.println("Enter the student's profile photo");
        String ProfilePhoto = scanner.nextLine();
        System.out.println("Enter the subjects that the student will be register in");
        String subjectsregistered = scanner.nextLine();
        System.out.println("Enter the Thesis Title that the student is working on");
        String thesistitle = ("\"" + scanner.nextLine() + "\"");
        System.out.println("Enter the progress of the student");
        String progress = scanner.nextLine();
        String password = "default123";
        String tuition = "$5000";
        if (AcademicLevel.equals("Undergraduate")) {
            tuition = "$5000";
        } else if(AcademicLevel.equals("Graduate") ) {
            tuition = "$4000";
<<<<<<< HEAD
        }
        String Grades = "A";
=======
        } else {tuition = "$0";}
        String Grades = "0";
>>>>>>> 5da8dd7acea24d47f67ee53c7e60535cfcc7d9ca

        return new Student(studentID, name, address, Telephone,
                email, AcademicLevel, CurrentSemester, ProfilePhoto,
                subjectsregistered, thesistitle, progress,
                password,tuition,Grades);
    }

    private void handleFacultyOptions(Scanner scanner, FacultyManager facultyManager){
        System.out.println("Event Management Options for " + super.getName() + ":");
        System.out.println("1. Add Faculty Member");
        System.out.println("2. Edit Faculty Member");
        System.out.println("3. Delete Faculty Member");
        System.out.println("4. View Faculty Profile");
        System.out.println("5. Back to Main Menu");
        System.out.println("If you want to assign courses to a faculty");
        System.out.println("then please go back to Manage Courses");

        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                boolean add = true;
                Faculty newFaculty = setFacultyDetails(scanner,add);
                facultyManager.addFaculty(newFaculty);
                break;
            case 2:
                add = false;
                System.out.println("Enter the email of faculty");
                String facultyEmail = scanner.nextLine();
                Faculty updatedFacultyProfile = setFacultyDetails(scanner,add);
                facultyManager.editFaculty(facultyEmail, updatedFacultyProfile);
                break;
            case 3:
                System.out.println("Enter the email of faculty");
                facultyEmail = scanner.nextLine();
                facultyManager.deleteFaculty(facultyEmail);
                break;
            case 4:
                System.out.println("Enter the email of faculty");
                facultyEmail = scanner.nextLine();
                Faculty newFacultyDetails = getFacultyDetails(facultyEmail);
                facultyManager.viewprofile(newFacultyDetails);
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private Faculty getFacultyDetails(String facultyEmail){

        String FacultyID = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Faculty ID");
        String name = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email", "Name");
        String facultyDegree = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Degree");
        String ResearchInterest = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email", "Research Interest");
        String OfficeLocation = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Office Location");
        String CoursesOffered = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Courses Offered");
        String password = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Password");

        return new Faculty(FacultyID, name, facultyDegree, ResearchInterest,
                facultyEmail, OfficeLocation, CoursesOffered, password);
    }

    private void handleCourseOptions(Scanner scanner, CourseManager courseManager) {
        while (true) {
            List<Course> courses = courseManager.getCourses();
            System.out.println("Course Management Options for " + super.getName() + ":");
            System.out.println("1. Add Course");
            System.out.println("2. Edit Course");
            System.out.println("3. Delete Course");
            System.out.println("4. Assign Faculty");
            System.out.println("5. Manage Enrollments");
            System.out.println("6. View Courses");
            System.out.println("7. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    courseManager.viewCourses();
                    Course newCourse = getCourseDetails(scanner);
                    courseManager.addCourse(newCourse);
                    break;

                case 2:
                    courseManager.viewCourses();
                    System.out.println("Enter course code to edit:");
                    String courseCode = scanner.nextLine();
                    for (Course course : courses){
                        if(course.getCourseCode().equals(courseCode)){
                            Course updatedCourse = getCourseDetails(scanner);
                            courseManager.editCourse(courseCode, updatedCourse);
                        }
                    }
                    break;

                case 3:
                    courseManager.viewCourses();
                    System.out.println("Enter course code to delete:");
                    courseCode = scanner.nextLine();
                    for (Course course : courses){
                        if(course.getCourseCode().equals(courseCode)){
                            Course updatedCourse = getCourseDetails(scanner);
                            courseManager.editCourse(courseCode, updatedCourse);
                        }
                    }
                    courseManager.deleteCourse(courseCode);
                    break;

                case 4:
                    courseManager.viewCourses();
                        System.out.println("Enter course code to assign faculty:");
                        courseCode = scanner.nextLine();
                        for (Course course : courses){
                            if(course.getCourseCode().equals(courseCode)){
                                System.out.println("Enter teacher name:");
                                String teacherName = scanner.nextLine();
                                courseManager.assignFaculty(courseCode, teacherName);
                                break;
                            }
                        }
                    break;

                case 5:
                    courseManager.viewCourses();
                    System.out.println("Enter course code to manage enrollments:");
                    courseCode = scanner.nextLine();
                    for (Course course : courses){
                        if(course.getCourseCode().equals(courseCode)){
                            courseManager.manageEnrollments(courseCode);
                            break;
                        }
                    }
                    break;

                case 6:
                    courseManager.viewCourses(); // View courses
                    break;

                case 7:
                    return; // Go back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleEventOptions(Scanner scanner, EventManager eventManager) {

        while (true) {
            System.out.println("Event Management Options for " + super.getName() + ":");
            System.out.println("1. Add Event");
            System.out.println("2. Edit Event");
            System.out.println("3. Delete Event");
            System.out.println("4. View Events");
            System.out.println("5. Manage Registrations");
            System.out.println("6. Back to Main Menu");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    Event newEvent = getEventDetails(scanner);
                    eventManager.addEvent(newEvent);
                    break;
                case 2:
                    System.out.println("Enter event code to edit:");
                    String eventCode = scanner.nextLine();
                    Event updatedEvent = getEventDetails(scanner);
                    eventManager.editEvent(eventCode, updatedEvent);
                    break;
                case 3:
                    System.out.println("Enter event code to delete:");
                    eventCode = scanner.nextLine();
                    eventManager.deleteEvent(eventCode);
                    break;
                case 4:
                    eventManager.viewEvents();
                    break;
                case 5:
                    System.out.println("Enter event code for registration:");
                    eventCode = scanner.nextLine();
                    System.out.println("Enter student ID to register:");
                    String studentId = scanner.nextLine();
                    eventManager.manageRegistrations(eventCode, studentId, fileload.ID_FetchThing("TextData/Student.txt", studentId, "Name"), "TextData/Student.txt"); //find name using the fetchthing
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private Course getCourseDetails(Scanner scanner) {
        System.out.println("Enter course name:");
        String courseName = scanner.nextLine();
        System.out.println("Enter course code:");
        String courseCode = scanner.nextLine();
        System.out.println("Enter subject name:");
        String subjectName = scanner.nextLine();
        System.out.println("Enter section number:");
        String sectionNumber = scanner.nextLine();
        System.out.println("Enter teacher name:");
        String teacherName = scanner.nextLine();
        System.out.println("Enter capacity:");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter lecture time:");
        String lectureTime = scanner.nextLine();
        System.out.println("Enter final exam date/time:");
        String finalExamDateTime = scanner.nextLine();
        System.out.println("Enter location:");
        String location = scanner.nextLine();

        return new Course(courseName, courseCode, subjectName, sectionNumber, capacity, lectureTime, finalExamDateTime, location, teacherName);
    }

    private Event getEventDetails(Scanner scanner) {
        System.out.println("Enter event name:");
        String eventName = scanner.nextLine();
        System.out.println("Enter event code:");
        String eventCode = scanner.nextLine();
        System.out.println("Enter description:");
        String description = scanner.nextLine();
        System.out.println("Enter header image (or leave blank for default):");
        String headerImage = scanner.nextLine();
        if (headerImage.isEmpty()) {
            headerImage = "default.jpg";
        }
        System.out.println("Enter location:");
        String location = scanner.nextLine();
        System.out.println("Enter date and time:");
        String dateTime = scanner.nextLine();
        System.out.println("Enter capacity:");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter cost:");
        String cost = scanner.nextLine();

        return new Event(eventCode,eventName, description,  location, dateTime, capacity, cost, headerImage);
    }

    private Faculty setFacultyDetails(Scanner scanner, boolean add){
        System.out.println("Enter faculty ID");
        String FacultyID = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter faculty's name");
        String name = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter the Degree of the faculty");
        String facultyDegree = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter the research interest of the faculty");
        String ResearchInterest = scanner.nextLine();
        scanner.nextLine();
        if(!add){
            System.out.println("Enter the faculty's Email");
        }
        String email = add ? facultyManager.Email(name) : scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter the faculty's office location");
        String OfficeLocation = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter the courses offered of the faculty");
        String CoursesOffered = scanner.nextLine();
        scanner.nextLine();
        if(!add){
            System.out.println("Enter the faculty's password");
        }
        String password = add ? "default123" : scanner.nextLine();
        scanner.nextLine();

        return new Faculty(FacultyID, name, facultyDegree, ResearchInterest,
                email, OfficeLocation, CoursesOffered, password);
    }

}
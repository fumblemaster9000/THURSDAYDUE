package university;

import javax.management.relation.Role;
import java.util.*;

public class Admin extends User {
    // Managers for handling different entities in the system
    private LoadFile fileload = new LoadFile(); // Utility for file operations
    private SubjectManager subjectManager = new SubjectManager(); // Subject management logic
    private CourseManager courseManager = new CourseManager(); // Course management logic
    private EventManager eventManager = new EventManager(); // Event management logic
    private StudentManager studentManager = new StudentManager(); // Student management logic
    private FacultyManager facultyManager = new FacultyManager(); // Faculty management logic

    // Constructor initializes the Admin with user information
    public Admin(String userId, String password, String name, String email) {
        super(userId, password, name, email);
        courseManager.loadCoursesFromFile(); // Load courses when Admin is instantiated
    }

    // Displays the administrative menu
    // by overriding a method in the parent class (User)
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
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                // Route the admin to the appropriate option based on their selection
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
                        System.out.println("Logging out..."); // Exit the loop
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    // Manages subject-related operations such as add, edit, delete, or view
    private void handleSubjectOptions(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Course Management Options for " + super.getName() + ":");
        System.out.println("1. Add Subject");
        System.out.println("2. Edit Subject");
        System.out.println("3. Delete Subjects");
        System.out.println("4.View Subjects");
        System.out.println("5. Back to Main Menu");
        try {
            System.out.print("Enter your choice: ");
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
                        case 5:
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
        } catch (Exception e) {
            System.out.println("Error while managing subjects: " + e.getMessage());
        }
    }
    // Manages operations related to students (add/edit/delete/view/Enrollments)
    private void handleStudentOptions(Scanner scanner, StudentManager studentManager){
        System.out.println("Event Management Options for " + super.getName() + ":");
        System.out.println("1. Add Student");
        System.out.println("2. Edit Student");
        System.out.println("3. Delete Student");
        System.out.println("4. View Student Profile");
        System.out.println("5. Manage Enrollments");
        System.out.println("6. Back to Main Menu");
        try {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    Set_Get_Details setDetails = new Set_Get_Details();
                    studentManager.addStudent(setDetails.setStudentDetails(scanner));
                    break;
                case 2:
                    System.out.println("Enter the ID of student");
                    String studentID = scanner.nextLine();
                    Set_Get_Details updatedStudentProfile = new Set_Get_Details();
                    studentManager.editStudent(studentID, updatedStudentProfile.setStudentDetails(scanner));
                    break;
                case 3:
                    System.out.println("Enter the ID of student");
                    studentID = scanner.nextLine();
                    studentManager.deleteStudent(studentID);
                    break;
                case 4:
                    System.out.println("Enter the ID of student");
                    studentID = scanner.nextLine();
                    Set_Get_Details getDetails = new Set_Get_Details();
                    studentManager.viewprofile(getDetails.getStudentDetails(studentID));
                    break;
                case 5:
                    System.out.println("Enter the student ID: ");
                    String StudentID = scanner.nextLine();
                    courseManager.courseRegisterStudent(StudentID);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (InputMismatchException e) {
        System.out.println("Invalid input for faculty operation!");
        scanner.nextLine(); // Clear invalid input
        } catch (Exception e) {
        System.out.println("Error occurred while managing faculties: " + e.getMessage());
        }
    }


// Manages faculty profile operations (add/edit/view)
    private void handleFacultyOptions(Scanner scanner, FacultyManager facultyManager){
        System.out.println("Event Management Options for " + super.getName() + ":");
        System.out.println("1. Add Faculty Member");
        System.out.println("2. Edit Faculty Member");
        System.out.println("3. Delete Faculty Member");
        System.out.println("4. View Faculty Profile");
        System.out.println("5. Back to Main Menu");
        System.out.println("If you want to assign courses to a faculty");
        System.out.println("then please go back to Manage Courses");
        try {
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    boolean add = true;
                    Set_Get_Details newFaculty = new Set_Get_Details();
                    facultyManager.addFaculty(newFaculty.setFacultyDetails(scanner,add));
                    break;
                case 2:
                    add = false;
                    System.out.println("Enter the email of faculty");
                    String facultyEmail = scanner.nextLine();
                    Set_Get_Details updatedFacultyProfile = new Set_Get_Details();
                    facultyManager.editFaculty(facultyEmail, updatedFacultyProfile.setFacultyDetails(scanner,add));
                    break;
                case 3:
                    System.out.println("Enter the email of faculty");
                    facultyEmail = scanner.nextLine();
                    facultyManager.deleteFaculty(facultyEmail);
                    break;
                case 4:
                    System.out.println("Enter the email of faculty");
                    facultyEmail = scanner.nextLine();
                    Set_Get_Details newFacultyDetails = new Set_Get_Details();
                    facultyManager.viewprofile(newFacultyDetails.getFacultyDetails(facultyEmail));
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for faculty operation!");
            scanner.nextLine(); // Clear invalid input
        } catch (Exception e) {
            System.out.println("Error occurred while managing faculties: " + e.getMessage());
        }
    }

    // Manages course-related operations like adding, editing, or viewing courses
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
            try{
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1: //Add Course
                        courseManager.viewCourses();
                        Set_Get_Details newCourse = new Set_Get_Details();
                        courseManager.addCourse(newCourse.getCourseDetails(scanner));
                        break;

                    case 2: //Edit Course
                        courseManager.viewCourses();
                        System.out.println("Enter the name of the course to edit:");
                        String courseName = scanner.nextLine();
                        System.out.println("Enter section number of the course to edit:");
                        String sectionNumber = scanner.nextLine();
                        for (Course course : courses) {
                            if (course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)) {
                                Set_Get_Details updatedCourse = new Set_Get_Details();
                                courseManager.editCourse(courseName, sectionNumber, updatedCourse.getCourseDetails(scanner));
                            }
                        }
                        break;

                    case 3: //Delete Course
                        courseManager.viewCourses();
                        System.out.println("Enter the name of the course to delete:");
                        courseName = scanner.nextLine();
                        System.out.println("Enter section number of the course to delete:");
                        sectionNumber = scanner.nextLine();
                        for (Course course : courses) {
                            if (course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)) {
                                courseManager.deleteCourse(courseName, sectionNumber);
                                break;
                            }
                        }
                        break;

                    case 4: //Assign Faculty
                        courseManager.viewCourses();
                        System.out.println("Enter the name of the course to assign faculty to:");
                        courseName = scanner.nextLine();
                        System.out.println("Enter section number of the course to assign faculty to:");
                        sectionNumber = scanner.nextLine();
                        for (Course course : courses) {
                            if (course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)) {
                                System.out.println("Enter teacher name:");
                                String teacherName = scanner.nextLine();
                                courseManager.assignFaculty(courseName, sectionNumber, teacherName);
                                break;
                            }
                        }
                        break;

                    case 5: //Manage Enrollments
                        courseManager.viewCourses();
                        System.out.println("Enter the name of the course to manage it enrollments:");
                        courseName = scanner.nextLine();
                        System.out.println("Enter section number of the course to manage it enrollments:");
                        sectionNumber = scanner.nextLine();
                        for (Course course : courses) {
                            if (course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)) {
                                courseManager.manageEnrollments(courseName, sectionNumber);
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
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for event operation!");
                scanner.nextLine(); // Clear invalid input
            } catch (Exception e) {
                System.out.println("Error occurred while managing events: " + e.getMessage());
            }
        }

    }

    // Manages event-related operations like scheduling or modifying events
    private void handleEventOptions(Scanner scanner, EventManager eventManager) {

        while (true) {
            System.out.println("Event Management Options for " + super.getName() + ":");
            System.out.println("1. Add Event");
            System.out.println("2. Edit Event");
            System.out.println("3. Delete Event");
            System.out.println("4. View Events");
            System.out.println("5. Manage Registrations");
            System.out.println("6. Back to Main Menu");
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        Set_Get_Details newEvent = new Set_Get_Details();
                        eventManager.addEvent(newEvent.getEventDetails(scanner));
                        break;
                    case 2:
                        System.out.println("Enter event code to edit:");
                        String eventCode = scanner.nextLine();
                        Set_Get_Details updatedEvent = new Set_Get_Details();
                        eventManager.editEvent(eventCode, updatedEvent.getEventDetails(scanner));
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
            } catch (InputMismatchException e) {
                // Handle invalid input type for menu selection
                System.out.println("Invalid input! Please enter a number between 1 and 4.");
                scanner.nextLine(); // Clear invalid input
            } catch (Exception e) {
                // Catch and log any unexpected exceptions
                System.out.println("An error occurred while managing events: " + e.getMessage());
            }
        }
    }
}
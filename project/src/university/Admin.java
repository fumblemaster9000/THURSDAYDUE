package university;

import javax.management.relation.Role;
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
        System.out.println("5. Back to Main Menu");
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
        String Rolledcourses = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Registered Courses");
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
                password,tuition,Grades, Rolledcourses);
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

            ArrayList<String> array = fileload.fetchAll("TextData/Student.txt", randID, "Student ID");
            if (array.isEmpty()){
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
        } else {tuition = "$0";}
        String Grades = "0";
        String Rolledcourses = "";
        String courseName = "";
        String section;
        System.out.println("Enter the courses and section numbers to be enrolled by the student, type \"DONE\" to finish");
        while(true){
            System.out.println("Enter CourseName ex. Calculus I");
            courseName = scanner.nextLine();
            System.out.println("Enter Section Number ex. Section 1");
            section = scanner.nextLine();
                if (!courseName.equals("DONE")) {
                Rolledcourses = courseName + "," + section + ";"; //still needs input verif
            }else{
                break; //output should look like (Calculus I,Section 1;History II,Section 0;...;...;.etc)
            }
        }

        return new Student(studentID, name, address, Telephone,
                email, AcademicLevel, CurrentSemester, ProfilePhoto,
                subjectsregistered, thesistitle, progress,
                password,tuition,Grades, Rolledcourses);
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
                case 1: //Add Course
                    courseManager.viewCourses();
                    Set_Get_Details newCourse = new Set_Get_Details();
                    courseManager.addCourse(newCourse.getCourseDetails(scanner));
                    break;

                case 2: //Edit Course
                    courseManager.viewCourses();
                    System.out.println("Enter the name of the course to edit:");
                    String courseName= scanner.nextLine();
                    System.out.println("Enter section number of the course to edit:");
                    String sectionNumber= scanner.nextLine();
                    for (Course course : courses){
                        if(course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)){
                            Set_Get_Details updatedCourse = new Set_Get_Details();
                            courseManager.editCourse(courseName, sectionNumber, updatedCourse.getCourseDetails(scanner));
                        }
                    }
                    break;

                case 3: //Delete Course
                    courseManager.viewCourses();
                    System.out.println("Enter the name of the course to delete:");
                    courseName= scanner.nextLine();
                    System.out.println("Enter section number of the course to delete:");
                    sectionNumber = scanner.nextLine();
                    for (Course course : courses){
                        if(course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)){
                            courseManager.deleteCourse(courseName, sectionNumber);
                            break;
                        }
                    }
                    break;

                case 4: //Assign Faculty
                    courseManager.viewCourses();
                    System.out.println("Enter the name of the course to assign faculty to:");
                    courseName= scanner.nextLine();
                    System.out.println("Enter section number of the course to assign faculty to:");
                    sectionNumber = scanner.nextLine();
                        for (Course course : courses){
                            if(course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)){
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
                    courseName= scanner.nextLine();
                    System.out.println("Enter section number of the course to manage it enrollments:");
                    sectionNumber = scanner.nextLine();
                    for (Course course : courses){
                        if(course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)){
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
        }
    }

}
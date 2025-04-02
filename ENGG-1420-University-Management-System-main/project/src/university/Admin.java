package university;

import java.util.Scanner;

public class Admin extends User {
    private SubjectManager subjectManager = new SubjectManager();
    private CourseManager courseManager = new CourseManager();
    private EventManager eventManager = new EventManager();

    public Admin(String userId, String password, String name) {
        super(userId, password, name);
        courseManager.loadCoursesFromFile(); // Load courses when Admin is instantiated
    }

    @Override
    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Options for " + User.getName() + ":");
            System.out.println("1. Manage Subjects");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Events");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    handleSubjectsOptions(scanner, subjectManager); // Handle subject-related options
                    break;
                case 2:
                    handleCourseOptions(scanner, courseManager); // Handle course-related options
                    break;
                case 3:
                    handleEventOptions(scanner, eventManager); // Handle event-related options
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleSubjectsOptions(Scanner scanner, SubjectManager subjectManager) {
        while (true) {
            System.out.println("Subject Management Options for " + User.getName() + ":");
            System.out.println("1. Add Subject");
            System.out.println("2. Edit Subject");
            System.out.println("3. Delete Subject");
            System.out.println("4. View Subjects");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter subject name:");
                    String subjectName = scanner.nextLine();
                    if (subjectName.isEmpty()) {
                        System.out.println("Subject name cannot be empty. Please try again.");
                        break;
                    }
                    System.out.println("Enter subject code:");
                    String subjectCode = scanner.nextLine();
                    if (subjectCode.isEmpty()) {
                        System.out.println("Subject code cannot be empty. Please try again.");
                        break;
                    }
                    if (!subjectManager.isSubjectCodeUnique(subjectCode, subjectManager.loadSubjectsFromFile())) {
                        System.out.println("Subject code already exists. Please use a unique code.");
                        break;
                    }
                    subjectManager.addSubject(subjectName, subjectCode);
                    break;
                case 2:
                    System.out.println("Enter subject code to edit:");
                    subjectCode = scanner.nextLine();
                    System.out.println("Enter new subject name:");
                    subjectName = scanner.nextLine();
                    if (subjectName.isEmpty()) {
                        System.out.println("Subject name cannot be empty. Please try again.");
                        break;
                    }
                    subjectManager.editSubject(subjectCode, subjectName);
                    break;
                case 3:
                    System.out.println("Enter subject code to delete:");
                    subjectCode = scanner.nextLine();
                    subjectManager.deleteSubject(subjectCode);
                    break;
                case 4:
                    subjectManager.viewSubjects();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleCourseOptions(Scanner scanner, CourseManager courseManager) {
        while (true) {
            System.out.println("Course Management Options for " + User.getName() + ":");
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
                    Course newCourse = getCourseDetails(scanner);
                    courseManager.addCourse(newCourse);
                    break;
                case 2:
                    System.out.println("Enter course code to edit:");
                    String courseCode = scanner.nextLine();
                    Course updatedCourse = getCourseDetails(scanner);
                    courseManager.editCourse(courseCode, updatedCourse);
                    break;
                case 3:
                    System.out.println("Enter course code to delete:");
                    courseCode = scanner.nextLine();
                    courseManager.deleteCourse(courseCode);
                    break;
                case 4:
                    System.out.println("Enter course code to assign faculty:");
                    courseCode = scanner.nextLine();
                    System.out.println("Enter teacher name:");
                    String teacherName = scanner.nextLine();
                    courseManager.assignFaculty(courseCode, teacherName);
                    break;
                case 5:
                    System.out.println("Enter course code to manage enrollments:");
                    courseCode = scanner.nextLine();
                    courseManager.manageEnrollments(courseCode);
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
            System.out.println("Event Management Options for " + User.getName() + ":");
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
                    eventManager.manageRegistrations(eventCode, studentId);
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

        return new Event(eventName, eventCode, description, headerImage, location, dateTime, capacity, cost);
    }
}


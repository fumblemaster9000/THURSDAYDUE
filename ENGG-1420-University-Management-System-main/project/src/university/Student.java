package university;

import java.util.Scanner;

public class Student extends User {
    public Student(String userId, String password, String name, String email) {
        super(userId, password, name);
    }

    @Override
    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Student Options for " + User.getName() + ":");
            System.out.println("1. View Subjects");
            System.out.println("2. View Courses");
            System.out.println("3. View Events (Including Registered)");
            System.out.println("4. Register for Events");
            System.out.println("5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    SubjectManager subjectManager = new SubjectManager();
                    subjectManager.viewSubjects();
                    break;
                case 2:
                    CourseManager courseManager = new CourseManager();
                    courseManager.viewCourses();
                    break;
                case 3:
                    viewEventsIncludingRegistered();
                    break;
                case 4:
                    EventManager eventManager = new EventManager();
                    System.out.println("Enter event code:");
                    String eventCode = scanner.nextLine();
                    System.out.println("Enter your student ID:");
                    String studentId = User.getUserId();
                    eventManager.manageRegistrations(eventCode, studentId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to view all events and highlight registered events
    public void viewEventsIncludingRegistered() {
        EventManager eventManager = new EventManager();
        eventManager.loadEventsFromFile();
        String studentId = User.getUserId();
        boolean found = false;

        System.out.println("Events:");
        for (Event event : eventManager.getEvents()) {
            boolean isRegistered = event.getRegisteredStudents().contains(studentId);
            System.out.println(event + (isRegistered ? " (Registered)" : ""));
            found = found || isRegistered;
        }

        if (!found) {
            System.out.println("You are not registered for any events.");
        }
    }
}

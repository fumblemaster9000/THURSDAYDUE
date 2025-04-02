package university;

import java.util.Scanner;

public class Faculty extends User {
    public Faculty(String userId, String password, String name) {
        super(userId, password, name);
    }

    @Override
    public void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Faculty Options for " + User.getName() + ":");
            System.out.println("1. View Subjects");
            System.out.println("2. View Courses");
            System.out.println("3. Exit");
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
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}



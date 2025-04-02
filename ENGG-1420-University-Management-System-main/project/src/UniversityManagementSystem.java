import java.io.*;
import java.util.*;

public class UniversityManagementSystem {
    static List<Subject> subjects = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your ID:");
        String userId = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        if (authenticateUser(userId, password)) {
            User user = getUserRole(userId);
            logUserActivity(userId, "Logged in");
            user.displayOptions();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static boolean authenticateUser(String userId, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("userDatabase.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(userId) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static User getUserRole(String userId) {
        char roleIndicator = userId.charAt(0);
        return switch (roleIndicator) {
            case 'A' -> new Admin(userId);
            case 'S' -> new Student(userId);
            case 'F' -> new Faculty(userId);
            default -> throw new IllegalArgumentException("Unknown role indicator!");
        };
    }

    public static void logUserActivity(String userId, String activity) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("userActivityLog.txt", true))) {
            bw.write("User: " + userId + " - Activity: " + activity + " - Time: " + new Date());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

abstract class User {
    String userId;

    User(String userId) {
        this.userId = userId;
    }

    abstract void displayOptions();
}

class Admin extends User {

    Admin(String userId) {
        super(userId);
    }

    @Override
    void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nAdmin Options:");
            System.out.println("1. Add Subject");
            System.out.println("2. Edit Subject");
            System.out.println("3. Delete Subject");
            System.out.println("4. View Subjects");
            System.out.println("5. Exit");
            System.out.println("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addSubject(scanner);
                    break;
                case 2:
                    editSubject(scanner);
                    break;
                case 3:
                    deleteSubject(scanner);
                    break;
                case 4:
                    viewSubjects();
                    break;
                case 5:
                    UniversityManagementSystem.logUserActivity(userId, "Logged out");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addSubject(Scanner scanner) {
        System.out.println("Enter Subject Code:");
        String code = scanner.nextLine();
        System.out.println("Enter Subject Name:");
        String name = scanner.nextLine();

        if (Subject.isCodeUnique(code)) {
            UniversityManagementSystem.subjects.add(new Subject(code, name));
            System.out.println("Subject added successfully!");
            UniversityManagementSystem.logUserActivity(userId, "Added subject: " + code);
        } else {
            System.out.println("Subject code must be unique!");
        }
    }

    private void editSubject(Scanner scanner) {
        System.out.println("Enter Subject Code to Edit:");
        String code = scanner.nextLine();
        for (Subject subject : UniversityManagementSystem.subjects) {
            if (subject.code.equals(code)) {
                System.out.println("Enter New Subject Name:");
                subject.name = scanner.nextLine();
                System.out.println("Subject edited successfully!");
                UniversityManagementSystem.logUserActivity(userId, "Edited subject: " + code);
                return;
            }
        }
        System.out.println("Subject not found!");
    }

    private void deleteSubject(Scanner scanner) {
        System.out.println("Enter Subject Code to Delete:");
        String code = scanner.nextLine();
        UniversityManagementSystem.subjects.removeIf(subject -> subject.code.equals(code));
        System.out.println("Subject deleted successfully!");
        UniversityManagementSystem.logUserActivity(userId, "Deleted subject: " + code);
    }

    private void viewSubjects() {
        System.out.println("Subjects:");
        for (Subject subject : UniversityManagementSystem.subjects) {
            System.out.println(subject);
        }
        UniversityManagementSystem.logUserActivity(userId, "Viewed subjects");
    }
}

class Student extends User {

    Student(String userId) {
        super(userId);
    }

    @Override
    void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nStudent Options:");
            System.out.println("1. View Subjects");
            System.out.println("2. Exit");
            System.out.println("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewSubjects();
                    break;
                case 2:
                    UniversityManagementSystem.logUserActivity(userId, "Logged out");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewSubjects() {
        System.out.println("Subjects:");
        for (Subject subject : UniversityManagementSystem.subjects) {
            System.out.println(subject);
        }
        UniversityManagementSystem.logUserActivity(userId, "Viewed subjects");
    }
}

class Faculty extends User {

    Faculty(String userId) {
        super(userId);
    }

    @Override
    void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nFaculty Options:");
            System.out.println("1. View Subjects");
            System.out.println("2. Exit");
            System.out.println("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewSubjects();
                    break;
                case 2:
                    UniversityManagementSystem.logUserActivity(userId, "Logged out");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewSubjects() {
        System.out.println("Subjects:");
        for (Subject subject : UniversityManagementSystem.subjects) {
            System.out.println(subject);
        }
        UniversityManagementSystem.logUserActivity(userId, "Viewed subjects");
    }
}

class Subject {
    String code;
    String name;

    Subject(String code, String name) {
        this.code = code;
        this.name = name;
    }

    static boolean isCodeUnique(String code) {
        return UniversityManagementSystem.subjects.stream().noneMatch(subject -> subject.code.equals(code));
    }

    @Override
    public String toString() {
        return "Code: " + code + ", Name: " + name;
    }
}

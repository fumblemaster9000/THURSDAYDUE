package university;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin Admin = new Admin("admin01", "password123", "Admin User", "email");

        while (true) {
            System.out.println("Login as:");
            System.out.println("1. Admin");
            System.out.println("2. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    Admin.displayOptions();
                    break;
                case 2:
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

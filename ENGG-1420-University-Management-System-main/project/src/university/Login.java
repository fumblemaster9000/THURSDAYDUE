package university;

import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your ID:");
        String userId = scanner.nextLine().trim();
        System.out.println("Enter your password:");
        String password = scanner.nextLine().trim();

        User user = authenticateUser(userId, password);
        if (user != null) {
            user.displayOptions(); //the fuck
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static User authenticateUser(String userId, String password) {

        char roleIndicator = userId.charAt(0);
        // Replace with the path to the file needed
        String fileName = "";

        switch (roleIndicator) {

            case 'A':
                fileName = ""; //access admin user and pass
                break;

            case 'S':
                fileName = "out\\production\\Final_project\\Shitbox\\Student.txt";//access student user and pass
                break;

            case 'F':
                fileName = "out\\production\\Final_project\\Shitbox\\Faculty.txt";//etc
                break;

            default:
                System.out.println("Invalid role indicator.");
                break;
        }
        //      default -> throw new IllegalArgumentException("Unknown role indicator!");

        // Read user credentials from file
        LoadFile system = new LoadFile();

        if (password.equals(system.ID_FetchThing(fileName, userId, "Password"))) {
            System.out.println("Valid Login");
        }else{
            System.out.println("INVALID");
            return null;
        }


         switch (roleIndicator) {
             case 'A':
                 return new Admin(userId, password, system.ID_FetchThing(fileName, userId, "Name"));

             case 'S':
                 return new Student(userId, password, system.ID_FetchThing(fileName, userId, "Name"), system.ID_FetchThing(fileName, userId, "Email"));

             case 'F':
                 return new Faculty(userId, password, system.ID_FetchThing(fileName, userId, "Name"));

             default:
                 throw new IllegalArgumentException("Unknown role indicator!");
         }

//        if (storedUserId.equals(userId) && storedPassword.equals(password)) { // Delete
//            // if (storedUserId.equals(userId) && PasswordCheck) {
//            char roleIndicator = userId.charAt(0); //Delete
//            return switch (roleIndicator) {
//                case 'A' -> new Admin(storedUserId, storedPassword, storedName);
//                case 'S' -> new Student(storedUserId, storedPassword, storedName);
//                case 'F' -> new Faculty(storedUserId, storedPassword, storedName);
//                default -> throw new IllegalArgumentException("Unknown role indicator!");
//            };
//        }
    }
}
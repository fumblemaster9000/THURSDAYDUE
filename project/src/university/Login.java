package university;

import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your ID:");
        String userId = scanner.nextLine().trim();
        System.out.println("Enter your password:");
        String password = scanner.nextLine().trim();

        User user = authenticateUser(userId, password); //login works correctly

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
                fileName = "TextData/Administrator.txt"; //access admin user and pass
                break;

            case 'S':
                fileName = "TextData/Student.txt";//access student user and pass
                break;

            case 'F':
                fileName = "TextData/Faculty.txt";//etc
                break;

            default:
                System.out.println("Invalid role indicator.");
                return null;
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

        String[] obj = system.fetchRow(fileName, userId); //loads an array from text file containing all attributes for a given object

         switch (roleIndicator) {
             case 'A':
                 return new Admin(obj[0],obj[1], obj[2], obj[3]);

             case 'S':
                 return new Student(obj[0],obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7], obj[8], obj[9], obj[10], obj[11]);

             case 'F':
                 return new Faculty(obj[0],obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7]);

             default:
                 throw new IllegalArgumentException("Unknown role indicator!");
         }
    }
}
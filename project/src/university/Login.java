package university;

import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your ID:");
        String userId = scanner.nextLine().trim();
        System.out.println("Enter your password:");
        String password = scanner.nextLine().trim();

        // Authenticate the user with the provided credentials
        User user = authenticateUser(userId, password); //login works correctly

        // If authentication is successful show user options; if not display an error
        if (user != null) {
            user.displayOptions(); //the fuck
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static User authenticateUser(String userId, String password) {
        try {
        // Extract role indicator (first character of the User ID)
        char roleIndicator = userId.charAt(0);
        // Initialize a variable to store the appropriate file path
        String fileName = "";

        // Determine the file based on the role indicator
        switch (roleIndicator) {

            case 'A':
                fileName = "TextData/Administrator.txt"; //access admin user and pass
                break;

            case 'S':
                fileName = "TextData/Student.txt";//access student user and pass
                break;

            case 'F':
                fileName = "TextData/Faculty.txt";//access faculty user and pass
                break;

            default:
                System.out.println("Invalid role indicator.");
                return null;
        }

        // Read user credentials from file
        LoadFile system = new LoadFile();

        // Validate the password using the system object
        if (password.equals(system.ID_FetchThing(fileName, userId, "Password"))) {
            System.out.println("Valid Login");
        }else{
            System.out.println("INVALID");
            return null;
        }

        // Fetch the user attributes from the file for the authenticated user
        String[] obj = system.fetchRow(fileName, userId); //loads an array from text file containing all attributes for a given object

        // Create the correct user object based on the role indicator
         switch (roleIndicator) {
             case 'A': // Return Admin object with the appropriate attributes
                 return new Admin(obj[0],obj[1], obj[2], obj[3]);

             case 'S': // Return Student object with the appropriate attributes
                 return new Student(obj[0],obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7], obj[8], obj[9], obj[10], obj[11], "Tuition", "Grades", obj[14]);

             case 'F': // Return Faculty object with the appropriate attributes
                 return new Faculty(obj[0],obj[1], obj[2], obj[3], obj[4], obj[5], obj[6], obj[7]);

             default: // Handle unexpected or unknown role indicators
                 throw new IllegalArgumentException("Unknown role indicator!");
         }
    }catch (IndexOutOfBoundsException e) {
            // Handle errors where row data is incomplete or invalid
            System.out.println("Error: User data is incomplete or corrupted. " + e.getMessage());
            return null;
        } catch (Exception e) {
            // Catch and handle any unexpected errors during authentication
            System.out.println("An error occurred during authentication: " + e.getMessage());
            return null;
        }
    }
}

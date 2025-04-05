package university;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

// The FacultyManager class handles the management of faculty records,
public class FacultyManager {
    private List<Faculty> faculties = new ArrayList<>();
    private String fileName = "TextData/Faculty.txt";
    private LoadFile fileload = new LoadFile();

    //USER FEATURES

    // Displays the profile information of a given faculty member.
    public void viewprofile(Faculty faculty) { //views student profile
        System.out.println("Profile Photo: " + faculty.getProfilePhoto());
        System.out.println("Degree: " + faculty.getDegree());
        System.out.println("Research Interest: " + faculty.getResearchInterest());
        System.out.println("Office Location: " + faculty.getOfficeLocation());
        System.out.println("Courses Offered: " + faculty.getCoursesOffered());
        System.out.println("Name: " + faculty.getName());
        System.out.println("Email: " + faculty.getemail());
        System.out.println("Password: " + faculty.getpassword());
        System.out.println("Faculty ID: " + faculty.getFacultyID());
    }

    public void editprofile(Faculty faculty) {
        Scanner scan = new Scanner(in);
        // Get the user's choice
        System.out.println("1. : Edit the password");
        System.out.println("2. : Upload a picture to change the profile photo");
        int choice = scan.nextInt();
        // Handle the user's choice using a switch statement
        switch (choice) {
            case 1:
                System.out.print("Enter your new password: ");
                scan.nextLine();
                faculty.setPassword(scan.nextLine()); // Update the faculty password
                // Write the updated password to the file, ensuring persistence
                fileload.writeToFile("TextData/Faculty.txt","Password", faculty.getFacultyID(),faculty.getpassword());
                break;

            case 2:
                System.out.print("Upload your photo:");
                //code for photo upload
                break;

            default:
                // Invalid choice handling (optional)
                System.out.println("Invalid option. Please try again.");

        }
    }

    //Displays the list of courses assigned to a specific faculty member
    public void viewAssignedCourse(Faculty faculty){
        LoadFile fileload = new LoadFile();
        // Retrieve and display the list of assigned courses
        ArrayList<String> CoursesInformation = fileload.fetchAll("TextData/Courses.txt", faculty.getCoursesOffered(), "Subject Code"); //returns subject code
        System.out.println("This is all of the Courses you teaching!");
        for (String Courses : CoursesInformation) {
            System.out.println(fileload.fetchAnything("TextData/Faculty.txt", Courses, "Courses Offered"));
        }
    }

    //  Deletes a faculty member's record from the system based on their email address
    public void deleteFaculty(String facultyEmail) {
        loadFacultyFromFile();
        faculties.removeIf(faculty -> faculty.getemail().equals(facultyEmail));
        saveFacultyToFile();
        System.out.println("Faculty deleted successfully!");
    }

    // Updates the details of an existing faculty member in the system
    public void editFaculty(String facultyEmail, Faculty updatedFacultyProfile) {
        loadFacultyFromFile();
        for (Faculty faculty : faculties) {
            if (faculty.getemail().equals(facultyEmail)) { // Find the matching faculty by using their email
                // Update faculty details
                faculty.setFacultyID(updatedFacultyProfile.getFacultyID());
                faculty.setFacultytName(updatedFacultyProfile.getName());
                faculty.setDegree(updatedFacultyProfile.getDegree());
                faculty.setResearchInterest(updatedFacultyProfile.getResearchInterest());
                faculty.setOfficeLocation(updatedFacultyProfile.getOfficeLocation());
                faculty.setCoursesOffered(updatedFacultyProfile.getCoursesOffered());
                faculty.setPassword(updatedFacultyProfile.getpassword());
                saveFacultyToFile(); // Save changes to the file
                System.out.println("Faculty updated successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void viewStudentInformation(Faculty faculty) {
        LoadFile fileload = new LoadFile();
        // Inform the user about the courses the faculty is teaching and
        // the students that are enrolled in these courses
        System.out.println("This is all of the Courses you teaching!");
        System.out.println(faculty.getCoursesOffered());
        System.out.println("This is all of the student that are enrolled in your Courses!");
        // Fetch all course information based on the courses taught by the faculty
        ArrayList<String> CoursesInformation = fileload.fetchAll("TextData/Courses.txt", faculty.getCoursesOffered(), "Subject Code");

        // Fetch the names and ID of students enrolled in the first course found in CoursesInformation
        ArrayList<String> Name = fileload.fetchAll("TextData/Student.txt", CoursesInformation.get(0), "Name");
        ArrayList<String> ID = fileload.fetchAll("TextData/Student.txt", CoursesInformation.get(0), "Student ID");

        // Iterate through the list of students and display their information
        for (int i = 0; i < Name.size(); i++){
            System.out.print(i + ". ");
            System.out.println(Name.get(i));
            System.out.println(ID.get(i));
            System.out.println(faculty.getCoursesOffered());
        }

    }

    // Adds a new faculty member to the system
    public void addFaculty(Faculty faculty){
        loadFacultyFromFile();
        // Ensure the email is unique
        if (isFacultyEmailUnique(faculty.getemail())) {
            faculties.add(faculty); // Add the faculty to the list
            saveFacultyToFile(); // Save updated list to file
            System.out.println("faculty added successfully!");
        } else {
            System.out.println("faculty email already exists. Please use an other ID.");
        }
    }

    private void saveFacultyToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header for the file
            writer.write("Faculty ID\tName\tDegree\tResearch Interest\tEmail\tOffice Location\tCourses Offered\tPassword\n");
            for (Faculty faculty : faculties) {
                // Write each faculty as a line in the specified file
                writer.write(faculty.getFacultyID() + "\t" +
                        faculty.getName() + "\t" +
                        faculty.getDegree() + "\t" +
                        faculty.getResearchInterest() + "\t" +
                        faculty.getemail() + "\t" +
                        faculty.getOfficeLocation() + "\t" +
                        faculty.getCoursesOffered() + "\t" +
                        faculty.getpassword() + "\t");
                writer.newLine();
            }
            System.out.println("Faculty saved to file.");
        } catch (IOException e) {
            System.out.println("Faculty saving events to file.");
            e.printStackTrace();
        }
    }

    // Checks if a course with the same email already exists
    private boolean isFacultyEmailUnique(String facultyEmail) {
        return faculties.stream().noneMatch(faculty -> faculty.getemail().equals(facultyEmail)); // match the specified values (facultyEmail)
    }

    // Create a unique email for new faculty
    public String Email(String name){
        String[] parts = name.split(" ");
        // Create the email by using the faculty (last name)@university.edu
        String Email = parts[parts.length - 1].toLowerCase() + "@university.edu";
        return Email;
    }

    public void loadFacultyFromFile() {
        String line;
        int row = 0;
        faculties.clear(); // Clear the current faculty list
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {

                if (row >= 1){ // Skip the first row, assuming it contains headers or metadata
                    String[] parts = line.split("\t"); // Split the line into parts using a tab delimiter

                    // Validate the number of elements in the line against the expected size
                    if (parts.length == 8) {
                        Faculty faculty = new Faculty(parts[0], parts[1], parts[2], parts[3],
                                parts[4], parts[5], parts[6], parts[7]);
                        this.faculties.add(faculty); // Add the valid faculty to the list
                    }
                }
                row+=1; // Increment row count
            }
            System.out.println("Faculty loaded from file.");
        } catch (IOException e) { // Handle file errors
            System.out.println("Error loading events from file.");
            e.printStackTrace();
        }
    }

}


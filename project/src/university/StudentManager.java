package university;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

import static java.lang.System.in;
// The StudentManager class handles the management of student records,
public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private String fileName = "TextData/Student.txt";
    private LoadFile fileload = new LoadFile();

    //USER FEATURES

    // Displays the profile information of a given student.
    public void viewprofile(Student student) {
        System.out.println("Profile Photo: " + student.getProfilePhoto());
        System.out.println("Subjects Registered: " + student.getSubjectsRegistered());
        System.out.println("Telephone: " + student.getTelephone());
        System.out.println("Thesis Title: " + student.getThesisTitle());
        System.out.println("Academic Level: " + student.getAcademicLevel());
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getemail());
        System.out.println("Address: " + student.getAddress());
        System.out.println("Student ID: " + student.getStudentID());
        System.out.println("Progress: " + student.getProgress());
        System.out.println("Grades: " + student.getGrades());
        System.out.println("Tuition Status: " + student.getTuition());
        System.out.println("Enrolled Courses: " + student.getRolledCourses());
    }

    public void editprofile(Student student) {
        loadStudentFromFile();
        Scanner scan = new Scanner(System.in);
        // Get the user's choice
        System.out.println("1. Edit the password");
        System.out.println("2. Upload a picture to change the profile photo");
        int choice = scan.nextInt();

        // Handle the user's choice using a switch statement
        switch (choice) {
            case 1:
                System.out.print("Enter your new password: ");
                scan.nextLine(); // Consume newline character
                String newPassword = scan.nextLine();
                student.setPassword(newPassword); // Update the student password
                // Write the updated password to the file, ensuring persistence
                fileload.writeToFile("TextData/Student.txt", "Password", student.getStudentID(), student.getpassword());
                System.out.println("Password updated successfully!");
                break;

            case 2:
                System.out.print("Upload your photo: ");
                String newPhoto = scan.nextLine(); // Capture the photo details
                student.setProfilePhoto(newPhoto);

                // Update the file with the new profile photo
                fileload.writeToFile("TextData/Student.txt", "Profile Photo", student.getStudentID(), student.getProfilePhoto());
                System.out.println("Profile photo updated successfully!");
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
        }

    }

    public void viewenrolledcourses(String Subject) { //displays list of courses with corresponding subject
        CourseManager courseManager = new CourseManager();
        courseManager.subjCourses(Subject);
    }

    public void viewgrades(String grades) {
        //to be determined what grades are represented by
    }

    public void viewtutionstatus(String tuition) {
        //to be determined what how tuition is represented
    }

    // Displays faculty profiles for teachers associated with the student's registered subjects
    public void viewFacultyProfiles(Student student) {
        LoadFile fileload = new LoadFile();
        // Fetch all teacher names associated with the student's registered subjects
        ArrayList<String> teacherList = fileload.fetchAll("TextData/Courses.txt", student.getSubjectsRegistered(), "Teacher Name");
        for (String teacher : teacherList) {
            // Fetch and display faculty details from the faculty data file
            System.out.println(fileload.fetchAnything("TextData/Faculty.txt", teacher, "Name")); // Fetch name
            System.out.println(fileload.fetchAnything1("TextData/Faculty.txt", teacher,"Name", "Email")); // Fetch email
            System.out.println(fileload.fetchAnything1("TextData/Faculty.txt", teacher, "Name","Research Interest")); // Fetch research interest
            System.out.println(fileload.fetchAnything1("TextData/Faculty.txt", teacher, "Name","Office Location")); // Fetch office location
        }
    }

    // Checks if a course with the same ID already exists
    private boolean isStudentIDUnique(String StudentID) {
        return students.stream().noneMatch(student -> student.getStudentID().equals(StudentID)); // match the specified values (StudentID)
    }

    // Updates the details of an existing student in the system
    public void editStudent(String studentID, Student updatedStudentProfile) {
        loadStudentFromFile();
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) { // Find the matching faculty by using their ID
                // Update student details
                student.setStudentName(updatedStudentProfile.getname());
                student.setAddress(updatedStudentProfile.getAddress());
                student.setTelephone(updatedStudentProfile.getTelephone());
                student.setStudentEmail(updatedStudentProfile.getemail());
                student.setAcademicLevel(updatedStudentProfile.getAcademicLevel());
                student.setCurrentSemester(updatedStudentProfile.getCurrentSemester());
                student.setProfilePhoto(updatedStudentProfile.getProfilePhoto());
                student.setSubjectsRegistered(updatedStudentProfile.getSubjectsRegistered());
                student.setThesisTitle(updatedStudentProfile.getThesisTitle());
                student.setProgress(updatedStudentProfile.getProgress());
                student.setPassword(updatedStudentProfile.getpassword());
                student.setGrades(updatedStudentProfile.getGrades());
                student.setTuition(updatedStudentProfile.getTuition());
                student.setRolledcourses(updatedStudentProfile.getRolledCourses());
                saveStudentToFile(); // Save changes to the file
                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    //  Deletes a student's record from the system based on their ID address
    public void deleteStudent(String studentID) {
        loadStudentFromFile();
        students.removeIf(student -> student.getStudentID().equals(studentID));
        saveStudentToFile();
        System.out.println("Student deleted successfully!");
    }

    private void saveStudentToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Student ID\tName\tAddress\tTelephone\tEmail\tAcademic Level\tCurrent Semester\tProfile Photo\tSubjects Registered\tThesis Title\tProgress\tPassword\tGrades\tTuition\tRegistered Courses");
            // Write the header for the file
            writer.newLine();
            for (Student student : students) {
                // Write each student as a line in the specified file
                writer.write(student.getStudentID() + "\t" +
                        student.getname() + "\t" +
                        student.getAddress() + "\t" +
                        student.getTelephone() + "\t" +
                        student.getemail() + "\t" +
                        student.getAcademicLevel() + "\t" +
                        student.getCurrentSemester() + "\t" +
                        student.getProfilePhoto() + "\t" +
                        student.getSubjectsRegistered() + "\t" +
                        student.getThesisTitle() + "\t" +
                        student.getProgress() + "\t" +
                        student.getpassword() + "\t" +
                        student.getGrades() + "\t" +
                        student.getTuition() + "\t" +
                        student.getRolledCourses());
                writer.newLine();
            }
            System.out.println("Student saved to file.");
        } catch (IOException e) {
            System.out.println("Student saving events to file.");
            e.printStackTrace();
        }
    }

    // Adds a new student to the system
    public void addStudent(Student student){
        loadStudentFromFile();
        // Ensure the email is unique
        if (isStudentIDUnique(student.getStudentID())) {
            students.add(student); // Add the student to the list
            saveStudentToFile(); // Save updated list to file
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Student ID already exists. Please use an other ID.");
        }
    }

    public void loadStudentFromFile() {
        String line;
        int row = 0;
        students.clear(); // Clear the current student list
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {

                if (row >= 1){ // Skip the first row, assuming it contains headers or metadata
                    String[] parts = line.split("\t");

                    // Validate the number of elements in the line against the expected size
                    if (parts.length == 15) {
                        Student student = new Student(parts[0], parts[1], parts[2], parts[3],
                                parts[4], parts[5], parts[6], parts[7],parts[8],parts[9],
                                parts[10],parts[11],parts[12],parts[13], parts[14]);
                        this.students.add(student); // Add the valid student to the list
                    }
                }
                row+=1; // Increment row count
            }
            System.out.println("Student loaded from file.");
        } catch (IOException e) { // Handle file errors
            System.out.println("Error loading events from file.");
            e.printStackTrace();
        }
    }

}

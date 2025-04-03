package university;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.in;

public class FacultyManager {
    private List<Faculty> faculties = new ArrayList<>();
    private String fileName = "TextData/Faculty.txt";
    //USER FEATURES
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
        System.out.println("1. : Edit the password");
        System.out.println("2. : Upload a picture to change the profile photo");
        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter your new password: ");
                scan.nextLine();
                faculty.setPassword(scan.nextLine());
                break;

            case 2:
                System.out.print("Upload your photo:");
                //code for photo upload
        }
    }

    public void viewAssignedCourse(Faculty faculty){
        LoadFile fileload = new LoadFile();
        ArrayList<String> CoursesInformation = fileload.fetchAll("TextData/Courses.txt", faculty.getCoursesOffered(), "Subject Code"); //returns subject code
        System.out.println("This is all of the Courses you teaching!");
        for (String Courses : CoursesInformation) {
            System.out.println(fileload.fetchAnything("TextData/Faculty.txt", Courses, "Courses Offered"));
        }
    }

    public void deleteFaculty(String facultyEmail) {
        loadFacultyFromFile();
        faculties.removeIf(faculty -> faculty.getemail().equals(facultyEmail));
        saveFacultyToFile();
        System.out.println("Faculty deleted successfully!");
    }

    public void editFaculty(String facultyEmail, Faculty updatedFacultyProfile) {
        loadFacultyFromFile();
        for (Faculty faculty : faculties) {
            if (faculty.getemail().equals(facultyEmail)) {
                faculty.setFacultyID(updatedFacultyProfile.getFacultyID());
                faculty.setFacultytName(updatedFacultyProfile.getName());
                faculty.setDegree(updatedFacultyProfile.getDegree());
                faculty.setResearchInterest(updatedFacultyProfile.getResearchInterest());
                faculty.setOfficeLocation(updatedFacultyProfile.getOfficeLocation());
                faculty.setCoursesOffered(updatedFacultyProfile.getCoursesOffered());
                faculty.setPassword(updatedFacultyProfile.getpassword());
                saveFacultyToFile();
                System.out.println("Faculty updated successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void viewStudentInformation(Faculty faculty) {
        int num = 1;
        LoadFile fileload = new LoadFile();
        System.out.println("This is all of the Courses you teaching!");
        System.out.println(faculty.getCoursesOffered());
        System.out.println("This is all of the student that are enrolled in your Courses!");
        ArrayList<String> CoursesInformation = fileload.fetchAll("TextData/Courses.txt", faculty.getCoursesOffered(), "Subject Code");

        ArrayList<String> Name = fileload.fetchAll("TextData/Student.txt", CoursesInformation.get(0), "Name");
        ArrayList<String> ID = fileload.fetchAll("TextData/Student.txt", CoursesInformation.get(0), "Student ID");

        for (int i = 0; i < Name.size(); i++){
            System.out.print(i + ". ");
            System.out.println(Name.get(i));
            System.out.println(ID.get(i));
            System.out.println(faculty.getCoursesOffered());
        }

        }

    public void addFaculty(Faculty faculty){
        loadFacultyFromFile();
        if (isFacultyEmailUnique(faculty.getemail())) {
            faculties.add(faculty);
            saveFacultyToFile();
            System.out.println("faculty added successfully!");
        } else {
            System.out.println("faculty email already exists. Please use an other ID.");
        }
    }

    private void saveFacultyToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Faculty ID\tName\tDegree\tResearch Interest\tEmail\tOffice Location\tCourses Offered\tPassword\n");
            for (Faculty faculty : faculties) {
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

    private boolean isFacultyEmailUnique(String facultyEmail) {
        return faculties.stream().noneMatch(faculty -> faculty.getemail().equals(facultyEmail));
    }

    public String Email(String name){
        String[] parts = name.split(" ");
        String Email = parts[parts.length - 1].toLowerCase() + "@university.edu";
        return Email;
    }

    public void loadFacultyFromFile() {
        String line;
        int row = 0;
        faculties.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {

                if (row >= 1){
                    String[] parts = line.split("\t");

                    if (parts.length == 8) {
                        Faculty faculty = new Faculty(parts[0], parts[1], parts[2], parts[3],
                                parts[4], parts[5], parts[6], parts[7]);
                        this.faculties.add(faculty);
                    }
                }
                row+=1;
            }
            System.out.println("Faculty loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading events from file.");
            e.printStackTrace();
        }
    }

}


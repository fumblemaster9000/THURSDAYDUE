package university;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

import static java.lang.System.in;

public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private String fileName = "TextData/Student.txt";

    //USER FEATURES
    public void viewprofile(Student student) { //views student profile
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
    }

    public void editprofile(Student student) {
        Scanner scan = new Scanner(in);
        System.out.println("1. : Edit the password");
        System.out.println("2. : Upload a picture to change the profile photo");
        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                System.out.print("Enter your new password: ");
                scan.nextLine();
                student.setPassword(scan.nextLine());
                break;

            case 2:
                System.out.print("Upload your photo:");
                //code for photo upload
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

    public void viewFacultyProfiles(Student student) {
        LoadFile fileload = new LoadFile();
        ArrayList<String> teacherList = fileload.fetchAll("TextData/Courses.txt", student.getSubjectsRegistered(), "Teacher Name");
        for (String teacher : teacherList) {
            System.out.println(fileload.fetchAnything("TextData/Faculty.txt", teacher, "Name"));
            System.out.println(fileload.fetchAnything1("TextData/Faculty.txt", teacher,"Name", "Email"));
            System.out.println(fileload.fetchAnything1("TextData/Faculty.txt", teacher, "Name","Research Interest"));
            System.out.println(fileload.fetchAnything1("TextData/Faculty.txt", teacher, "Name","Office Location"));
        }
    }

    private boolean isStudentIDUnique(String StudentID) {
        return students.stream().noneMatch(student -> student.getStudentID().equals(StudentID));
    }

    public void editStudent(String studentID, Student updatedStudentProfile) {
        loadStudentFromFile();
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
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
                saveStudentToFile();
                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void deleteStudent(String studentID) {
        loadStudentFromFile();
        students.removeIf(student -> student.getStudentID().equals(studentID));
        saveStudentToFile();
        System.out.println("Student deleted successfully!");
    }

    private void saveStudentToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Student ID\tName\tAddress\tTelephone\tEmail\tAcademic Level\tCurrent Semester\tProfile Photo\tSubjects Registered\tThesis Title\tProgress\tPassword\tGrades\tTuition");
            writer.newLine();
            for (Student student : students) {
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
                        student.getTuition());
                writer.newLine();
            }
            System.out.println("Student saved to file.");
        } catch (IOException e) {
            System.out.println("Student saving events to file.");
            e.printStackTrace();
        }
    }

    public void addStudent(Student student){
        loadStudentFromFile();
        if (isStudentIDUnique(student.getStudentID())) {
            students.add(student);
            saveStudentToFile();
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Student ID already exists. Please use an other ID.");
        }
    }

    public void RegisterStudent(String Name, String Address, String Telephone, String Email, String Academiclevel, String CurrentSmester, String SubjectsRegistered, String Progress){
        LoadFile fileload = new LoadFile();
        int[][] arr = fileload.arraydimension("TextData/Student.txt");
        String checker = "";
        String randID = "";
        while(true) {
            Random random = new Random();
            int randomNumber = random.nextInt(10000);
            if (randomNumber >= 1000) {
                randID = "S2025" + randomNumber; // For numbers 1000 to 9999
            } else if (randomNumber >= 100) {
                randID = "S20250" + randomNumber; // For numbers 100 to 999
            } else if (randomNumber >= 10) {
                randID = "S202500" + randomNumber; // For numbers 10 to 99
            } else {
                randID = "S2025000" + randomNumber; // For numbers 0 to 9
            }

            ArrayList<String> array = fileload.fetchAll("TextData/Student.txt", checker, "Student ID");
            if (array.size() == 0){
                break;
            }
        }

        String[][] array = fileload.readFromFile("TextData/Student.txt");
        for(int i = 0; i < array.length-1 ; i++){
            checker = checker + array[i][0] + "\t" + array[i][1] + "\t" + array[i][2] + "\t" + array[i][3] + "\t" + array[i][4] + "\t" + array[i][5] + "\t" + array[i][6] + "\t" + array[i][7] + "\t" + array[i][8] + "\t" + array[i][9] + "\t" + array[i][10] + "\t" + array[i][11] + "\t"+ array[i][12] + "\t" + array[i][13] + "\n";
        }

        String newString = checker + randID + "\t" + Name + "\t" + Address + "\t" + Telephone + "\t" + Email + "\t" + Academiclevel + "\t" + CurrentSmester + "\t" + "default" + "\t" + SubjectsRegistered + "\t" + "_" + "\t" + Progress + "\t" + "default123";
        fileload.newRow("TextData/Student.txt", newString);
    }

    public void loadStudentFromFile() {
        String line;
        int row = 0;
        students.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {

                if (row >= 1){
                    String[] parts = line.split("\t");

                    if (parts.length == 14) {
                        Student student = new Student(parts[0], parts[1], parts[2], parts[3],
                                parts[4], parts[5], parts[6], parts[7],parts[8],parts[9],
                                parts[10],parts[11],parts[12],parts[13]);
                        this.students.add(student);
                    }}
                row+=1;
            }
            System.out.println("Student loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading events from file.");
            e.printStackTrace();
        }
    }


}

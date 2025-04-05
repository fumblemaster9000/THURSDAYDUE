package university;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//A utility class to handle getting and setting details for key entities
public class Set_Get_Details {
    private LoadFile fileload = new LoadFile();
    private StudentManager studentManager = new StudentManager();
    private FacultyManager facultyManager = new FacultyManager();

    // Fetches the details of a student based on their ID
     Student getStudentDetails(String studentID ){
         try{
             if (studentID == null || studentID.trim().isEmpty()) {
                 System.out.println("Error: Student ID cannot be null or empty.");
                 return null;}
            String name = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Name");
            String address = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Address");
            String Telephone = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Telephone");
            String email = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Email");
            String AcademicLevel = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Academic Level");
            String CurrentSemester = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Current Semester");
            String ProfilePhoto = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Profile Photo");
            String subjectsregistered = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Subjects Registered");
            String thesistitle = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Thesis Title");
            String progress = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Progress");
            String password = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Password");
            String Rolledcourses = fileload.ID_FetchThing("TextData/Student.txt", studentID, "Registered Courses");
            String tuition =  "$5000";
            if (AcademicLevel.equals("Undergraduate")) {
                tuition = "$5000";
            } else if(AcademicLevel.equals("Graduate") ) {
                tuition = "$4000";
            } else {tuition = "$0";}

            String Grades = "A";

            return new Student(studentID, name, address, Telephone,
                    email, AcademicLevel, CurrentSemester, ProfilePhoto,
                    subjectsregistered, thesistitle, progress,
                    password,tuition,Grades, Rolledcourses);
         } catch (Exception e) {
             System.out.println("Error while fetching student details: " + e.getMessage());
             return null;
         }
     }


    // Collects and sets new student details via user input
    Student setStudentDetails(Scanner scanner){
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
        String studentID = randID;
        System.out.println("Enter student's name");
        String name = scanner.nextLine();
        System.out.println("Enter the address of the student");
        String address = scanner.nextLine();
        System.out.println("Enter the student's Telephone");
        String Telephone = scanner.nextLine();
        System.out.println("Enter the student's Email");
        String email = scanner.nextLine();
        System.out.println("Enter the student's academic level");
        String AcademicLevel = scanner.nextLine();
        System.out.println("Enter the current semester the student is in");
        String CurrentSemester = scanner.nextLine();
        System.out.println("Enter the student's profile photo");
        String ProfilePhoto = scanner.nextLine();
        System.out.println("Enter the subjects that the student will be register in");
        String subjectsregistered = scanner.nextLine();
        System.out.println("Enter the Thesis Title that the student is working on");
        String thesistitle = ("\"" + scanner.nextLine() + "\"");
        System.out.println("Enter the progress of the student");
        String progress = scanner.nextLine();
        String password = "default123";
        String tuition = "$5000";
        if (AcademicLevel.equals("Undergraduate")) {
            tuition = "$5000";
        } else if(AcademicLevel.equals("Graduate") ) {
            tuition = "$4000";
        } else {tuition = "$0";}
        String Grades = "0";
        String Rolledcourses = "";
        String courseName = "";
        String section;
        System.out.println("Enter the courses and section numbers to be enrolled by the student, type \"DONE\" to finish");
        while(true){
            System.out.println("Enter CourseName ex. Calculus I");
            courseName = scanner.nextLine();
            System.out.println("Enter Section Number ex. Section 1");
            section = scanner.nextLine();
            if (!courseName.equals("DONE")) {
                Rolledcourses = courseName + "," + section + ";"; //still needs input verif
            }else{
                break; //output should look like (Calculus I,Section 1;History II,Section 0;...;...;.etc)
            }
        }

        return new Student(studentID, name, address, Telephone,
                email, AcademicLevel, CurrentSemester, ProfilePhoto,
                subjectsregistered, thesistitle, progress,
                password,tuition,Grades, Rolledcourses);
    }
    // Fetches the details of a faculty member based on their email
    Faculty getFacultyDetails(String facultyEmail){

        String FacultyID = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Faculty ID");
        String name = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email", "Name");
        String facultyDegree = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Degree");
        String ResearchInterest = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email", "Research Interest");
        String OfficeLocation = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Office Location");
        String CoursesOffered = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Courses Offered");
        String password = fileload.fetchAnything1("TextData/Faculty.txt", facultyEmail, "Email","Password");

        return new Faculty(FacultyID, name, facultyDegree, ResearchInterest,
                facultyEmail, OfficeLocation, CoursesOffered, password);
    }

    // Collects and sets new faculty details via user input
     Faculty setFacultyDetails(Scanner scanner, boolean add){
        System.out.println("Enter faculty ID");
        String FacultyID = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter faculty's name");
        String name = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter the Degree of the faculty");
        String facultyDegree = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter the research interest of the faculty");
        String ResearchInterest = scanner.nextLine();
        scanner.nextLine();
        // setFacultyDetails is not use to add a new faculty,
        // then course email from the user
        if(!add){
            System.out.println("Enter the faculty's Email");
        }
        // if setFacultyDetails is used to add a new faculty
        // then automatically generate a unique email for faculty
        String email = add ? facultyManager.Email(name) : scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter the faculty's office location");
        String OfficeLocation = scanner.nextLine();
        scanner.nextLine();
        System.out.println("Enter the courses offered of the faculty");
        String CoursesOffered = scanner.nextLine();
        scanner.nextLine();
        // setFacultyDetails is not use to add a new faculty,
        // then course password from the user
        if(!add){
            System.out.println("Enter the faculty's password");
        }
        // if setFacultyDetails is used to add a new faculty
        // then automatically set the password to the default
        String password = add ? "default123" : scanner.nextLine();
        scanner.nextLine();

        return new Faculty(FacultyID, name, facultyDegree, ResearchInterest,
                email, OfficeLocation, CoursesOffered, password);
    }

    // Gathers course details from the user
     Course getCourseDetails(Scanner scanner) {
        System.out.println("Enter course code:");
        String courseCode = scanner.nextLine();
        System.out.println("Enter course name:");
        String courseName = scanner.nextLine();
        System.out.println("Enter subject name:");
        String subjectCode = scanner.nextLine();
        System.out.println("Enter section number:");
        String sectionNumber = scanner.nextLine();
        System.out.println("Enter teacher name:");
        String teacherName = scanner.nextLine();
        System.out.println("Enter capacity:");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter lecture time:");
        String lectureTime = scanner.nextLine();
        System.out.println("Enter final exam date/time:");
        String finalExamDateTime = scanner.nextLine();
        System.out.println("Enter location:");
        String location = scanner.nextLine();

        return new Course(courseCode, courseName, subjectCode, sectionNumber,
                capacity, lectureTime, finalExamDateTime, location, teacherName);
    }
    // Collects event details from the user
     Event getEventDetails(Scanner scanner) {
        System.out.println("Enter event name:");
        String eventName = scanner.nextLine();
        System.out.println("Enter event code:");
        String eventCode = scanner.nextLine();
        System.out.println("Enter description:");
        String description = scanner.nextLine();
        System.out.println("Enter header image (or leave blank for default):");
        String headerImage = scanner.nextLine();
        if (headerImage.isEmpty()) {
            headerImage = "default.jpg";
        }
        System.out.println("Enter location:");
        String location = scanner.nextLine();
        System.out.println("Enter date and time:");
        String dateTime = scanner.nextLine();
        System.out.println("Enter capacity:");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter cost:");
        String cost = scanner.nextLine();

         if (eventCode.isEmpty() || eventName.isEmpty() || description.isEmpty() ||
                 location.isEmpty() || dateTime.isEmpty() || cost.isEmpty()) {
             System.out.println("Error: All fields are required except the header image.");
             return null;
         }

         return new Event(eventCode,eventName, description,  location, dateTime, capacity, cost, headerImage);
    }
}

package university;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Manages the creation, modification, and deletion of courses
public class CourseManager {

    private List<Course> courses = new ArrayList<>();
    private String fileName = "TextData/Courses.txt";

    public void addCourse(Course course) {
        loadCoursesFromFile(); // Load existing courses before adding a new one
        if (isCourseUnique(course.getCourseName(), course.getSectionNumber())) { //get method of course
            courses.add(course); // Add the course to the internal list
            saveCoursesToFile(); // Save updates to persistent storage
            System.out.println("Course added successfully!");
        } else {
            System.out.println("Course code already exists. Please use a unique code.");
        }
    }

    public void editCourse(String courseName, String sectionNumber, Course updatedCourse) {
        loadCoursesFromFile(); // Load existing courses before editing
        for (Course course : courses) {
            // Find the course by name and section number
            if (course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)) {
                // Use setters to update the course attributes
                course.setCourseCode(updatedCourse.getCourseCode());
                course.setCourseName(updatedCourse.getCourseName());
                course.setSubjectCode(updatedCourse.getSubjectCode());
                course.setSectionNumber(updatedCourse.getSectionNumber());
                course.setTeacherName(updatedCourse.getTeacherName());
                course.setCapacity(updatedCourse.getCapacity());
                course.setLectureTime(updatedCourse.getLectureTime());
                course.setFinalExamDateTime(updatedCourse.getFinalExamDateTime());
                course.setLocation(updatedCourse.getLocation());

                saveCoursesToFile();
                System.out.println("Course updated successfully!");
                return;
            }
        }
        System.out.println("Course not found.");
    }

    // Deletes a course from the system.
    public void deleteCourse(String courseName, String sectionNumber) {
        loadCoursesFromFile(); // Load existing courses before deleting
        // Removes any course from the list where the course name and section number
        courses.removeIf(course -> course.getCourseName().equals(courseName) &&
                course.getSectionNumber().equals(sectionNumber)); // match the specified values (courseName and sectionNumber)
        saveCoursesToFile();
        System.out.println("Course deleted successfully!");
    }

    public void subjCourses(String subjname) { //needs a check for subject code
        loadCoursesFromFile(); // Load existing courses before viewing
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course course : courses) {
                if (course.getSubjectCode().equals(subjname)) {
                    System.out.println(course);
                }
            }
        }
    }

    // Views all the courses currently managed by the system
    public void viewCourses() {
        loadCoursesFromFile(); // Load existing courses before viewing
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course course : courses) {
                System.out.println(course);
            }
        }
    }

    // Assigns a faculty member to a course.
    public void assignFaculty(String courseName, String sectionNumber, String teacherName) {
        loadCoursesFromFile(); // Load existing courses before assigning faculty
        for (Course course : courses) {
            if (course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)) {
                course.setTeacherName(teacherName); // Assign the teacher
                saveCoursesToFile();
                System.out.println("Faculty assigned successfully!");
                return;
            }
        }
        System.out.println("Course not found.");
    }

    public void manageEnrollments(String courseName, String sectionNumber) {
        // Implementation for managing enrollments (e.g., view enrolled students, add/remove students)
    }

    // Checks if a course with the same name and section number already exists
    private boolean isCourseUnique(String courseName, String sectionNumber) {
        return courses.stream().noneMatch(course -> course.getCourseName().equals(courseName) &&
                course.getSectionNumber().equals(sectionNumber)); // match the specified values (courseName and sectionNumber)
    }

    // Write the course list to a file
    private void saveCoursesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header for the file
            writer.write("Course Code\tCourse Name\tSubject Code\tSection Number\tCapacity\tLecture Time\tFinal Exam Date/Time\tLocation\tTeacher Name\n");
            // Write each course as a line in the specified file
            for (Course course : courses) {
                writer.write(course.getCourseCode() + "\t" +
                        course.getCourseName() + "\t" +
                        course.getSubjectCode() + "\t" +
                        course.getSectionNumber() + "\t" +
                        course.getCapacity() + "\t" +
                        course.getLectureTime() + "\t" +
                        course.getFinalExamDateTime() + "\t" +
                        course.getLocation() + "\t" +
                        course.getTeacherName());
                writer.newLine();
            }
            System.out.println("Courses saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving courses to file.");
            e.printStackTrace();
        }
    }

    public void loadCoursesFromFile() { //loads all courses
        String line;
        int row = 0;
        courses.clear(); // Clear the current list to avoid duplication
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {

                if (row >= 1) { // Skip the first row, assuming it contains headers or metadata
                    String[] parts = line.split("\t"); // Split the line into parts using a tab delimiter

                    // Validate the number of elements in the line against the expected size
                    if (parts.length == 9) {
                        Course course = new Course(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5],
                                parts[6], parts[7], parts[8]);
                        this.courses.add(course); // Add the valid course object to the list
                    }
                }
                row += 1; // Increment row count
            }
            System.out.println("Courses loaded from file.");
        } catch (IOException e) { // Handle file errors
            System.out.println("Error loading courses from file.");
            e.printStackTrace();
        }
    }

    public List<Course> getCourses() {
        return courses;
    }

    public static List<String[]> parseStringToPairs(String input) {
        List<String[]> pairs = new ArrayList<>();

        // Split the input by semicolon to get individual pairs
        String[] entries = input.split(";");

        for (String entry : entries) {
            // Check if the entry is not empty (to handle trailing semicolons)
            if (!entry.isEmpty()) {
                // Split each pair by the comma
                String[] pair = entry.split(",");
                if (pair.length == 2) { // Ensure it has exactly 2 elements
                    pairs.add(pair);
                }
            }
        }
	    return pairs;
    }

    // Fetches enrolled courses for a student by their ID
    public List<String[]> getenrolled(String studentID) {
        LoadFile loadFile = new LoadFile();
        String Line = loadFile.ID_FetchThing("TextData/Student.txt", studentID, "Registered Courses");
        return parseStringToPairs(Line);
    }

    // Displays the courses a specific student is enrolled in
    public void viewEnrolledCourses(String studentID) {
        // Get the list of enrolled courses as pairs
        List<String[]> enrolledCourses = getenrolled(studentID);

        if (enrolledCourses == null || enrolledCourses.isEmpty()) {
            System.out.println("No courses found for student ID: " + studentID);
            return;
        }

        System.out.println("Enrolled courses for student ID: " + studentID);
        for (String[] pair : enrolledCourses) {
            // Ensure each pair has 2 elements (course name and section number)
            if (pair.length == 2) {
                System.out.println("Course Name: " + pair[0] + ", Section Number: " + pair[1]);
            } else {
                System.out.println("Invalid course pair found.");
            }
        }
    }

    public void courseRegisterStudent(String studentID){
        LoadFile loadFile = new LoadFile();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the course name for the student to be enrolled to: ");
        String courseName = scan.nextLine();
        System.out.println("Enter the section to enroll the student: ");
        String section = scan.nextLine();

        // Fetch the student's currently registered courses
        String Line = loadFile.ID_FetchThing("TextData/Student.txt", studentID, "Registered Courses");

        // Check if the student has prior registrations
        if (!Line.equals("na")) {
            if (Line.contains(courseName + "," + section)) {
                System.out.println("Student already enrolled in this course");
                return; // Exit if already registered for the same course and section
            } else if (Line.contains(courseName)) {
                //replace section at course *complicated
            } else {
                // Append the new course and section
                Line = Line + courseName + "," + section + ";";
            }
        }
        else{
            // No prior registrations; add the first course and section
            Line = courseName + "," + section + ";";
        }

        // Update the student's registration data in the file
        loadFile.writeToFile("TextData/Student.txt", "Registered Courses", studentID, Line);
    }
}



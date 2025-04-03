package university;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    private List<Course> courses = new ArrayList<>();
    private String fileName = "TextData/Courses.txt";

    public void addCourse(Course course) {
        loadCoursesFromFile(); // Load existing courses before adding a new one
        if (isCourseUnique(course.getCourseName(),course.getSectionNumber())) { //get method of course
            courses.add(course);
            saveCoursesToFile();
            System.out.println("Course added successfully!");
        } else {
            System.out.println("Course code already exists. Please use a unique code.");
        }
    }

    public void editCourse(String courseName, String sectionNumber, Course updatedCourse) {
        loadCoursesFromFile(); // Load existing courses before editing
        for (Course course : courses) {
            if(course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)) {
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


    public void deleteCourse(String courseName, String sectionNumber) {
        loadCoursesFromFile(); // Load existing courses before deleting
        courses.removeIf(course -> course.getCourseName().equals(courseName) &&
                course.getSectionNumber().equals(sectionNumber));
        saveCoursesToFile();
        System.out.println("Course deleted successfully!");
    }

    public void subjCourses(String subjname) { //needs a check for subject code
        loadCoursesFromFile(); // Load existing courses before viewing
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course course : courses) {
                if (course.getSubjectCode().equals(subjname)){
                    System.out.println(course);}
            }
        }
    }

    public void viewCourses() { //needs a check for subject code
        loadCoursesFromFile(); // Load existing courses before viewing
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course course : courses) {
                System.out.println(course);
            }
        }
    }

    public void assignFaculty(String courseName, String sectionNumber, String teacherName) {
        loadCoursesFromFile(); // Load existing courses before assigning faculty
        for (Course course : courses) {
            if (course.getCourseName().equals(courseName) && course.getSectionNumber().equals(sectionNumber)) {
                course.setTeacherName(teacherName);
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

    private boolean isCourseUnique(String courseName, String sectionNumber) {
        return courses.stream().noneMatch(course -> course.getCourseName().equals(courseName) &&
                course.getSectionNumber().equals(sectionNumber));
    }

    private void saveCoursesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Course Code\tCourse Name\tSubject Code\tSection Number\tCapacity\tLecture Time\tFinal Exam Date/Time\tLocation\tTeacher Name\n");
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

                if (row >= 1) {
                    String[] parts = line.split("\t");

                    if (parts.length == 9) {
                        Course course = new Course(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5],
                                parts[6], parts[7], parts[8]);
                        this.courses.add(course);
                    }
                }
                row+=1;
            }
            System.out.println("Courses loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading courses from file.");
            e.printStackTrace();
        }
    }

    public List<Course> getCourses(){
        return courses;
    }
}

package university;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    private List<Course> courses = new ArrayList<>();
    private String fileName = "TextData/Courses.txt";
    public void addCourse(Course course) {
        loadCoursesFromFile(); // Load existing courses before adding a new one
        if (isCourseCodeUnique(course.getCourseCode())) { //get method of course
            courses.add(course);
            saveCoursesToFile();
            System.out.println("Course added successfully!");
        } else {
            System.out.println("Course code already exists. Please use a unique code.");
        }
    }

    public void editCourse(String courseCode, Course updatedCourse) {
        loadCoursesFromFile(); // Load existing courses before editing
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
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


    public void deleteCourse(String courseCode) {
        loadCoursesFromFile(); // Load existing courses before deleting
        courses.removeIf(course -> course.getCourseCode().equals(courseCode));
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

    public void assignFaculty(String courseCode, String teacherName) {
        loadCoursesFromFile(); // Load existing courses before assigning faculty
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                course.setTeacherName(teacherName);
                saveCoursesToFile();
                System.out.println("Faculty assigned successfully!");
                return;
            }
        }
        System.out.println("Course not found.");
    }

    public void manageEnrollments(String courseCode) {
        // Implementation for managing enrollments (e.g., view enrolled students, add/remove students)
    }

    private boolean isCourseCodeUnique(String courseCode) {
        return courses.stream().noneMatch(course -> course.getCourseCode().equals(courseCode));
    }

    private void saveCoursesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Course Code\tCourse Name\tSubject Code\tSection Number\tCapacity\tLecture Time\tFinal Exam Date/Time\tLocation\tTeacher Name");
            for (Course course : courses) {
                writer.write(course.getCourseCode() + "\t" +
                        course.getCourseName() + "\t" +
                        course.getSubjectCode() + "\t" +
                        course.getSectionNumber() + "\t" +
                        course.getTeacherName() + "\t" +
                        course.getCapacity() + "\t" +
                        course.getLectureTime() + "\t" +
                        course.getFinalExamDateTime() + "\t" +
                        course.getLocation());
                writer.newLine();
            }
            System.out.println("Courses saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving courses to file.");
            e.printStackTrace();
        }
    }

    public void loadCoursesFromFile() { //loads all courses
        courses.clear(); // Clear the current list to avoid duplication
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");

                if (i > 0) {
                    if (parts.length == 9) {
                        Course course = new Course(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5],
                                parts[6], parts[7], parts[8]);
                        courses.add(course);
                    }
                }
                i = 1;
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

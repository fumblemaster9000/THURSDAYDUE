package university;

import java.util.ArrayList;
import java.util.List;

public class SubjectManager {
    private LoadFile loadFile = new LoadFile();
    private String fileName = "out/production/Final_project/Shitbox/Subjects.txt";

    public boolean isSubjectCodeUnique(String subjectCode, List<Subject> subjects) {
        return subjects.stream().noneMatch(subject -> subject.getSubjectCode().equals(subjectCode));
    }

    public void addSubject(String subjectName, String subjectCode) {
        List<Subject> subjects = loadSubjectsFromFile();
        System.out.println("Loaded subjects: " + subjects.size()); // Debugging output
        if (isSubjectCodeUnique(subjectCode, subjects)) {
            subjects.add(new Subject(subjectName, subjectCode));
            System.out.println("Subject added: " + subjectName + ", " + subjectCode); // Debugging output
            saveSubjectsToFile(subjects);
            System.out.println("Subject added successfully!");
        } else {
            System.out.println("Subject code already exists. Please use a unique code.");
        }
    }

    public void editSubject(String subjectCode, String newSubjectName) {
        List<Subject> subjects = loadSubjectsFromFile();
        for (Subject subject : subjects) {
            if (subject.getSubjectCode().equals(subjectCode)) {
                subject.setSubjectName(newSubjectName);
                saveSubjectsToFile(subjects);
                System.out.println("Subject updated successfully!");
                return;
            }
        }
        System.out.println("Subject not found.");
    }

    public void deleteSubject(String subjectCode) {
        List<Subject> subjects = loadSubjectsFromFile();
        subjects.removeIf(subject -> subject.getSubjectCode().equals(subjectCode));
        saveSubjectsToFile(subjects);
        System.out.println("Subject deleted successfully!");
    }

    public void viewSubjects() {
        List<Subject> subjects = loadSubjectsFromFile();
        if (subjects.isEmpty()) {
            System.out.println("No subjects available.");
        } else {
            for (Subject subject : subjects) {
                System.out.println(subject);
            }
        }
    }

    private void saveSubjectsToFile(List<Subject> subjects) {
        StringBuilder content = new StringBuilder();

        //loadFile.writeToFile(fileName, Subject, userID, replacement);
    }

    public List<Subject> loadSubjectsFromFile() {
        List<Subject> subjects = new ArrayList<>();
        String[][] arr = loadFile.readFromFile(fileName);
        for (String[] row : arr) {
            if (row.length == 2) {
                subjects.add(new Subject(row[0], row[1]));
            }
        }
        System.out.println("Subjects loaded from file: " + subjects.size()); // Debugging output
        return subjects;
    }
}

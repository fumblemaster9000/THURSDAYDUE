package university;

import java.util.ArrayList;
import java.util.List;

public class SubjectManager {
	private LoadFile loadFile = new LoadFile();
	private String fileName = "TextData/Subjects.txt";

	public SubjectManager() {
	}

	public boolean isSubjectCodeUnique(String subjectCode, List<Subject> subjects) {
		for (int i = 0; i < subjects.size() - 1; i++) { // Skip the last element
			Subject subject = subjects.get(i);
			if (subject != null && subject.getSubjectCode().equals(subjectCode)) {
				return false; // Found a match, not unique
			}
		}

		// Check the last element separately
		if (subjects.get(subjects.size() - 1) == null) {
			throw new NullPointerException("The last element is null.");
		}

		return true; // No matches found, subjectCode is unique
	}

	public void addSubject(String subjectName, String subjectCode) {
		List<Subject> subjects = this.loadSubjectsFromFile();
		System.out.println("Loaded subjects: " + subjects.size());
		if (this.isSubjectCodeUnique(subjectCode, subjects)) {
			subjects.remove(subjects.size()-1);
			subjects.add(new Subject(subjectName, subjectCode));
			System.out.println("Subject added: " + subjectName + ", " + subjectCode);
			this.saveSubjectsToFile(subjects);
			System.out.println("Subject added successfully!");
		} else {
			System.out.println("Subject code already exists. Please use a unique code.");
		}

	}

	public void editSubject(String subjectCode, String newSubjectName) {
		List<Subject> subjects = this.loadSubjectsFromFile();

		for(Subject subject : subjects) {
			if (subject.getSubjectCode().equals(subjectCode)) {
				subject.setSubjectName(newSubjectName);
				this.saveSubjectsToFile(subjects);
				System.out.println("Subject updated successfully!");
				return;
			}
		}

		System.out.println("Subject not found.");
	}

	public void deleteSubject(String subjectCode) {
		List<Subject> subjects = this.loadSubjectsFromFile();
		// Use a copy of the list to avoid ConcurrentModificationException
		for (Subject subject : new ArrayList<>(subjects)) {
			if (subject != null && subject.getSubjectCode().equals(subjectCode)) {
				subjects.remove(subject); // Remove the subject directly
				subjects.remove(subjects.size()-1);
				break;
			}
		}

		// Save the updated list back to the file
		this.saveSubjectsToFile(subjects);

		System.out.println("Subject deleted successfully!");
	}

	public void viewSubjects() {
		List<Subject> subjects = this.loadSubjectsFromFile();
		if (subjects.isEmpty()) {
			System.out.println("No subjects available.");
		} else {
			for (Subject subject : subjects) {
				System.out.println(subject);
			}
		}
	}

	public void adminviewSubjects() {
		List<Subject> subjects = this.loadSubjectsFromFile();
		if (subjects.isEmpty()) {
			System.out.println("No subjects available.");
		} else {
			for(Subject subject : subjects) {
				System.out.println(subject);
			}
		}

	}

	private void saveSubjectsToFile(List<Subject> subjects) {
		StringBuilder content = new StringBuilder();
		for(Subject subject : subjects) {
			content.append(subject.getSubjectName()).append("\t").append(subject.getSubjectCode()).append("\n");
		}
		loadFile.newRow("TextData/Subjects.txt", content.toString());
	}

	public List<Subject> loadSubjectsFromFile() {
		List<Subject> subjects = new ArrayList();
		String[][] arr = this.loadFile.readFromFile(this.fileName);

		for(String[] row : arr) {
			if (row.length == 2) {
				subjects.add(new Subject(row[0], row[1]));
			}
		}

		System.out.println("Subjects loaded from file: " + subjects.size());
		return subjects;
	}
}
package university;

import java.util.ArrayList;
import java.util.List;
// The SubjectManager class handles the management of faculty records,
public class SubjectManager {
	private LoadFile loadFile = new LoadFile();
	private String fileName = "TextData/Subjects.txt";

	public SubjectManager() {
	}

	public boolean isSubjectCodeUnique(String subjectCode, List<Subject> subjects) {
		// Iterate through the list, skipping the last element
		for (int i = 0; i < subjects.size() - 1; i++) { // Skip the last element
			Subject subject = subjects.get(i);
			// Check if this subject's code matches the input

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
		if (this.isSubjectCodeUnique(subjectCode, subjects)) { // Check if the subject code is unique
			subjects.remove(subjects.size()-1);

			// Create and add a new Subject with the provided name and code
			subjects.add(new Subject(subjectName, subjectCode));
			System.out.println("Subject added: " + subjectName + ", " + subjectCode);
			this.saveSubjectsToFile(subjects); // Save the updated subjects list back to the file
			System.out.println("Subject added successfully!");
		} else {
			// Notify the user that the subject code is not unique
			System.out.println("Subject code already exists. Please use a unique code.");
		}

	}

	public void editSubject(String subjectCode, String newSubjectName) {
		List<Subject> subjects = this.loadSubjectsFromFile();

		// Iterate through the subjects to find the one to edit
		for(Subject subject : subjects) {
			if (subject.getSubjectCode().equals(subjectCode)) { // Check if a subject with the matching code is found
				subject.setSubjectName(newSubjectName); // Update the subject's name
				this.saveSubjectsToFile(subjects); // Save the updated list back to the file
				// Display confirmation message and exit
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
				subjects.remove(subjects.size()-1); // Remove the last element from the list
				break;
			}
		}

		// Save the updated list back to the file
		this.saveSubjectsToFile(subjects);

		System.out.println("Subject deleted successfully!");
	}

	public void viewSubjects() {
		List<Subject> subjects = this.loadSubjectsFromFile();
		// Check if the list is empty and print a message if there are no subjects
		if (subjects.isEmpty()) {
			System.out.println("No subjects available.");
		} else {
			// Iterate over the list and print each subject
			for (Subject subject : subjects) {
				System.out.println(subject);
			}
		}
	}


	// Write the subjects list to a file
	private void saveSubjectsToFile(List<Subject> subjects) {
		StringBuilder content = new StringBuilder();
		// Iterate over each subject in the list of subjects
		for(Subject subject : subjects) {
			// Append the subject's name and code, separated by a tab, followed by a new line
			content.append(subject.getSubjectName()).append("\t").append(subject.getSubjectCode()).append("\n");
		}
		// Write the built content string to the file using the LoadFile class
		loadFile.newRow("TextData/Subjects.txt", content.toString());
	}

	public List<Subject> loadSubjectsFromFile() {
		List<Subject> subjects = new ArrayList();
		// Read a 2D array from the file
		String[][] arr = this.loadFile.readFromFile(this.fileName);

		// Iterate through each row in the array
		for(String[] row : arr) {
			// If the row has exactly two elements (a valid subject)
			if (row.length == 2) {
				// Create a new Subject object and add it to the list
				subjects.add(new Subject(row[0], row[1]));
			}
		}

		// Print the number of loaded subjects
		System.out.println("Subjects loaded from file: " + subjects.size());
		return subjects; // Return the list of subjects

	}
}
package university;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventManager {
    private List<Event> events = new ArrayList<>();
    private String fileName = "TextData/Events.txt";

    // Adds a new event to the list and saves it to the file.
    public void addEvent(Event event) {
        loadEventsFromFile(); // Load existing events
        if (isEventCodeUnique(event.getEventCode())) { // Check for uniqueness
            events.add(event); // Add the new event
            saveEventsToFile(); // Save updated list to file
            System.out.println("Event added successfully!");
        } else {
            System.out.println("Event code already exists. Please use a unique code.");
        }
    }

    // Updates an existing event based on its event code
    public void editEvent(String eventCode, Event updatedEvent) {
        loadEventsFromFile();
        for (Event event : events) {
            if (event.getEventCode().equals(eventCode)) { // Find the matching event by using its code
                // Update event details
                event.setDescription(updatedEvent.getDescription());
                event.setHeaderImage(updatedEvent.getHeaderImage());
                event.setLocation(updatedEvent.getLocation());
                event.setDateTime(updatedEvent.getDateTime());
                event.setCapacity(updatedEvent.getCapacity());
                event.setCost(updatedEvent.getCost());
                saveEventsToFile(); // Save changes to the file
                System.out.println("Event updated successfully!");
                return;
            }
        }
        System.out.println("Event not found.");
    }

    // Deletes an event from the list based on its event code
    public void deleteEvent(String eventCode) {
        loadEventsFromFile();
        events.removeIf(event -> event.getEventCode().equals(eventCode)); // Remove matching event
        saveEventsToFile(); // Save changes to the file
        System.out.println("Event deleted successfully!");
    }

    // Displays all events currently available
    public void viewEvents() {
        loadEventsFromFile();
        if (events.isEmpty()) {
            System.out.println("No events available."); // Show message if no events exist
        } else {
            for (Event event : events) {
                System.out.println(event); // Print event details
            }
        }
    }

    // Manages student registrations for a specific event
    public void manageRegistrations(String eventCode, String studentId, String name, String fileName) {
        loadEventsFromFile();
        for (Event event : events) {
            if (event.getEventCode().equals(eventCode)) { // Find the matching event by code
                for (int i = 0; i < event.getRegisteredStudents().size(); i++){
                    if (event.getRegisteredStudents().get(i).equals(name)){
                        System.out.println("You are already registered");
                        return;
                    }
                }
                if (event.registerStudent(studentId, fileName)) {
                    saveEventsToFile();
                    System.out.println("You are registered successfully!");
                } else {
                    System.out.println("Registration failed. Event is full.");
                }
                return;
            }
        }
        System.out.println("Event not found.");
    }

    // Retrieves the list of events currently stored in the system
    public List<Event> getEvents() {
        loadEventsFromFile(); // Load current events
        return events; // Return the list of events
    }

    // Checks if the given event code is unique within the list of events
    private boolean isEventCodeUnique(String eventCode) {
        return events.stream().noneMatch(event -> event.getEventCode().equals(eventCode)); // match the specified values (eventCode)
    }

    private void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the header for the file
            writer.write("Event Code\tEvent Name\tDescription\tLocation\tDate and Time\tCapacity\tCost\tHeader Image\tRegistered Students");
            writer.newLine();
            for (Event event : events) {
                // Write each event as a line in the specified file
                writer.write(event.getEventCode() + "\t" +
                        event.getEventName() + "\t" +
                        event.getDescription() + "\t" +
                        event.getLocation() + "\t" +
                        event.getDateTime() + "\t" +
                        event.getCapacity() + "\t" +
                        event.getCost() + "\t" +
                        event.getHeaderImage() + "\t" +
                        String.join(",", event.getRegisteredStudents()));
                writer.newLine();
            }
            System.out.println("Events saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving events to file.");
            e.printStackTrace();
        }
    }

    public void loadEventsFromFile() {
        String line;
        int row = 0;
	    events.clear(); // Clear the current list to avoid duplication
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {

                if (row >= 1){ // Skip the first row, assuming it contains headers or metadata
                    String[] parts = line.split("\t"); // Split the line into parts using a tab delimiter

                    // Validate the number of elements in the line against the expected size
                    if (parts.length == 9) {
                    Event event = new Event(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), parts[6], parts[7]);
                    event.setRegisteredStudents(new ArrayList<>(Arrays.asList(parts[8].split(","))));
                    this.events.add(event); // Add the valid event object to the list

                    }}
                row+=1; // Increment row count

            }
            System.out.println("Events loaded from file.");
        } catch (IOException e) { // Handle file errors
            System.out.println("Error loading events from file.");
            e.printStackTrace();
        }
    }
}

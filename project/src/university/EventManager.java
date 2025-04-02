package university;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventManager {
    private List<Event> events = new ArrayList<>();
    private String fileName = "TextData/Events.txt";

    public void addEvent(Event event) {
        loadEventsFromFile();
        if (isEventCodeUnique(event.getEventCode())) {
            events.add(event);
            saveEventsToFile();
            System.out.println("Event added successfully!");
        } else {
            System.out.println("Event code already exists. Please use a unique code.");
        }
    }

    public void editEvent(String eventCode, Event updatedEvent) {
        loadEventsFromFile();
        for (Event event : events) {
            if (event.getEventCode().equals(eventCode)) {
                event.setEventName(updatedEvent.getEventName());
                event.setDescription(updatedEvent.getDescription());
                event.setHeaderImage(updatedEvent.getHeaderImage());
                event.setLocation(updatedEvent.getLocation());
                event.setDateTime(updatedEvent.getDateTime());
                event.setCapacity(updatedEvent.getCapacity());
                event.setCost(updatedEvent.getCost());
                saveEventsToFile();
                System.out.println("Event updated successfully!");
                return;
            }
        }
        System.out.println("Event not found.");
    }

    public void deleteEvent(String eventCode) {
        loadEventsFromFile();
        events.removeIf(event -> event.getEventCode().equals(eventCode));
        saveEventsToFile();
        System.out.println("Event deleted successfully!");
    }

    public void viewEvents() {
        loadEventsFromFile();
        if (events.isEmpty()) {
            System.out.println("No events available.");
        } else {
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    public void manageRegistrations(String eventCode, String studentId, String name, String fileName) {
        loadEventsFromFile();
        for (Event event : events) {
            if (event.getEventCode().equals(eventCode)) {
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

    public List<Event> getEvents() {
        loadEventsFromFile();
        return events;
    }

    private boolean isEventCodeUnique(String eventCode) {
        return events.stream().noneMatch(event -> event.getEventCode().equals(eventCode));
    }

    private void saveEventsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Event Code\tEvent Name\tDescription\tLocation\tDate and Time\tCapacity\tCost\tHeader Image\tRegistered Students");
            writer.newLine();
            for (Event event : events) {
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
	    events.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((line = reader.readLine()) != null) {

                if (row >= 1){
                String[] parts = line.split("\t");

                if (parts.length == 9) {
                    Event event = new Event(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]), parts[6], parts[7]);
                    event.setRegisteredStudents(new ArrayList<>(Arrays.asList(parts[8].split(","))));
                    this.events.add(event);
                }}
                row+=1;
            }
            System.out.println("Events loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading events from file.");
            e.printStackTrace();
        }
    }
}

package university;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Event> events = new ArrayList<>();
    private String fileName = "C:\\Users\\admin\\Desktop\\School\\Coding\\ENGG1420\\Final_project\\EventsDatabase.txt";

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

    public void manageRegistrations(String eventCode, String studentId) {
        loadEventsFromFile();
        for (Event event : events) {
            if (event.getEventCode().equals(eventCode)) {
                if (event.registerStudent(studentId)) {
                    saveEventsToFile();
                    System.out.println("Student registered successfully!");
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
            for (Event event : events) {
                writer.write(event.getEventName() + "\t" +
                        event.getEventCode() + "\t" +
                        event.getDescription() + "\t" +
                        event.getHeaderImage() + "\t" +
                        event.getLocation() + "\t" +
                        event.getDateTime() + "\t" +
                        event.getCapacity() + "\t" +
                        event.getCost() + "\t" +
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
        events.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 9) {
                    Event event = new Event(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                            Integer.parseInt(parts[6]), parts[7]);
                    event.setRegisteredStudents(new ArrayList<>(List.of(parts[8].split(","))));
                    events.add(event);
                }
            }
            System.out.println("Events loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading events from file.");
            e.printStackTrace();
        }
    }
}

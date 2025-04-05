package university;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventName; // Name of the event
    private String eventCode; // Unique code for the event
    private String description; // Description of the event
    private String headerImage; // Path to the header image
    private String location; // Event location
    private String dateTime; // Date and time of the event
    private int capacity; // Maximum number of participants the event can accommodate
    private String cost; // Cost of attending the event
    private List<String> registeredStudents = new ArrayList<>(); // List of registered participants' names


    // Constructor to initialize the Event object
    public Event(String eventCode, String eventName, String description, String location,
                 String dateTime, int capacity, String cost, String headerImage) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.cost = cost;

        // Assign default image if no header image is provided
        this.headerImage = headerImage.isEmpty() ? "default.jpg" : headerImage;
    }

    // Getter and Setter methods
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventCode() {
        return eventCode;
    }
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeaderImage() {
        return headerImage;
    }
    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage.isEmpty() ? "default.jpg" : headerImage;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<String> getRegisteredStudents() {
        return registeredStudents;
    }
    public void setRegisteredStudents(List<String> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    // Add a student to the registered list
    public boolean registerStudent(String studentId, String fileName) {
        // LoadFile utility to fetch student details from file
        LoadFile load = new LoadFile();

        // Validate if the event has remaining capacity
        if (capacity > getRegisteredStudents().size() - 1) {
            // Fetch and add the student's name to the registered list
            registeredStudents.add(load.ID_FetchThing(fileName, studentId, "Name"));
            setRegisteredStudents(registeredStudents);
            return true;
        }
        return false;
    }

    public boolean registerFaculty(String FacultyId) {
        LoadFile load = new LoadFile();
        // Validate if the event has remaining capacity
        if (capacity > getRegisteredStudents().size()) {
            // Fetch and add the faculty's name to the registered list
            String facultyName = load.ID_FetchThing("TextData/Faculty.txt", FacultyId, "Name");
            if (facultyName == null || facultyName.isEmpty()) {
                System.out.println("Error: Unable to fetch faculty details.");
                return false;
            }

            registeredStudents.add(facultyName); // Add the faculty to the list
            setRegisteredStudents(registeredStudents); // Update the list on the event
            return true;
        } else {
            System.out.println("Error: The event has reached its maximum capacity.");
            return false;
        }
    }


    @Override
    public String toString() {
        return "Event [Name=" + eventName + ", Code=" + eventCode + ", Description=" + description +
                ", Header Image=" + headerImage + ", Location=" + location + ", Date and Time=" + dateTime +
                ", Capacity=" + capacity + ", Cost=" + cost + ", Registered Students=" + registeredStudents + "]";
    }


}

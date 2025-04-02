package university;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventName;
    private String eventCode;
    private String description;
    private String headerImage;
    private String location;
    private String dateTime;
    private int capacity;
    private String cost;
    private List<String> registeredStudents = new ArrayList<>();

    // Constructor
    public Event(String eventCode, String eventName, String description, String location, String dateTime, int capacity, String cost, String headerImage) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.cost = cost;
        this.headerImage = headerImage.isEmpty() ? "default.jpg" : headerImage;
    }

    // Getters and Setters
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
        LoadFile load = new LoadFile();
        if (capacity > getRegisteredStudents().size() - 1) {
            registeredStudents.add(load.ID_FetchThing(fileName, studentId, "Name"));
            setRegisteredStudents(registeredStudents);
            return true;
        }
        return false;
    }

    public boolean registerFaculty(String FacultyId) {
        LoadFile load = new LoadFile();
        if (capacity > getRegisteredStudents().size() - 1) {
            registeredStudents.add(load.ID_FetchThing("TextData/Faculty.txt", FacultyId, "Name"));
            setRegisteredStudents(registeredStudents);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Event [Name=" + eventName + ", Code=" + eventCode + ", Description=" + description +
                ", Header Image=" + headerImage + ", Location=" + location + ", Date and Time=" + dateTime +
                ", Capacity=" + capacity + ", Cost=" + cost + ", Registered Students=" + registeredStudents + "]";
    }


}

package university;

import java.util.List;

public class FacultyProfile {
    private String name;
    private String email;
    private String profilePhoto;
    private String degree;
    private String researchInterest;
    private List<String> coursesOffered;
    private String officeLocation;
    private String facultyId;  // Added facultyId attribute

    public FacultyProfile(String name, String email, String profilePhoto, String degree, String researchInterest,
                          List<String> coursesOffered, String officeLocation, String facultyId) {
        this.name = name;
        this.email = email;
        this.profilePhoto = profilePhoto;
        this.degree = degree;
        this.researchInterest = researchInterest;
        this.coursesOffered = coursesOffered;
        this.officeLocation = officeLocation;
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getResearchInterest() {
        return researchInterest;
    }

    public void setResearchInterest(String researchInterest) {
        this.researchInterest = researchInterest;
    }

    public List<String> getCoursesOffered() {
        return coursesOffered;
    }

    public void setCoursesOffered(List<String> coursesOffered) {
        this.coursesOffered = coursesOffered;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nEmail: " + email + "\nProfile Photo: " + profilePhoto +
                "\nDegree: " + degree + "\nResearch Interest: " + researchInterest +
                "\nCourses Offered: " + String.join(", ", coursesOffered) +
                "\nOffice Location: " + officeLocation + "\nFaculty ID: " + facultyId;
    }

}

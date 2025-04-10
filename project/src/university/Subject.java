package university;

public class Subject {
    private String subjectName;
    private String subjectCode;

    public Subject(String subjectCode, String subjectName) {
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    @Override
    public String toString() {
        return "Subject [Code=" + subjectCode + ", Name=" + subjectName + "]";
    }
}

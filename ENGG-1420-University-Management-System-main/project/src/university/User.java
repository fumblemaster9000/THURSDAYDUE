package university;

public class User {
    static String userId;
    String password;
    static String name;
    static String email;

    // Constructor
    public User(String userId, String password, String name) {
        // add , String email
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }
    public static String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public static String getName() {
        return name;
    }

    public static String getEmail() {
        return email;
    }

    public void displayOptions() {
        System.out.println("User Options for " + name + ":");
        SubjectManager subjectManager = new SubjectManager();
        subjectManager.viewSubjects();
    }
}

package university;

public class User {
    String userId;
    String password;
    String name;
    String email;

    // Constructor initializes user attributes
    public User(String userId, String password, String name, String email) {
        // add , String email
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Getters for user attributes
    public String getUserId() {
        return userId;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    // Setters to modify user attributes
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setPassword(String password) {this.password = password;}
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Displays user-specific menu options
    public void displayOptions() {
    }
}

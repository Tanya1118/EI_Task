import java.util.HashMap;
import java.util.Map;

/**
 * AuthenticationService class handles user authentication for the application.
 * It stores user credentials and provides a method for validating user logins.
 */
class AuthenticationService {
    private Map<String, User> users; // Map to store usernames and their associated User objects

    /**
     * Constructor to initialize the AuthenticationService with predefined users.
     * In this case, two users are added: "admin" and "user" with their respective passwords.
     */
    public AuthenticationService() {
        users = new HashMap<>(); // Initialize the map for storing user credentials
        users.put("admin", new User("admin", "admin123")); // Add admin user
        users.put("user", new User("user", "user123")); // Add regular user
    }

    /**
     * Authenticate the user based on provided username and password.
     * @param username The username of the user trying to log in
     * @param password The password provided by the user
     * @return true if authentication is successful; false otherwise
     */
    public boolean authenticate(String username, String password) {
        User user = users.get(username); // Retrieve the User object by username
        return user != null && user.getPassword().equals(password); // Check if user exists and password matches
    }
}

import java.util.HashMap;
import java.util.Map;

// MockServer class to simulate server behavior
public class MockServer {
    // Map to store user IDs and corresponding names
    private static Map<String, String> idsNames = new HashMap<>();
    // Map to store detailed member information
    private static Map<String, String> memberDetails = new HashMap<>();

    // Constructor for MockServer class
    public MockServer() {
        // Display a message indicating that the test server is running
        System.out.println("The test server is running...");
    }

    // Method to add a user to the server
    public void addUser(String id, String name) {
        // Add the user's ID and name to the idsNames map
        idsNames.put(id, name);
        // Add the user's ID and name to the memberDetails map
        memberDetails.put(id, name);
    }

    // Method to remove a user from the server
    public void removeUser(String id) {
        // Remove the user's ID and corresponding name from the idsNames map
        idsNames.remove(id);
        // Remove the user's ID and corresponding name from the memberDetails map
        memberDetails.remove(id);
    }

    // Method to retrieve the name of a user based on their ID
    public String getName(String id) {
        // Return the name associated with the provided ID from the idsNames map
        return idsNames.get(id);
    }

    // Method to retrieve detailed information about a member based on their ID
    public String getMemberDetail(String id) {
        // Return the detailed information associated with the provided ID from the memberDetails map
        return memberDetails.get(id);
    }
}
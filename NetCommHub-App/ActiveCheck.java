import java.util.List;

// Represents a message for checking active users in the system
public class ActiveCheck extends Carrier {

    // Constructor for ActiveCheck class
    public ActiveCheck(List<String> activeUser) {
        // Call the superclass constructor (Carrier) to initialize common fields
        super();

        // Set the list of active users in the Carrier class using the setActiveMembers method
        setActiveMembers(activeUser);
    }
}
// Represents a message indicating that a user has left the chat
public class UserLeft extends Carrier {

    // Constructor for UserLeft class
    public UserLeft(String content, String id, String name) {
        // Call the superclass constructor (Carrier) to initialize common fields
        super();

        // Create an array containing the user's ID and name
        String[] idName = {id, name};

        // Set the ID and name in the Carrier class using the setIdName method
        setIdName(idName);

        // Set the content of the message
        setContent(content);
    }
}
// Represents a message indicating that a requested username has been accepted
public class NameAccepted extends Carrier {

    // Constructor for NameAccepted class
    public NameAccepted(String id, String name) {
        // Call the superclass constructor (Carrier) to initialize common fields
        super();

        // Create an array containing the new user's ID and name
        String[] newuser = {id, name};

        // Set the ID and name in the Carrier class using the setIdName method
        setIdName(newuser);
    }
}
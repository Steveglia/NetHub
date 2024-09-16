// Represents a message requesting authentication from a user
public class AuthRequest extends Carrier {

    // Constructor for AuthRequest class
    public AuthRequest(String[] sender, Boolean auth) {
        // Call the superclass constructor (Carrier) to initialize common fields
        super();

        // Set the ID and name of the sender in the Carrier class using the setIdName method
        setIdName(sender);

        // Set the authentication status in the Carrier class using the setAuth method
        setAuth(auth);
    }
}
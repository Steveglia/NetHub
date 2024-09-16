// Represents a message containing details about chat members
public class MemberDetails extends Carrier {

    // Constructor for MemberDetails class
    public MemberDetails(String senderId, String senderName) {
        // Call the superclass constructor (Carrier) to initialize common fields
        super();

        // Create an array containing the sender's ID and name
        String[] sender = {senderId, senderName};

        // Set the ID and name of the sender in the Carrier class using the setIdName method
        setIdName(sender);
    }
}
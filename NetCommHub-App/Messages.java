// Represents a message containing chat content between users
public class Messages extends Carrier {

    // Constructor for Messages class
    public Messages(String content, String sender, String receiver, String[] idName) {
        // Call the superclass constructor (Carrier) to initialize common fields
        super();

        // Set the content of the message
        setContent(content);

        // Set the sender of the message
        setSender(sender);

        // Set the receiver of the message
        setReceiver(receiver);

        // Set the ID and name of the sender and receiver in the Carrier class using the setIdName method
        setIdName(idName);
    }
}
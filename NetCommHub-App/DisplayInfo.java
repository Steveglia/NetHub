// Represents a message for displaying informational content
public class DisplayInfo extends Carrier {

    // Constructor for DisplayInfo class
    public DisplayInfo(String content) {
        // Call the superclass constructor (Carrier) to initialize common fields
        super();

        // Set the content of the message
        setContent(content);
    }
}
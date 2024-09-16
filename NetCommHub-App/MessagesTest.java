import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Unit tests for the Messages class
public class MessagesTest {

    // The instance of the Messages class to be tested
    private Messages messages;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the Messages class with sample content, sender, receiver, and idName
        messages = new Messages("Sample Content", "SenderID", "ReceiverID", new String[]{"SenderID", "SenderName"});
    }

    // Test case to check if the getContent method returns the correct content
    @Test
    public void testGetContent() {
        // Retrieve the content using the getContent method
        String retrievedContent = messages.getContent();
        // Assert that the retrieved content is equal to the content used in the constructor
        assertEquals("Sample Content", retrievedContent);
    }

    // Test case to check if the setContent method updates the content correctly
    @Test
    public void testSetContent() {
        // Use setContent to update the content
        messages.setContent("New Content");
        // Retrieve the updated content using getContent
        String retrievedContent = messages.getContent();
        // Assert that the retrieved content is equal to the new content set in setContent
        assertEquals("New Content", retrievedContent);
    }

    // Test case to check if the getSender method returns the correct sender
    @Test
    public void testGetSender() {
        // Retrieve the sender using the getSender method
        String retrievedSender = messages.getSender();
        // Assert that the retrieved sender is equal to the sender used in the constructor
        assertEquals("SenderID", retrievedSender);
    }

    // Test case to check if the setSender method updates the sender correctly
    @Test
    public void testSetSender() {
        // Use setSender to update the sender
        messages.setSender("NewSenderID");
        // Retrieve the updated sender using getSender
        String retrievedSender = messages.getSender();
        // Assert that the retrieved sender is equal to the new sender set in setSender
        assertEquals("NewSenderID", retrievedSender);
    }

    // Test case to check if the getReceiver method returns the correct receiver
    @Test
    public void testGetReceiver() {
        // Retrieve the receiver using the getReceiver method
        String retrievedReceiver = messages.getReceiver();
        // Assert that the retrieved receiver is equal to the receiver used in the constructor
        assertEquals("ReceiverID", retrievedReceiver);
    }

    // Test case to check if the setReceiver method updates the receiver correctly
    @Test
    public void testSetReceiver() {
        // Use setReceiver to update the receiver
        messages.setReceiver("NewReceiverID");
        // Retrieve the updated receiver using getReceiver
        String retrievedReceiver = messages.getReceiver();
        // Assert that the retrieved receiver is equal to the new receiver set in setReceiver
        assertEquals("NewReceiverID", retrievedReceiver);
    }

    // Test case to check if the getIdName method returns the correct ID and name
    @Test
    public void testGetIdName() {
        // Retrieve the ID and name using the getIdName method
        String[] retrievedIdName = messages.getIdName();
        // Assert that the retrieved ID and name are equal to the ones used in the constructor
        assertArrayEquals(new String[]{"SenderID", "SenderName"}, retrievedIdName);
    }

    // Test case to check if the setIdName method updates the ID and name correctly
    @Test
    public void testSetIdName() {
        // Create a new array with updated ID and name
        String[] newIdName = {"NewSenderID", "NewSenderName"};

        // Use setIdName to update the ID and name
        messages.setIdName(newIdName);

        // Retrieve the updated ID and name using getIdName
        String[] retrievedIdName = messages.getIdName();

        // Assert that the retrieved ID and name match the new values set in setIdName
        assertArrayEquals(newIdName, retrievedIdName);
    }
}
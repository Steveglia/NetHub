import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Unit tests for the DisplayInfo class
public class DisplayInfoTest {

    // The instance of the DisplayInfo class to be tested
    private DisplayInfo displayInfo;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the DisplayInfo class with sample content
        displayInfo = new DisplayInfo("Sample Content");
    }

    // Test case to check if the getContent method returns the correct content
    @Test
    public void testGetContent() {
        // Retrieve the content using the getContent method
        String retrievedContent = displayInfo.getContent();
        // Assert that the retrieved content is equal to the content used in the constructor
        assertEquals("Sample Content", retrievedContent);
    }

    // Test case to check if the setContent method updates the content correctly
    @Test
    public void testSetContent() {
        // Use setContent to update the content
        displayInfo.setContent("New Content");
        // Retrieve the updated content using getContent
        String retrievedContent = displayInfo.getContent();
        // Assert that the retrieved content is equal to the new content set in setContent
        assertEquals("New Content", retrievedContent);
    }
}
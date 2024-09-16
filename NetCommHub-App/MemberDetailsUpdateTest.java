import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Unit tests for the MemberDetailsUpdate class
public class MemberDetailsUpdateTest {

    // The instance of the MemberDetailsUpdate class to be tested
    private MemberDetailsUpdate memberDetailsUpdate;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the MemberDetailsUpdate class with sample member details and content
        memberDetailsUpdate = new MemberDetailsUpdate(new String[]{"TestMemberID", "TestMemberName"}, "Updated Content");
    }

    // Test case to check if the getIdName method returns the correct member details
    @Test
    public void testGetIdName() {
        // Retrieve the member details using the getIdName method
        String[] retrievedIdName = memberDetailsUpdate.getIdName();
        // Assert that the retrieved member details match the ones used in the constructor
        assertArrayEquals(new String[]{"TestMemberID", "TestMemberName"}, retrievedIdName);
    }

    // Test case to check if the setIdName method updates the member details correctly
    @Test
    public void testSetIdName() {
        // Create a new array with updated member details
        String[] newIdName = {"NewMemberID", "NewMemberName"};

        // Use setIdName to update the member details
        memberDetailsUpdate.setIdName(newIdName);

        // Retrieve the updated member details using getIdName
        String[] retrievedIdName = memberDetailsUpdate.getIdName();

        // Assert that the retrieved member details match the new values set in setIdName
        assertArrayEquals(newIdName, retrievedIdName);
    }

    // Test case to check if the getContent method returns the correct content
    @Test
    public void testGetContent() {
        // Retrieve the content using the getContent method
        String retrievedContent = memberDetailsUpdate.getContent();
        // Assert that the retrieved content is equal to the content used in the constructor
        assertEquals("Updated Content", retrievedContent);
    }

    // Test case to check if the setContent method updates the content correctly
    @Test
    public void testSetContent() {
        // Use setContent to update the content
        memberDetailsUpdate.setContent("New Content");
        // Retrieve the updated content using getContent
        String retrievedContent = memberDetailsUpdate.getContent();
        // Assert that the retrieved content is equal to the new content set in setContent
        assertEquals("New Content", retrievedContent);
    }
}
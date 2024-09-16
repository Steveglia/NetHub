import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Unit tests for the MemberDetails class
public class MemberDetailsTest {

    // The instance of the MemberDetails class to be tested
    private MemberDetails memberDetails;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the MemberDetails class with sample sender ID and name
        memberDetails = new MemberDetails("TestSenderID", "TestSenderName");
    }

    // Test case to check if the getIdName method returns the correct ID and name
    @Test
    public void testGetIdName() {
        // Retrieve the ID and name using the getIdName method
        String[] retrievedIdName = memberDetails.getIdName();
        // Assert that the retrieved ID and name match the ones used in the constructor
        assertArrayEquals(new String[]{"TestSenderID", "TestSenderName"}, retrievedIdName);
    }

    // Test case to check if the setIdName method updates the ID and name correctly
    @Test
    public void testSetIdName() {
        // Create a new array with updated ID and name
        String[] newIdName = {"NewSenderID", "NewSenderName"};

        // Use setIdName to update the ID and name
        memberDetails.setIdName(newIdName);

        // Retrieve the updated ID and name using getIdName
        String[] retrievedIdName = memberDetails.getIdName();

        // Assert that the retrieved ID and name match the new values set in setIdName
        assertArrayEquals(newIdName, retrievedIdName);
    }
}
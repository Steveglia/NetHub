import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Unit tests for the UserLeft class
public class UserLeftTest {

    // The instance of the UserLeft class to be tested
    private UserLeft userLeft;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the UserLeft class with dummy values
        userLeft = new UserLeft("User left the chat", "userID", "UserName");
    }

    // Test case to check if the getContent method returns a non-null value
    @Test
    public void testGetContent() {
        // Retrieve the content using the getContent method
        String content = userLeft.getContent();
        // Assert that the content is not null
        assertNotNull(content);
    }

    // Test case to check if the getIdName method returns a non-null and non-empty array
    @Test
    public void testGetIdName() {
        // Retrieve the ID and name using the getIdName method
        String[] idName = userLeft.getIdName();
        // Assert that the ID and name array is not null and not empty
        assertNotNull(idName);
        assertTrue(idName.length > 0);
    }

    // Test case to check if the setIdName method updates the ID and name correctly
    @Test
    public void testSetIdName() {
        // Set a new ID and name using the setIdName method
        String[] newIdName = {"newUserID", "NewUserName"};
        userLeft.setIdName(newIdName);

        // Retrieve the updated ID and name using the getIdName method
        String[] updatedIdName = userLeft.getIdName();

        // Assert that the retrieved ID and name match the new values set in setIdName
        assertArrayEquals(newIdName, updatedIdName);
    }
}
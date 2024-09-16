import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Unit tests for the NameAccepted class
public class NameAcceptedTest {

    // The instance of the NameAccepted class to be tested
    private NameAccepted nameAccepted;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the NameAccepted class with a sample ID and name
        nameAccepted = new NameAccepted("UserID", "UserName");
    }

    // Test case to check if the getIdName method returns the correct ID and name
    @Test
    public void testGetIdName() {
        // Retrieve the ID and name using the getIdName method
        String[] retrievedIdName = nameAccepted.getIdName();
        // Assert that the retrieved ID and name are equal to the ones used in the constructor
        assertArrayEquals(new String[]{"UserID", "UserName"}, retrievedIdName);
    }

    // Test case to check if the setIdName method updates the ID and name correctly
    @Test
    public void testSetIdName() {
        // Create a new array with updated ID and name
        String[] newIdName = {"NewUserID", "NewUserName"};

        // Use setIdName to update the ID and name
        nameAccepted.setIdName(newIdName);

        // Retrieve the updated ID and name using getIdName
        String[] retrievedIdName = nameAccepted.getIdName();

        // Assert that the retrieved ID and name match the new values set in setIdName
        assertArrayEquals(newIdName, retrievedIdName);
    }
}
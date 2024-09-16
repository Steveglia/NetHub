import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

// Unit tests for the ActiveCheck class
public class ActiveCheckTest {

    // The instance of the ActiveCheck class to be tested
    private ActiveCheck activeCheck;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the ActiveCheck class with a sample list of active users
        List<String> activeUsers = Arrays.asList("User1", "User2");
        activeCheck = new ActiveCheck(activeUsers);
    }

    // Test case to check if the getActiveMembers method returns the correct list of active users
    @Test
    public void testGetActiveMembers() {
        // Retrieve the list of active users using the getActiveMembers method
        List<String> retrievedActiveUsers = activeCheck.getActiveMembers();
        // Assert that the retrieved list is equal to the original list used in the constructor
        assertEquals(Arrays.asList("User1", "User2"), retrievedActiveUsers);
    }

    // Test case to check if the setActiveMembers method updates the list of active users correctly
    @Test
    public void testSetActiveMembers() {
        // Create a new list of active users
        List<String> newActiveUsers = Arrays.asList("NewUser1", "NewUser2");
        // Use setActiveMembers to update the list of active users
        activeCheck.setActiveMembers(newActiveUsers);
        // Retrieve the updated list using getActiveMembers
        List<String> retrievedActiveUsers = activeCheck.getActiveMembers();
        // Assert that the retrieved list is equal to the new list set in setActiveMembers
        assertEquals(newActiveUsers, retrievedActiveUsers);
    }
}
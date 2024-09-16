import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Unit tests for the CurrentCoordinator class
public class CurrentCoordinatorTest {

    // The instance of the CurrentCoordinator class to be tested
    private CurrentCoordinator currentCoordinator;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the CurrentCoordinator class with a sample coordinator
        currentCoordinator = new CurrentCoordinator("TestCoordinator");
    }

    // Test case to check if the getCoordinator method returns the correct coordinator
    @Test
    public void testGetCoordinator() {
        // Retrieve the coordinator using the getCoordinator method
        String retrievedCoordinator = currentCoordinator.getCoordinator();
        // Assert that the retrieved coordinator is equal to the coordinator used in the constructor
        assertEquals("TestCoordinator", retrievedCoordinator);
    }

    // Test case to check if the setCoordinator method updates the coordinator correctly
    @Test
    public void testSetCoordinator() {
        // Use setCoordinator to update the coordinator
        currentCoordinator.setCoordinator("NewCoordinator");
        // Retrieve the updated coordinator using getCoordinator
        String retrievedCoordinator = currentCoordinator.getCoordinator();
        // Assert that the retrieved coordinator is equal to the new coordinator set in setCoordinator
        assertEquals("NewCoordinator", retrievedCoordinator);
    }
}
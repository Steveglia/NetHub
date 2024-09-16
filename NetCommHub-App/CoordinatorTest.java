import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Unit tests for the Coordinator class
public class CoordinatorTest {

    // The instance of the Coordinator class to be tested
    private Coordinator coordinator;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the Coordinator class
        coordinator = new Coordinator();
    }

    // Test case to check if the getCoordinator method returns null for a Coordinator instance
    @Test
    public void testGetCoordinator() {
        // Retrieve the coordinator using the getCoordinator method
        String retrievedCoordinator = coordinator.getCoordinator();
        // Assert that the retrieved coordinator is null, as it is not set in the constructor
        assertNull(retrievedCoordinator);
    }

    // Test case to check if the setCoordinator method updates the coordinator correctly
    @Test
    public void testSetCoordinator() {
        // Use setCoordinator to update the coordinator
        coordinator.setCoordinator("NewCoordinator");
        // Retrieve the updated coordinator using getCoordinator
        String retrievedCoordinator = coordinator.getCoordinator();
        // Assert that the retrieved coordinator is equal to the new coordinator set in setCoordinator
        assertEquals("NewCoordinator", retrievedCoordinator);
    }
}
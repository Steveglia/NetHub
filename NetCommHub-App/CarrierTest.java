import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;

// Unit tests for the Carrier class
public class CarrierTest {

    // The instance of the Carrier class to be tested
    private Carrier carrier;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the Carrier class
        carrier = new Carrier();
    }

    // Test case to check if getContent returns the correct content
    @Test
    public void testGetContent() {
        // Set content using the setContent method
        carrier.setContent("Test Content");
        // Assert that getContent returns the expected content
        assertEquals("Test Content", carrier.getContent());
    }

    // Test case to check if getReceiver returns the correct receiver
    @Test
    public void testGetReceiver() {
        // Set receiver using the setReceiver method
        carrier.setReceiver("TestReceiver");
        // Assert that getReceiver returns the expected receiver
        assertEquals("TestReceiver", carrier.getReceiver());
    }

    // Test case to check if getSender returns the correct sender
    @Test
    public void testGetSender() {
        // Set sender using the setSender method
        carrier.setSender("TestSender");
        // Assert that getSender returns the expected sender
        assertEquals("TestSender", carrier.getSender());
    }

    // Test case to check if getCoordinator returns the correct coordinator
    @Test
    public void testGetCoordinator() {
        // Set coordinator using the setCoordinator method
        carrier.setCoordinator("TestCoordinator");
        // Assert that getCoordinator returns the expected coordinator
        assertEquals("TestCoordinator", carrier.getCoordinator());
    }

    // Test case to check if getAuth returns the correct authentication status
    @Test
    public void testGetAuth() {
        // Set auth using the setAuth method
        carrier.setAuth(true);
        // Assert that getAuth returns the expected authentication status
        assertTrue(carrier.getAuth());
    }

    // Test case to check if getIdName returns the correct ID and name array
    @Test
    public void testGetIdName() {
        // Set idName using the setIdName method
        String[] idName = {"TestId", "TestName"};
        carrier.setIdName(idName);
        // Assert that getIdName returns the expected ID and name array
        assertArrayEquals(idName, carrier.getIdName());
    }

    // Test case to check if getActiveMembers returns the correct list of active members
    @Test
    public void testGetActiveMembers() {
        // Set activeMembers using the setActiveMembers method
        List<String> activeMembers = Arrays.asList("Member1", "Member2");
        carrier.setActiveMembers(activeMembers);
        // Assert that getActiveMembers returns the expected list of active members
        assertEquals(activeMembers, carrier.getActiveMembers());
    }

    // Test case to check if setContent sets the content correctly
    @Test
    public void testSetContent() {
        // Use setContent to set the content
        carrier.setContent("New Content");
        // Assert that getContent returns the updated content
        assertEquals("New Content", carrier.getContent());
    }

    // Test case to check if setReceiver sets the receiver correctly
    @Test
    public void testSetReceiver() {
        // Use setReceiver to set the receiver
        carrier.setReceiver("NewReceiver");
        // Assert that getReceiver returns the updated receiver
        assertEquals("NewReceiver", carrier.getReceiver());
    }

    // Test case to check if setSender sets the sender correctly
    @Test
    public void testSetSender() {
        // Use setSender to set the sender
        carrier.setSender("NewSender");
        // Assert that getSender returns the updated sender
        assertEquals("NewSender", carrier.getSender());
    }

    // Test case to check if setCoordinator sets the coordinator correctly
    @Test
    public void testSetCoordinator() {
        // Use setCoordinator to set the coordinator
        carrier.setCoordinator("NewCoordinator");
        // Assert that getCoordinator returns the updated coordinator
        assertEquals("NewCoordinator", carrier.getCoordinator());
    }

    // Test case to check if setAuth sets the authentication status correctly
    @Test
    public void testSetAuth() {
        // Use setAuth to set the authentication status
        carrier.setAuth(false);
        // Assert that getAuth returns the updated authentication status
        assertFalse(carrier.getAuth());
    }

    // Test case to check if setIdName sets the ID and name array correctly
    @Test
    public void testSetIdName() {
        // Use setIdName to set the ID and name array
        String[] idName = {"NewId", "NewName"};
        carrier.setIdName(idName);
        // Assert that getIdName returns the updated ID and name array
        assertArrayEquals(idName, carrier.getIdName());
    }

    // Test case to check if setActiveMembers sets the list of active members correctly
    @Test
    public void testSetActiveMembers() {
        // Use setActiveMembers to set the list of active members
        List<String> activeMembers = Arrays.asList("NewMember1", "NewMember2");
        carrier.setActiveMembers(activeMembers);
        // Assert that getActiveMembers returns the updated list of active members
        assertEquals(activeMembers, carrier.getActiveMembers());
    }
}
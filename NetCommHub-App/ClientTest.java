import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Method;

// Unit tests for the Client class
public class ClientTest {

    // The instance of the Client class to be tested
    private Client client;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the Client class
        client = new Client();
    }

    // Test case to check if the getGUI method returns a non-null value
    @Test
    public void testGetGUI() {
        assertNotNull(client.getGUI());
    }

    // Test case to check if the getIds method returns a non-null and non-empty map
    @Test
    public void testGetIds() {
        assertNotNull(client.getIds());
        assertFalse(client.getIds().isEmpty());
    }

    // Test case to check if the getNameIds method returns a non-null and non-empty map
    @Test
    public void testGetNameIds() {
        assertNotNull(client.getNameIds());
        assertFalse(client.getNameIds().isEmpty());
    }

    // Test case to check if the setIds method sets the IDs map correctly
    @Test
    public void testSetIds() {
        // Create a new map of IDs
        Map<String, String> newIds = new ConcurrentHashMap<>();
        newIds.put("2", "TestUser");

        // Set the new map using the setIds method
        client.setIds(newIds);

        // Assert that the setIds method successfully updated the IDs map
        assertEquals(newIds, client.getIds());
    }

    // Test case to check if the setMyId method sets the client's ID correctly
    @Test
    public void testSetMyId() {
        // Set a new ID using the setMyId method
        client.setMyId("TestID");

        // Assert that the setMyId method successfully updated the client's ID
        assertEquals("TestID", client.getMyId());
    }

    // Test case to check if the getOutputStream method initially returns null
    @Test
    public void testOutputStream() {
        assertNull(client.getOutputStream());
    }

    // Test case to check if the getLoginHandler method returns a non-null value
    @Test
    public void testGetLoginHandler() {
        assertNotNull(client.getLoginHandler());
    }

    // Test case to check the private method handleConnectionFailure using reflection
    @Test
    public void testHandleConnectionFailure() {
        invokePrivateMethod(client, "handleConnectionFailure");
    }

    // Test case to check the private method closeGui using reflection
    @Test
    public void testCloseGui() {
        invokePrivateMethod(client, "closeGui");
    }

    // Test case to check the private method errorMessages using reflection
    @Test
    public void testErrorMessages() {
        invokePrivateMethod(client, "errorMessages");

    }

    // Helper method to invoke private methods using reflection
    private void invokePrivateMethod(Client client, String methodName, Object... args) {
        try {
            // Use reflection to access the private method
            Method privateMethod = Client.class.getDeclaredMethod(methodName);
            privateMethod.setAccessible(true);

            // Invoke the private method on the provided instance
            if (args.length == 0) {
                privateMethod.invoke(client);
            } else {
                privateMethod.invoke(client, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception not expected");
        }
    }
}
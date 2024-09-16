import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MockServerTest {
    // Declaring a MockServer instance
    private MockServer server;

    @Before
    // This method will be executed before each test method
    public void setUp() {
        // Initializing the MockServer instance
        server = new MockServer();
    }

    @Test
    // Test method to check adding a user
    public void testAddUser() { 
        // Creating an instance of UniqueIdGenerator
        UniqueIdGenerator uniqueIdGenerator = UniqueIdGenerator.getInstance(1000, 9999);
        // Generating a unique ID
        String id = uniqueIdGenerator.generateUniqueId();
        // Adding a user to the server
        server.addUser(id, "TestUser");
        // Checking if the name associated with the ID is correct
        assertEquals("TestUser", server.getName(id));
        // Checking if the member details associated with the ID are correct
        assertEquals("TestUser", server.getMemberDetail(id));
    }

    @Test
     // Test method to check removing a user
    public void testRemoveUser() {
        // Creating an instance of UniqueIdGenerator
         UniqueIdGenerator uniqueIdGenerator = UniqueIdGenerator.getInstance(1000, 9999);
        // Generating a unique ID
        String id = uniqueIdGenerator.generateUniqueId();
        // Adding a user to the server
        server.addUser(id, "TestUser");
        // Removing the user from the server
        server.removeUser(id);
        // Asserting that the name associated with the ID is null after removal
        assertNull(server.getName(id));
        // Asserting that the member details associated with the ID are null after removal
        assertNull(server.getMemberDetail(id));
    }

    @Test
    // Test method to check getting the name of a user
    public void testGetName() {
        // Creating an instance of UniqueIdGenerator
        UniqueIdGenerator uniqueIdGenerator = UniqueIdGenerator.getInstance(1000, 9999);
        // Generating a unique ID
        String id = uniqueIdGenerator.generateUniqueId();
        // Adding a user to the server
        server.addUser(id, "TestUser");
        // Checking if the correct name is retrieved for the given ID
        assertEquals("TestUser", server.getName(id));
    }

    @Test
    // Test method to check getting member details of a user
    public void testGetMemberDetail() { 
        // Creating an instance of UniqueIdGenerator
        UniqueIdGenerator uniqueIdGenerator = UniqueIdGenerator.getInstance(1000, 9999);
        // Generating a unique ID
        String id = uniqueIdGenerator.generateUniqueId();
        // Adding a user to the server
        server.addUser(id, "TestUser");
        // Checking if the correct member details are retrieved for the given ID
        assertEquals("TestUser", server.getMemberDetail(id));
    }
}
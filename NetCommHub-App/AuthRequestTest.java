import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Unit tests for the AuthRequest class
public class AuthRequestTest {

    // The instance of the AuthRequest class to be tested
    private AuthRequest authRequest;

    // Set up method executed before each test
    @Before
    public void setUp() {
        // Create a new instance of the AuthRequest class with sample sender details and authentication status
        String[] senderDetails = {"UserID", "UserName"};
        Boolean authenticationStatus = true;
        authRequest = new AuthRequest(senderDetails, authenticationStatus);
    }

    // Test case to check if the getIdName method returns the correct ID and name of the sender
    @Test
    public void testGetIdName() {
        // Retrieve the ID and name of the sender using the getIdName method
        String[] retrievedSenderDetails = authRequest.getIdName();
        // Assert that the retrieved details are equal to the original sender details used in the constructor
        assertArrayEquals(new String[]{"UserID", "UserName"}, retrievedSenderDetails);
    }

    // Test case to check if the getAuth method returns the correct authentication status
    @Test
    public void testGetAuth() {
        // Retrieve the authentication status using the getAuth method
        Boolean retrievedAuthStatus = authRequest.getAuth();
        // Assert that the retrieved status is equal to the original status used in the constructor
        assertEquals(true, retrievedAuthStatus);
    }

    // Test case to check if the setIdName method updates the ID and name of the sender correctly
    @Test
    public void testSetIdName() {
        // Create a new set of sender details
        String[] newSenderDetails = {"NewUserID", "NewUserName"};
        // Use setIdName to update the ID and name of the sender
        authRequest.setIdName(newSenderDetails);
        // Retrieve the updated details using getIdName
        String[] retrievedSenderDetails = authRequest.getIdName();
        // Assert that the retrieved details are equal to the new details set in setIdName
        assertArrayEquals(newSenderDetails, retrievedSenderDetails);
    }

    // Test case to check if the setAuth method updates the authentication status correctly
    @Test
    public void testSetAuth() {
        // Use setAuth to update the authentication status
        authRequest.setAuth(false);
        // Retrieve the updated status using getAuth
        Boolean retrievedAuthStatus = authRequest.getAuth();
        // Assert that the retrieved status is equal to the new status set in setAuth
        assertEquals(false, retrievedAuthStatus);
    }
}
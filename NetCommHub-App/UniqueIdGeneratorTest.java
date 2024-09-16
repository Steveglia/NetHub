import org.junit.Test;
import static org.junit.Assert.*;

// Test class for UniqueIdGenerator
public class UniqueIdGeneratorTest {
    
    // Test method for generating a unique ID
    @Test
    public void testGenerateUniqueId() {
        // Create an instance of UniqueIdGenerator with specified range
        UniqueIdGenerator generator = UniqueIdGenerator.getInstance(1000, 9999);

        // Generate a unique ID
        String uniqueId = generator.generateUniqueId();

        // Assert that the generated unique ID is not null
        assertNotNull(uniqueId);

        // Split the unique ID into parts separated by '_'
        String[] parts = uniqueId.split("_");
        
        // Assert that the unique ID consists of two parts
        assertEquals(2, parts.length);

        // Extract the random number part and convert it to an integer
        int randomNum = Integer.parseInt(parts[0]);
        
        // Assert that the random number falls within the specified range
        assertTrue(randomNum >= 1000 && randomNum <= 9999);

        // Extract the timestamp part and convert it to a long
        long timestamp = Long.parseLong(parts[1]);
        
        // Assert that the timestamp is greater than 0
        assertTrue(timestamp > 0);
    }

    // Test method for generating different unique IDs
    @Test
    public void testGenerateDifferentIds() {
        // Create an instance of UniqueIdGenerator with specified range
        UniqueIdGenerator generator = UniqueIdGenerator.getInstance(1000, 9999);
        
        // Generate two unique IDs
        String uniqueId1 = generator.generateUniqueId();
        String uniqueId2 = generator.generateUniqueId();

        // Assert that both generated unique IDs are not null
        assertNotNull(uniqueId1);
        assertNotNull(uniqueId2);
        
        // Assert that the two generated unique IDs are not equal
        assertNotEquals(uniqueId1, uniqueId2);
    }
}
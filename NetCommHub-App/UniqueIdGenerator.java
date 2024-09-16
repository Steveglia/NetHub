import java.util.Random;

// Class for generating unique IDs
public class UniqueIdGenerator {
    // Singleton instance of UniqueIdGenerator
    private static UniqueIdGenerator instance;
    // Minimum and maximum values for generating random numbers
    private int min;
    private int max;

    // Private constructor to prevent direct instantiation
    private UniqueIdGenerator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    // Method to get the singleton instance of UniqueIdGenerator
    public static synchronized UniqueIdGenerator getInstance(int min, int max) {
        // If instance is not initialized, create a new instance
        if (instance == null) {
            instance = new UniqueIdGenerator(min, max);
        }
        return instance;
    }

    // Generate a unique ID based on timestamp and random number
    public String generateUniqueId() {
        // Get current timestamp in seconds
        long timestamp = System.currentTimeMillis() / 1000L;
        // Generate a random number within the specified range
        int randomNum = new Random().nextInt(max - min + 1) + min;
        // Concatenate random number and timestamp to form unique ID
        return randomNum + "_" + timestamp;
    }
}
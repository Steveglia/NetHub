// Represents a message indicating the current coordinator in a system
public class CurrentCoordinator extends Carrier {

    // Constructor for CurrentCoordinator class
    public CurrentCoordinator(String coordinator) {
        // Call the superclass constructor (Carrier) to initialize common fields
        super();

        // Set the current coordinator in the Carrier class using the setCoordinator method
        setCoordinator(coordinator);
    }
}
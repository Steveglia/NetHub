// Represents a message updating details about chat members
public class MemberDetailsUpdate extends Carrier {

    // Constructor for MemberDetailsUpdate class
    public MemberDetailsUpdate(String[] memberDetails, String content) {
        // Call the superclass constructor (Carrier) to initialize common fields
        super();

        // Set the ID and name of the member in the Carrier class using the setIdName method
        setIdName(memberDetails);

        // Set additional content related to the member details update
        setContent(content);
    }
}
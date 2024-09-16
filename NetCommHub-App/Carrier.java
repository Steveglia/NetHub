import java.io.Serializable;
import java.util.List;

// Represents a generic carrier class for messaging
public class Carrier implements Serializable {

    // Factory method to create instances of subclasses
    public static Carrier createMessage(String messageType, Object... args) {
        switch (messageType) {
            case "MemberDetails":
                if (args.length != 2) {
                    throw new IllegalArgumentException("MemberDetails message type requires senderId and senderName parameters.");
                }
                return new MemberDetails((String) args[0], (String) args[1]);
            case "AuthRequest":
                if (args.length != 2 || !(args[0] instanceof String[]) || !(args[1] instanceof Boolean)) {
                    throw new IllegalArgumentException("AuthRequest message type requires a String[] parameter for sender and a Boolean parameter for auth.");
                }
                return new AuthRequest((String[]) args[0], (Boolean) args[1]);
            case "Coordinator":
                if (args.length != 0) {
                    throw new IllegalArgumentException("Coordinator message type does not require any parameters.");
                }
                return new Coordinator();
            case "CurrentCoordinator":
                if (args.length != 1 || !(args[0] instanceof String)) {
                    throw new IllegalArgumentException("CurrentCoordinator message type requires a String parameter for coordinator.");
                }
                return new CurrentCoordinator((String) args[0]);
            case "DisplayInfo":
                if (args.length != 1 || !(args[0] instanceof String)) {
                    throw new IllegalArgumentException("DisplayInfo message type requires a String parameter for content.");
                }
                return new DisplayInfo((String) args[0]);
            case "MemberDetailsUpdate":
                if (args.length != 2 || !(args[0] instanceof String[]) || !(args[1] instanceof String)) {
                    throw new IllegalArgumentException("MemberDetailsUpdate message type requires a String[] parameter for memberDetails and a String parameter for content.");
                }
                return new MemberDetailsUpdate((String[]) args[0], (String) args[1]);
            case "Messages":
                if (args.length != 4 || !(args[0] instanceof String) || !(args[1] instanceof String) || !(args[2] instanceof String) || !(args[3] instanceof String[])) {
                    throw new IllegalArgumentException("Messages message type requires a String parameter for content, sender, receiver, and a String[] parameter for idName.");
                }
                return new Messages((String) args[0], (String) args[1], (String) args[2], (String[]) args[3]);
            case "NameAccepted":
                if (args.length != 2 || !(args[0] instanceof String) || !(args[1] instanceof String)) {
                    throw new IllegalArgumentException("NameAccepted message type requires a String parameter for id and name.");
                }
                return new NameAccepted((String) args[0], (String) args[1]);
            case "NameRequest":
                if (args.length != 0) {
                    throw new IllegalArgumentException("NameRequest message type does not require any parameters.");
                }
                return new NameRequest();
            case "RemoveIds":
                if (args.length != 0) {
                    throw new IllegalArgumentException("RemoveIds message type does not require any parameters.");
                }
                return new RemoveIds();
            case "UpdateMenu":
                if (args.length != 0) {
                    throw new IllegalArgumentException("UpdateMenu message type does not require any parameters.");
                }
                return new UpdateMenu();
            case "UserLeft":
                if (args.length != 3 || !(args[0] instanceof String) || !(args[1] instanceof String) || !(args[2] instanceof String)) {
                    throw new IllegalArgumentException("UserLeft message type requires String parameters for content, id, and name.");
                }
                return new UserLeft((String) args[0], (String) args[1], (String) args[2]);
            default:
                throw new IllegalArgumentException("Unsupported message type: " + messageType);
        }
    }
    // Message content
    private String content;

    // Message receiver
    private String receiver;

    // Message sender
    private String sender;

    // Current coordinator in the system
    private String coordinator;

    // Authentication status for the message
    private Boolean auth;

    // ID and name of the sender
    private String[] idName;

    // List of active members in the system
    private List<String> activeMembers;

    // Getter methods for accessing private fields

    public String getContent() {
        return content;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public Boolean getAuth() {
        return auth;
    }

    public String[] getIdName() {
        return idName;
    }

    public List<String> getActiveMembers() {
        return activeMembers;
    }

    // Setter methods for modifying private fields

    public void setContent(String content) {
        this.content = content;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public void setIdName(String[] idName) {
        this.idName = idName;
    }

    public void setActiveMembers(List<String> activeMembers) {
        this.activeMembers = activeMembers;
    }
}
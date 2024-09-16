import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JOptionPane;

public class Client {
    // Server connection details
    private String serverAddress;
    private int port;

    // Components for handling login and GUI
    private LoginHandler loginHandler;
    private ObjectOutputStream out;
    private String myId;
    private Map<String, String> ids = new ConcurrentHashMap<>();
    private Map<String, String> nameIds = new ConcurrentHashMap<>();
    private GUI gui;

    // Connection timeout constant
    private static final int CONNECTION_TIMEOUT = 1000;

    // Constructor initializes default values, login, and GUI
    public Client() {
        // Pre-defined IDs for system messages
        this.ids.put("0", "Broadcast");
        this.ids.put("1", "Chat Info");
        this.nameIds.put("Chat Info", "1");
        this.nameIds.put("Broadcast", "0");

        // Initialize GUI and LoginHandler
        this.gui = new GUI(this);
        this.loginHandler = new LoginHandler();

        // Retrieve login details; exit if unsuccessful
        String[] loginDetails = this.loginHandler.login();
        if (loginDetails != null) {
            this.serverAddress = loginDetails[0];
            this.port = Integer.parseInt(loginDetails[1]);
        } else {
            errorMessages();
        }
    }

    // Main method to execute the client application
    public static void main(String[] args) {
        try {
            // Create and start the client
            Client client = new Client();
            client.gui.show();
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Establishes a connection to the server and sets up communication
    private void run() {
        Socket socket = new Socket();
        try {
            // Attempt to connect to the server within a specified timeout
            socket.connect(new InetSocketAddress(serverAddress, port), CONNECTION_TIMEOUT);

            if (socket.isConnected()) {
                System.out.println("Connected to the server");
                // If connected, set up communication
                setupCommunication(socket);
            } else {
                // Handle failure to connect to the server
                handleConnectionFailure();
            }
        } catch (IOException e) {
            // Handle I/O exceptions during connection
            handleConnectionFailure();
        } finally {
            // Close the socket and GUI, regardless of connection outcome
            closeSocket(socket);
            closeGui();
        }
    }

    // Sets up input and output streams for communication with the server
    private void setupCommunication(Socket socket) {
        try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            this.out = out;
            RequestHandler requestHandler = new RequestHandler();
            // Continuously read and handle incoming messages
            while (true) {
                Carrier obj = (Carrier) in.readObject();
                requestHandler.handleRequest(obj, this);
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle communication errors
            handleCommunicationError(e);
        }
    }

    // Handles failure to connect to the server
    private void handleConnectionFailure() {
        System.out.println("Failed to connect to the server");
        errorMessages();
    }

    // Handles communication errors
    private void handleCommunicationError(Exception e) {
        errorMessages();
        e.printStackTrace();
    }

    // Getter for GUI instance
    public GUI getGUI() {
        return this.gui;
    }

    // Getters and setters for IDs and name-IDs maps
    public Map<String, String> getIds() {
        return this.ids;
    }

    public Map<String, String> getNameIds() {
        return this.nameIds;
    }

    public void setNameIds(Map<String, String> nameIds) {
        this.nameIds = nameIds;
    }

    public void setIds(Map<String, String> ids) {
        this.ids = ids;
    }

    // Getter for output stream
    public ObjectOutputStream getOutputStream() {
        return this.out;
    }

    // Getter and setter for client's ID
    public String getMyId() {
        return this.myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    // Getter for LoginHandler instance
    public LoginHandler getLoginHandler() {
        return this.loginHandler;
    }

    // Closes the GUI by making it invisible and disposing of it
    private void closeGui() {
        gui.getFrame().setVisible(false);
        gui.getFrame().dispose();
    }
    
    // Displays an error message dialog
    private void errorMessages() {
        JOptionPane.showMessageDialog(gui.getFrame(),
                "Failed to connect to the server. Please check the server address and port number.",
                "Connection Error", JOptionPane.ERROR_MESSAGE);
    }

    // Closes the socket if it is open
    private void closeSocket(Socket socket) {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
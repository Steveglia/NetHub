import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    // Constants for server configuration
    private static final int SERVER_PORT = 59001;
    private static final int THREAD_POOL_SIZE = 500;

    // Maps to store server state
    private static Map<String, Boolean> onlineStatus = new HashMap<>();
    private static Map<String, String> idsNames = new ConcurrentHashMap<>();
    private static Map<String, ObjectOutputStream> writers = new ConcurrentHashMap<>();
    private static Map<String, List<String>> memberDetails = new ConcurrentHashMap<>();
    private static ObjectOutputStream coordinator = null;
    private static UniqueIdGenerator generator = UniqueIdGenerator.getInstance(1000, 9999);

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running...");

        // Thread pool for handling client connections
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        // Scheduled thread pool for executing tasks at fixed intervals
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new ScheduledTask(), 0, 5, TimeUnit.SECONDS);

        try (ServerSocket listener = new ServerSocket(SERVER_PORT)) {
            while (true) {
                // Accept incoming client connections and assign them to threads
                Handler handler = new Handler(listener.accept());
                pool.execute(handler);
                scheduler.execute(new ScheduledTask());
            }
        }
    }

    // Scheduled task to perform actions at fixed intervals
    private static class ScheduledTask implements Runnable {
        @Override
        public void run() {
            // Send an ActiveCheck message to the coordinator
            ActiveCheck activeCheck = new ActiveCheck(new ArrayList<>(idsNames.values()));
            if (coordinator != null) {
                try {
                    coordinator.writeObject(activeCheck);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Handler class to manage communication with each client
    private static class Handler implements Runnable {
        private String name;
        private String id;
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                // Setup input and output streams for communication
                setupStreams();
                // Handle user name registration
                handleNameRegistration();
                // Handle coordinator assignment
                handleCoordinator();
                // Handle new user joining
                handleNewUser();
                // Handle messages from users
                handleMessages();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Cleanup after the client leaves
                cleanupAfterClientLeaves();
            }
        }

        // Setup ObjectInputStream and ObjectOutputStream for communication
        private void setupStreams() throws IOException {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }

        // Handle user name registration
        private void handleNameRegistration() throws IOException, ClassNotFoundException {
            while (true) {
                // Request a name from the client
                Carrier namerequest = Carrier.createMessage("NameRequest");
                out.writeObject(namerequest);
                // Receive the chosen name from the client
                NameRequest nameobj = (NameRequest) in.readObject();
                name = nameobj.getSender();
                if (name == null) {
                    return;
                }
                // Capitalize the first letter of the name
                name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
                synchronized (idsNames) {
                    // Check if the name is unique and not empty
                    if (!name.isEmpty() && !idsNames.values().contains(name)) {
                        id = generator.generateUniqueId();
                        idsNames.put(id, name);
                        // Notify the client that the name is accepted
                        Carrier obj =Carrier.createMessage("NameAccepted", id, name);
                        out.writeObject(obj);
                        break;
                    }
                }
            }
        }

        // Handle coordinator assignment
        private void handleCoordinator() throws IOException {
            synchronized (idsNames) {
                // If no coordinator is assigned, make this client the coordinator
                if (coordinator == null) {
                    coordinator = out;
                    Carrier coordinator = Carrier.createMessage("Coordinator");
                    out.writeObject(coordinator);
                }
            }
        }

        // Handle a new user joining
        private void handleNewUser() throws IOException {
            // Notify other users about the new user joining
            for (ObjectOutputStream writer : writers.values()) {
                Carrier displayInfo = Carrier.createMessage("DisplayInfo", name + " has joined the chat.");
                writer.writeObject(displayInfo);
            }

            // If there is a coordinator, notify the new user
            if (coordinator != null) {
                for (Map.Entry<String, ObjectOutputStream> entry : writers.entrySet()) {
                    if (entry.getValue().equals(coordinator)) {
                        Carrier currentcoordinator = Carrier.createMessage("CurrentCoordinator", idsNames.get(entry.getKey()));
                        out.writeObject(currentcoordinator);
                        break;
                    }
                }
            }
            onlineStatus.put(id, true);

            // Store member details
            memberDetails.put(id, Arrays.asList(name, socket.getInetAddress().toString(), Integer.toString(socket.getPort())));
            writers.put(id, out);
        }

        // Handle messages from users
        private void handleMessages() throws IOException, ClassNotFoundException {
            while (true) {
                Carrier obj = (Carrier) in.readObject();
                if ((obj instanceof Messages) && (obj.getReceiver().equals("0"))) {
                    broadcastMessage(obj);
                } else if ((obj instanceof Messages) && (idsNames.keySet().contains(obj.getReceiver()))) {
                    directMessage(obj);
                } else if (obj instanceof MemberDetails) {
                    // Handle member details request
                    handleMemberDetails(obj);
                } else if (obj instanceof AuthRequest) {
                    // Handle authentication request
                    handleAuthRequest(obj);
                }
            }
        }

        // Broadcast messages to all users
        private void broadcastMessage(Carrier obj) throws IOException {
            for (ObjectOutputStream writer : writers.values()) {
                Carrier displayInfo = Carrier.createMessage("DisplayInfo", idsNames.get(obj.getSender()) + ": " + obj.getContent());
                writer.writeObject(displayInfo);
            }
        }

        // Send private messages to the specified user
        private void directMessage(Carrier obj) throws IOException {
            for (String clientId : idsNames.keySet()) {
                if (obj.getReceiver().equals(clientId) || obj.getSender().equals(clientId)) {
                    if (onlineStatus.get(obj.getReceiver())) {
                        Carrier message = Carrier.createMessage("Messages", obj.getContent(), obj.getSender(), obj.getReceiver(), obj.getIdName());
                        writers.get(clientId).writeObject(message);
                    }
                }
            }
        }

        // Handle member details request
        private void handleMemberDetails(Carrier obj) throws IOException {
            if (writers.get(obj.getIdName()[0]).equals(coordinator)) {
                broadcastMemberDetails();
            } else {
                String[] idName = {obj.getIdName()[0], idsNames.get(obj.getIdName()[0])};
                Carrier authrequest = Carrier.createMessage("AuthRequest", (Object[]) idName, false);
                coordinator.writeObject(authrequest);
            }
        }

        // Broadcast member details to all users
        private void broadcastMemberDetails() throws IOException {
            coordinator.writeObject(new RemoveIds());
            for (Map.Entry<String, ObjectOutputStream> entry : writers.entrySet()) {
                String md = memberDetails.get(entry.getKey()).get(0) + ", " + "ID: " + entry.getKey() + " is at " + memberDetails.get(entry.getKey()).get(1) + ":" + memberDetails.get(entry.getKey()).get(2);
                String[] idName = {entry.getKey(), idsNames.get(entry.getKey())};
                Carrier memberDetailsUpdate = Carrier.createMessage("MemberDetailsUpdate", idName, md);
                coordinator.writeObject(memberDetailsUpdate);
                Carrier updateMenu = Carrier.createMessage("UpdateMenu");
                coordinator.writeObject(updateMenu);
            }
        }

        // Handle authentication request
        private void handleAuthRequest(Carrier obj) throws IOException {
            if (obj.getAuth()) {
                for (Map.Entry<String, ObjectOutputStream> entry : writers.entrySet()) {
                    if (entry.getKey().equals(obj.getReceiver())) {
                        broadcastMemberDetailsToSingleClient(entry);
                    }
                }
            }
        }

        // Broadcast member details to a single client
        private void broadcastMemberDetailsToSingleClient(Map.Entry<String, ObjectOutputStream> entry) throws IOException {
            entry.getValue().writeObject(new RemoveIds());
            for (Map.Entry<String, ObjectOutputStream> ent : writers.entrySet()) {
                String md = memberDetails.get(ent.getKey()).get(0) + ", " + "ID: " + ent.getKey() + " is at " + memberDetails.get(ent.getKey()).get(1) + ":" + memberDetails.get(ent.getKey()).get(2);
                String[] idName = {ent.getKey(), idsNames.get(ent.getKey())};
                Carrier memberDetailsUpdate = Carrier.createMessage("MemberDetailsUpdate", idName, md);
                entry.getValue().writeObject(memberDetailsUpdate);
                Carrier updateMenu = Carrier.createMessage("UpdateMenu");
                entry.getValue().writeObject(updateMenu);
            }
        }

        // Cleanup resources after the client leaves
        private void cleanupAfterClientLeaves() {
            if (out != null) {
                writers.remove(id);
                memberDetails.remove(id);
                if (coordinator == out) {
                    coordinator = null;
                    handleCoordinatorLeaving();
                }
                try {
                    out.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (id != null) {
                broadcastUserLeftMessage();
                onlineStatus.put(id, false);
                idsNames.remove(id);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Notify other users about the client leaving
        private void broadcastUserLeftMessage() {
            for (ObjectOutputStream writer : writers.values()) {
                try {
                    
                    Carrier userLeft = Carrier.createMessage("UserLeft", name + " left the chat.", id, name);
                    writer.writeObject(userLeft);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Handle coordinator leaving the chat
        private static void handleCoordinatorLeaving() {
            synchronized (idsNames.values()) {
                if (!writers.isEmpty()) {
                    String newCoordinatorName = writers.keySet().iterator().next();
                    coordinator = writers.get(newCoordinatorName);
                    try {
                        Carrier coordinatorAssigment = Carrier.createMessage("Coordinator");
                        coordinator.writeObject(coordinatorAssigment);
                        broadcastNewCoordinatorMessage(newCoordinatorName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Broadcast a message about the new coordinator to all clients
    private static void broadcastNewCoordinatorMessage(String newCoordinatorName) throws IOException {
        Carrier coordinatorMessage = Carrier.createMessage("CurrentCoordinator", idsNames.get(newCoordinatorName));
        for (Map.Entry<String, ObjectOutputStream> entry : writers.entrySet()) {
            if (!entry.getValue().equals(coordinator)) {
                entry.getValue().writeObject(coordinatorMessage);
            }
        }
    }
}
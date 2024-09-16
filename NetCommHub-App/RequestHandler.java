import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class RequestHandler {

    // Method to handle different types of requests
    public void handleRequest(Carrier obj, Client client) throws IOException {
        // Get GUI object from client
        GUI gui = client.getGUI();

        // Handling different types of requests
        if (obj instanceof NameRequest) {
            handleNameRequest((NameRequest) obj, client);
        } else if (obj instanceof NameAccepted) {
            handleNameAccepted((NameAccepted) obj, gui, client);
        } else if (obj instanceof Coordinator) {
            handleCoordinator((Coordinator) obj, gui);
        } else if (obj instanceof RemoveIds) {
            handleRemoveIds(client);
        } else if (obj instanceof UserLeft) {
            handleUserLeft((UserLeft) obj, client, gui);
        } else if (obj instanceof CurrentCoordinator) {
            handleCurrentCoordinator((CurrentCoordinator) obj, gui);
        } else if (obj instanceof DisplayInfo) {
            handleDisplayInfo((DisplayInfo) obj, gui);
        } else if (obj instanceof Messages) {
            handleMessages((Messages) obj, client, gui);
        } else if (obj instanceof MemberDetailsUpdate) {
            handleMemberDetailsUpdate((MemberDetailsUpdate) obj, client, gui);
        } else if (obj instanceof AuthRequest) {
            handleAuthRequest((AuthRequest) obj, client, gui);
        } else if (obj instanceof UpdateMenu) {
            handleUpdateMenu(gui);
        } else if (obj instanceof ActiveCheck) {
            handleActiveCheck((ActiveCheck) obj, gui);
        }
    }

    // Handling NameRequest
    private void handleNameRequest(Carrier obj, Client client) throws IOException {
        obj.setSender(client.getLoginHandler().getName());
        client.getOutputStream().writeObject(obj);
    }

    // Handling NameAccepted
    private void handleNameAccepted(Carrier obj, GUI gui, Client client) {
        client.setMyId(obj.getIdName()[0]);
        gui.getFrame().setTitle("Chatter - " + obj.getIdName()[1]);
    }

    // Handling Coordinator
    private void handleCoordinator(Carrier obj, GUI gui) {
        gui.getTabDataMap().get("1").getTextArea().append("You are the Coordinator\n");
        gui.getActiveMembersInfo().setVisible(true);
    }

    // Handling RemoveIds
    private void handleRemoveIds(Client client) {
        client.setIds(null);
    }

    // Handling UserLeft
    private void handleUserLeft(Carrier obj, Client client, GUI gui) {
        // If a user left, update GUI and remove user information
        gui.getTabDataMap().get("0").getTextArea().append(obj.getContent() + "\n");

        if (gui.getTabDataMap().get(obj.getIdName()[0]) != null) {
            gui.getTabDataMap().get(obj.getIdName()[0]).getTextArea().setForeground(Color.GRAY);
            int tabIndex = gui.getTabbedPane().indexOfTab(client.getIds().get(obj.getIdName()[0]));
            if (tabIndex != -1) {
                gui.getTabbedPane().setTitleAt(tabIndex, "~ " + client.getIds().get(obj.getIdName()[0]) + " ~");
            }
            gui.getTabDataMap().get(obj.getIdName()[0]).getTextArea().append("!!! This user has left the chat !!!");
            gui.getTabDataMap().get(obj.getIdName()[0]).setTextField(false, false);
        }
        // Remove user IDs and update GUI
        client.getIds().remove(obj.getIdName()[0]);
        client.getNameIds().remove(obj.getIdName()[1]);
        gui.getTabDataMap().remove(obj.getIdName()[0]);
        gui.updateRecipientMenu();
    }

    // Handling CurrentCoordinator
    private void handleCurrentCoordinator(Carrier obj, GUI gui) {
        // If current coordinator information is received, update GUI
        gui.getTabDataMap().get("1").getTextArea().append(obj.getCoordinator() + " is the Coordinator" + "\n");
    }

    // Handling DisplayInfo
    private void handleDisplayInfo(Carrier obj, GUI gui) {
        // If display information is received, update GUI and notifications
        gui.getTabDataMap().get("0").getTextArea().append(obj.getContent() + "\n");
        if (!gui.getCurrentTab().equals("0")) {
            int counter = gui.getTabDataMap().get("0").getCounter();
            counter++;
            gui.getTabDataMap().get("0").setCounter(counter);
            gui.updateNotificationLabel("0", String.valueOf(counter));
        }
    }

    // Handling Messages
    private void handleMessages(Carrier obj, Client client, GUI gui) {
        // If messages are received, handle accordingly
        if (client.getMyId().equals(obj.getSender())) {
            // If the message sender is the client, display message in GUI
            gui.getTabDataMap().get(obj.getReceiver()).getTextArea().append("You: " + obj.getContent() + "\n");
        } else {
            // If the message sender is someone else, handle accordingly
            if (!client.getIds().containsKey(obj.getSender())) {
                // If sender information is not available, update client IDs and GUI
                Map<String, String> tempIds = client.getIds();
                Map<String, String> tempNameIds = client.getNameIds();
                tempIds.put(obj.getIdName()[0], obj.getIdName()[1]);
                tempNameIds.put(obj.getIdName()[1], obj.getIdName()[0]);
                client.setIds(tempIds);
                client.setNameIds(tempNameIds);
                gui.updateRecipientMenu();
            }
            if (!gui.getTabDataMap().containsKey(obj.getSender())) {
                // If tab for sender is not available, create a new tab
                gui.createTab(obj.getSender());
            } else {
                // If tab for sender exists, retrieve it
                if (!gui.hasTab(gui.getTabbedPane(), client.getIds().get(obj.getSender()))) {
                    gui.retrieveTab(obj.getSender());
                }
            }
            // Display message in GUI and update notifications
            gui.getTabDataMap().get(obj.getSender()).getTextArea().append(client.getIds().get(obj.getSender()) + ": " + obj.getContent() + "\n");
            if (!gui.getCurrentTab().equals(obj.getSender())) {
                int counter = gui.getTabDataMap().get(obj.getSender()).getCounter();
                counter++;
                gui.getTabDataMap().get(obj.getSender()).setCounter(counter);
                gui.updateNotificationLabel(obj.getSender(), String.valueOf(counter));
            }
        }
    }

    // Handling MemberDetailsUpdate
    private void handleMemberDetailsUpdate(Carrier obj, Client client, GUI gui) {
        // If member details are updated, update GUI and client information
        if (!gui.getCurrentTab().equals("1")) {
            int counter = gui.getTabDataMap().get("1").getCounter();
            counter++;
            gui.getTabDataMap().get("1").setCounter(counter);
            gui.updateNotificationLabel("1", String.valueOf(counter));
        }
        if (!obj.getIdName()[0].equals("1") || !obj.getIdName()[0].equals("0")) {
            gui.getTabDataMap().get("1").getTextArea().append(obj.getContent() + "\n");
        }

        // Update client IDs and GUI
        if (client.getIds() == null) {
            Map<String, String> map = new HashMap<>();
            Map<String, String> nameMap = client.getNameIds();
            map.put(obj.getIdName()[0], obj.getIdName()[1]);
            nameMap.put(obj.getIdName()[1], obj.getIdName()[0]);
            client.setNameIds(nameMap);
            client.setIds(map);
        } else {
            Map<String, String> tempIds = client.getIds();
            Map<String, String> tempNameIds = client.getNameIds();
            tempIds.put(obj.getIdName()[0], obj.getIdName()[1]);
            tempNameIds.put(obj.getIdName()[1], obj.getIdName()[0]);
            client.setNameIds(tempNameIds);
            client.setIds(tempIds);
        }
    }

    // Handling AuthRequest
    private void handleAuthRequest(Carrier obj, Client client, GUI gui) throws IOException {
        // If authentication request is received, prompt user and send response
        int response = JOptionPane.showConfirmDialog(
            gui.getFrame(),
            obj.getIdName()[1] + " is requesting users details. Do you want to share it?",
            "Member Details Request",
            JOptionPane.YES_NO_OPTION
        );
        if (response == JOptionPane.YES_OPTION) {
            // If user agrees, set authentication and send response
            obj.setAuth(true);
            obj.setReceiver(obj.getIdName()[0]);
            obj.setSender(client.getMyId());
            client.getOutputStream().writeObject(obj);
        }
    }

    // Handling UpdateMenu
    private void handleUpdateMenu(GUI gui) {
        gui.updateRecipientMenu();
    }

    // Handling ActiveCheck
    private void handleActiveCheck(Carrier obj, GUI gui) {
        // If active check is received, update active members list in GUI
        gui.setActiveMembersListData(obj.getActiveMembers().toArray(new String[0]));
    }
}
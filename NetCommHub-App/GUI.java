import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

public class GUI {

    // HashMap to store data related to each tab
    private HashMap<String, TabData> tabDataMap = new HashMap<>();

    // List to display active members
    private JList<String> activeMembersList = new JList<String>();

    // ID of the recipient
    private String receiverID;

    // Main frame
    private JFrame frame = new JFrame("Chatter");

    // Tabbed pane to manage multiple chats
    private JTabbedPane tabbedPane = new JTabbedPane();

    // Panel to display active members information
    private JPanel activeMembersInfo = new JPanel(new BorderLayout());

    // Text area to display messages
    private JTextArea messageArea = new JTextArea();

    // Scroll pane for the message area
    private JScrollPane scrollPane = new JScrollPane(messageArea);

    // Menu for selecting recipients
    private JMenu recipientMenu = new JMenu("Recipient  ▼");

    // Button to request detailed information
    private JButton detailRequest = new JButton("Detail Request");

    // ID of the recipient
    private String recipient;

    // ID of the current tab
    private String currentTab = "1";

    // Reference to the client object
    private Client client;

    // Constructor
    public GUI(Client client) {
        this.client = client;

        // Set active members info panel to initially invisible
        activeMembersInfo.setVisible(false);
        activeMembersInfo.setPreferredSize(new Dimension(200, 0));

        // Add active members list to the panel
        activeMembersInfo.add(new JScrollPane(activeMembersList), BorderLayout.CENTER);

        // Create default tabs
        createTab("1");
        createTab("0");

        // Add components to the frame
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(650, 450);
        frame.getContentPane().add(activeMembersInfo, BorderLayout.EAST);
        frame.add(tabbedPane);

        // Set border for active members info panel
        activeMembersInfo.setBorder(BorderFactory.createTitledBorder("List Of Active Members"));

        // Pack and center the frame
        frame.pack();
        frame.setLocationRelativeTo(null);

        // Add listener for tab change events
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (client.getNameIds().get(tabbedPane.getTitleAt(selectedIndex)) != null) {
                    currentTab = client.getNameIds().get(tabbedPane.getTitleAt(selectedIndex));
                    int counter = 0;
                    recipient = currentTab;
                    tabDataMap.get(currentTab).setCounter(counter);
                    updateNotificationLabel(currentTab, String.valueOf(counter));
                    getLabel(currentTab).setForeground(Color.BLACK);
                } else {
                    System.out.println("The user left the chat");
                }
            }
        });

        // Add listener for recipient menu item selection
        recipientMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipient = recipientMenu.getText();
            }
        });

        // Add listener for detail request button
        detailRequest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Request detailed information about the member
                    MemberDetails clientdata = (MemberDetails) Carrier.createMessage("MemberDetails", client.getMyId(), "");
                    client.getOutputStream().writeObject(clientdata);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Add recipientMenu and detailRequest button to the menu bar
        menuBar.add(recipientMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(detailRequest);
    }

    // Getter methods
    public HashMap<String, TabData> getTabDataMap() {
        return tabDataMap;
    }

    public String getCurrentTab() {
        return currentTab;
    }

    public String getreceiverID() {
        return receiverID;
    }

    // Setter method for recipient ID
    public void setRecviverID(String receiverID) {
        this.receiverID = receiverID;
    }

    // Method to get the label associated with a tab
    public JLabel getLabel(String tabId) {
        CustomTabComponent tabComponent = tabDataMap.get(tabId).getTabComponent();
        if (tabComponent != null) {
            return tabComponent.label;
        }
        return null;
    }

    // Getter and setter methods for active members info panel
    public JPanel getActiveMembersInfo() {
        return activeMembersInfo;
    }

    public void setActiveMembersInfo(JPanel activeMembersInfo) {
        this.activeMembersInfo = activeMembersInfo;
    }

    // Getter and setter methods for recipient
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    // Getter methods for frame, message area, and tabbed pane
    public JFrame getFrame() {
        return frame;
    }

    public JTextArea getMessageArea() {
        return tabDataMap.get(currentTab).getTextArea();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JMenu getRecipientMenu() { 
        return recipientMenu;
    }

    // Setter method for active members list data
    public void setActiveMembersListData(String[] data) {
        activeMembersList.setListData(data);
    }

    // Method to make the frame visible
    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Method to update the recipient menu
    public void updateRecipientMenu() {
        recipientMenu.removeAll();
        if (!client.getIds().containsKey("0")) {
            client.getIds().put("0", "Broadcast");
        }
        for (String id : client.getIds().keySet()) {
            if (!id.equals(client.getMyId())) {
                JMenuItem userItem = new JMenuItem(client.getIds().get(id));
                userItem.addActionListener(e -> {
                    if (!tabDataMap.containsKey(id)) {
                        createTab(id);
                    } else {
                        if (!hasTab(tabbedPane, client.getIds().get(id))) {
                            retrieveTab(id);
                        }
                    }
                });
                if (!userItem.getText().equals("Chat Info") && !userItem.getText().equals("Broadcast")) {
                    recipientMenu.add(userItem);
                }
            }
        }
        recipientMenu.revalidate();
        recipientMenu.repaint();
    }

    public void createTab(String id) {
        int counter = 0;
    
        // Create a new JPanel
        JPanel panel = new JPanel(new BorderLayout());
        JTextField textField = new JTextField();
    
        // Create a new JTextArea
        JTextArea textArea = new JTextArea(16, 50);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    
        // Add the JTextArea and JTextField to the panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(textField, BorderLayout.SOUTH);
    
        client.getNameIds().put(client.getIds().get(id), id);
    
        // Add tab to the tabbed pane
        tabbedPane.addTab(client.getIds().get(id), panel);
    
        // Set tab component based on closable status
        if (!id.equals("0") && !id.equals("1")) {
            int index = tabbedPane.indexOfTab(client.getIds().get(id));
            if (index != -1) {
                CustomTabComponent tabComponent = new CustomTabComponent(tabbedPane, client.getIds().get(id), true);
                tabbedPane.setTabComponentAt(index, tabComponent);
                TabData tabData = new TabData(textArea, tabComponent, counter, textField);
                tabDataMap.put(id, tabData);
            }
        } else {
            CustomTabComponent tabComponent = new CustomTabComponent(tabbedPane, client.getIds().get(id), false);
            int index = tabbedPane.indexOfTab(client.getIds().get(id));
            if (index != -1) {
                tabbedPane.setTabComponentAt(index, tabComponent);
                TabData tabData = new TabData(textArea, tabComponent, counter, textField);
                tabDataMap.put(id, tabData);
            }
        }
    
        // Disable text field for specific tabs
        if (id.equals("1")) {
            tabDataMap.get("1").setTextField(false, false);
        }
    }

    // Method to update notification label of a tab
    public void updateNotificationLabel(String tabId, String text) {
        // Get the NotificationTabComponent for the tab
        CustomTabComponent tabComponent = tabDataMap.get(tabId).getTabComponent();

        // Update the notification label
        if (tabComponent != null) {
            tabComponent.updateNotificationLabel(text);
        }
    }

    // Custom tab component class
    private class CustomTabComponent extends JPanel {
        JLabel label;
        private JLabel notificationLabel;

        // Constructor
        private CustomTabComponent(JTabbedPane tabbedPane, String title, boolean isClosable) {
            // Ensure the panel is transparent
            setOpaque(false);

            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

            label = new JLabel(title);
            label.setName(title);
            add(label);
            label.setForeground(Color.BLACK);

            // Create a label with the title
            notificationLabel = new JLabel();
            notificationLabel.setForeground(Color.RED);
            add(notificationLabel, BorderLayout.NORTH);

            // Create a close button for the tab if closable
            if (isClosable) {
                JButton closeButton = new JButton("<html><div style='color:red; font-size: 9px;'>x</div></html>");
                closeButton.setBorderPainted(true);
                closeButton.setContentAreaFilled(true);
                Dimension closeButtonSize = new Dimension(15, 15);
                closeButton.setPreferredSize(closeButtonSize);
                closeButton.setMargin(new Insets(0, 1, 2, 2));
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Prompt user before closing the chat
                        int response = JOptionPane.showConfirmDialog(
                                getFrame(), "Do you want to close this chat?",
                                "Closing chat", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {
                            int tabIndex = tabbedPane.indexOfTabComponent(CustomTabComponent.this);
                            if (tabIndex != -1) {
                                tabbedPane.removeTabAt(tabIndex);
                            }
                        }
                    }
                });
                add(closeButton);
            }
        }

        // Method to update notification label
        public void updateNotificationLabel(String text) {
            label.setForeground(Color.RED);
            notificationLabel.setText(text);
            notificationLabel.setVisible(!text.equals("0"));
            if (label.getName() != null) {
                tabDataMap.get(client.getNameIds().get(label.getName())).setCounter(Integer.parseInt(text));
            } else {
                System.out.println("Label name is null");
            }
        }
    }

    // TabData class to store data related to each tab
    public class TabData {
        private JTextArea textArea;
        private CustomTabComponent tabComponent;
        private int counter;
        private JTextField textField;

        // Constructor
        public TabData(JTextArea textArea, CustomTabComponent tabComponent, int counter, JTextField textField) {
            this.textArea = textArea;
            this.tabComponent = tabComponent;
            this.counter = counter;
            this.textField = textField;
            this.textField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Create a message instance with the content from the text field
                        Messages clientdata = (Messages) Carrier.createMessage("Messages", getTabDataMap().get(currentTab).getTextField().getText(), client.getMyId(), recipient, new String[]{client.getMyId(), client.getIds().get(client.getMyId())});
                        // Send the message instance to the server
                        client.getOutputStream().writeObject(clientdata);
                        // Clear the text field after sending
                        getTabDataMap().get(currentTab).getTextField().setText("");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    receiverID = recipient;
                }
            });
        }

        // Getter methods
        public JTextField getTextField() {
            return textField;
        }

        public JTextArea getTextArea() {
            return textArea;
        }

        public CustomTabComponent getTabComponent() {
            return tabComponent;
        }

        public int getCounter() {
            return counter;
        }

        // Setter methods
        public void setCounter(int counter) {
            this.counter = counter;
        }

        public void setTextArea(JTextArea textArea) {
            this.textArea = textArea;
        }

        public void setTabComponent(CustomTabComponent tabComponent) {
            this.tabComponent = tabComponent;
        }

        // Method to enable or disable text field
        public void setTextField(boolean visible, boolean editable) {
            textField.setVisible(visible);
            textField.setEditable(editable);
        }
    }

    // Method to retrieve an existing tab
    public void retrieveTab(String id) {
        // Create a new JPanel
        JPanel panel = new JPanel(new BorderLayout());
        JTextField textField = tabDataMap.get(id).getTextField();
        // Create a new JTextArea and JTextField
        JTextArea textArea = tabDataMap.get(id).getTextArea();
        // Add the JTextArea and JTextField to the panel
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(textField, BorderLayout.SOUTH);
        client.getNameIds().put(client.getIds().get(id), id);
        CustomTabComponent tabComponent = tabDataMap.get(id).getTabComponent();
        tabbedPane.addTab(client.getIds().get(id), panel);
        tabbedPane.setTabComponentAt(tabbedPane.indexOfTab(client.getIds().get(id)), tabComponent);
    }

    // Method to check if a tab exists
    public boolean hasTab(JTabbedPane tabbedPane, String tabTitle) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            String title = tabbedPane.getTitleAt(i);
            if (title.equals(tabTitle)) {
                return true;
            }
        }
        return false;
    }
}
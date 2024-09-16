// Login component of the GUI
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class LoginHandler {
    // JFrame to be used for dialogs
    private JFrame frame = new JFrame();
    
    // TextFields for entering server address and port
    private JTextField addressField = new JTextField();
    private JTextField portField = new JTextField();

    // Method to handle user login input
    public String[] login() {
        // Create a panel with a 3x2 grid layout
        JPanel panel = new JPanel(new GridLayout(3, 2));

        // Add labels and text fields for server address and port to the panel
        panel.add(new JLabel("Address of the server: "));
        panel.add(addressField);
        panel.add(new JLabel("Port of the server: "));
        panel.add(portField);

        // Show a dialog with OK and Cancel buttons for user input
        int result = JOptionPane.showConfirmDialog(frame, panel, 
            "Enter details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // If the user clicks OK
        if (result == JOptionPane.OK_OPTION) {
            String address = addressField.getText();
            int port;

            // Check if the IP address is valid
            if (!address.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) {
                JOptionPane.showMessageDialog(frame,
                    "Invalid IP address. Please enter a valid IPv4 address.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return login(); // Recursive call to retry login after an error
            }

            // Check if the port number is valid
            try {
                port = Integer.parseInt(portField.getText());
                if (port < 0 || port > 65535) {
                    JOptionPane.showMessageDialog(frame,
                        "Port number out of range. Please enter a number between 0 and 65535.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return login(); // Recursive call to retry login after an error
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame,
                    "Invalid port number. Please enter a valid integer.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return login(); // Recursive call to retry login after an error
            }

            return new String[]{address, String.valueOf(port)};
        } else {
            return null; // User clicked Cancel
        }
    }

    // Method to get the user's screen name
    public String getName() {
        String name = null;
        do {
            // Show input dialog for the user to enter a screen name
            name = JOptionPane.showInputDialog(
                frame,
                "Choose a screen name: ",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE
            );
    
            // Validate the entered username
            if (name == null || name.trim().isEmpty() || !name.matches("^[a-zA-Z]+$")) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Invalid username. Please enter a non-empty string of characters.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } while (name == null || name.trim().isEmpty() || !name.matches("^[a-zA-Z]+$"));
        return name;
    }
}
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.awt.event.ActionEvent;
 
 
public class GUITest {
    private GUI gui;
 
    @Before
    public void setUp() {
        var mockedClient = new Client();
        gui = new GUI(mockedClient);
    }
 
    @Test
    public void testGUI() {
        // Test that the GUI is not null
        assertNotNull(gui);
    }
 
    @Test
    public void testCreateTab() {
 
        gui.createTab("0");
        assertNotNull(gui.getTabDataMap().get("0"));
    }
 
    @Test
    public void testUpdateRecipientMenu() {
        // Test updating recipient menu
        gui.updateRecipientMenu();
    }
 
    @Test
    public void testHasTab() {
        // Test that the tab is not null
        gui.createTab("0");
        assertTrue(gui.hasTab(gui.getTabbedPane(), "Broadcast"));
        assertFalse(gui.hasTab(gui.getTabbedPane(), "Client X"));
    }
 
    @Test
    public void testRecipientMenuActionListener() {
        // Simulate a click on the recipientMenu
        gui.getRecipientMenu().getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
 
        // Verify that the recipient variable is updated as expected
        assertEquals("Recipient should be updated", gui.getRecipient(), gui.getRecipientMenu().getText());
    }
}
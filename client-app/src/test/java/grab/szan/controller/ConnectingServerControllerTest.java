package grab.szan.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import grab.szan.utils.AlertCaptor;
import grab.szan.utils.Utils;
import javafx.application.Platform;
import javafx.scene.control.TextField;

/**
 * Test class for ConnectingServerController.
 * This test uses reflection to access private fields and methods.
 */
public class ConnectingServerControllerTest {

    private ConnectingServerController controller;

    // Initialize JavaFX toolkit; can be called only once.
    @BeforeClass
    public static void initJFX() throws InterruptedException {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already initialized, ignore.
        }
        // Wait a bit to ensure toolkit is fully initialized.
        Thread.sleep(500);
    }

    @Before
    public void setUp() throws Exception {
        // Reset the alert captor.
        AlertCaptor.reset();

        // Set the alert handler in Utils so that, in test mode, alerts are captured by AlertCaptor.
        Utils.setAlertHandler(new BiConsumer<String, String>() {
            @Override
            public void accept(String title, String message) {
                AlertCaptor.captureAlert(title, message);
            }
        });

        // Create an instance of the controller.
        controller = new ConnectingServerController();

        // Use reflection to set the private fields hostField and portField.
        Field hostFieldField = ConnectingServerController.class.getDeclaredField("hostField");
        hostFieldField.setAccessible(true);
        hostFieldField.set(controller, new TextField());

        Field portFieldField = ConnectingServerController.class.getDeclaredField("portField");
        portFieldField.setAccessible(true);
        portFieldField.set(controller, new TextField());
    }

    @Test
    public void testOnConnectEmptyFields() throws Exception {
        // Set the text in the host and port fields to empty.
        Field hostFieldField = ConnectingServerController.class.getDeclaredField("hostField");
        hostFieldField.setAccessible(true);
        TextField hostField = (TextField) hostFieldField.get(controller);
        hostField.setText("");

        Field portFieldField = ConnectingServerController.class.getDeclaredField("portField");
        portFieldField.setAccessible(true);
        TextField portField = (TextField) portFieldField.get(controller);
        portField.setText("");

        // Invoke the private onConnect() method via reflection.
        Method onConnectMethod = ConnectingServerController.class.getDeclaredMethod("onConnect");
        onConnectMethod.setAccessible(true);
        onConnectMethod.invoke(controller);

        // We assume that for empty fields, the controller calls Utils.showAlert,
        // which in test mode calls AlertCaptor.captureAlert(title, message).
        String alertTitle = AlertCaptor.getLastAlertTitle();
        String alertContent = AlertCaptor.getLastAlertContent();

        assertNotNull("Alert should have been captured", alertTitle);
        assertEquals("Error", alertTitle);
        assertNotNull("Alert content should have been captured", alertContent);
        assertTrue("Alert content should mention that all fields must be filled!",
                   alertContent.contains("All fields must be filled!"));
    }
}

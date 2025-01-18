package grab.szan.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

import grab.szan.Client;
import grab.szan.utils.AlertCaptor;
import grab.szan.utils.Utils;
import javafx.application.Platform;
import javafx.scene.control.TextField;

public class JoinGameControllerTest {

    private JoinGameController controller;
    private MockedStatic<Client> mockedClientStatic;
    private Client mockClient;

    // Initialize JavaFX toolkit and set system property for Byte Buddy (Java 23)
    @BeforeClass
    public static void initJFX() throws InterruptedException {
        System.setProperty("net.bytebuddy.experimental", "true");
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already initialized.
        }
        Thread.sleep(500);
    }

    @Before
    public void setUp() throws Exception {
        // Reset AlertCaptor.
        AlertCaptor.reset();
        // Set the alert handler in Utils so that alerts are redirected to AlertCaptor.
        Utils.setAlertHandler(new BiConsumer<String, String>() {
            @Override
            public void accept(String title, String message) {
                AlertCaptor.captureAlert(title, message);
            }
        });
        // Alternatively, using lambda:
        // Utils.setAlertHandler((title, message) -> AlertCaptor.captureAlert(title, message));

        // Mock the static method Client.getInstance() using Mockito.
        mockedClientStatic = mockStatic(Client.class);
        mockClient = mock(Client.class);
        // When Client.getInstance() is called, return our mock.
        mockedClientStatic.when(Client::getInstance).thenReturn(mockClient);

        // Create an instance of JoinGameController.
        controller = new JoinGameController();

        // Set the private fields of the controller (gameNameTextField and nicknameTextField) using reflection.
        setPrivateField("gameNameTextField", new TextField());
        setPrivateField("nicknameTextField", new TextField());
    }

    @After
    public void tearDown() throws Exception {
        // Close the static mock.
        mockedClientStatic.close();
    }

    /**
     * Helper method to set private fields using reflection.
     */
    private void setPrivateField(String fieldName, Object value) throws Exception {
        Field field = JoinGameController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    /**
     * Helper method to get private fields using reflection.
     */
    private Object getPrivateField(String fieldName) throws Exception {
        Field field = JoinGameController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(controller);
    }
    
    @Test
    public void testOnJoinEmptyFields() throws Exception {
        // Set fields to empty.
        ((TextField) getPrivateField("gameNameTextField")).setText("");
        ((TextField) getPrivateField("nicknameTextField")).setText("");
        
        // Invoke the private onJoin() method using reflection.
        Method onJoinMethod = JoinGameController.class.getDeclaredMethod("onJoin");
        onJoinMethod.setAccessible(true);
        onJoinMethod.invoke(controller);
        
        // We expect that Utils.showAlert is called, which (in test mode)
        // will forward the alert to AlertCaptor.
        String alertTitle = AlertCaptor.getLastAlertTitle();
        String alertContent = AlertCaptor.getLastAlertContent();
        
        assertNotNull("Alert should have been captured", alertTitle);
        assertEquals("Error", alertTitle);
        assertNotNull("Alert content should have been captured", alertContent);
        assertTrue("Alert content should mention that all fields must be filled",
                   alertContent.contains("All fields must be filled!"));
        
        // Verify that Client.getInstance().sendToServer() was never called.
        verify(mockClient, never()).sendToServer(anyString());
    }
    
    @Test
    public void testOnJoinValidFields() throws Exception {
        // Set valid data.
        ((TextField) getPrivateField("gameNameTextField")).setText("testRoom");
        ((TextField) getPrivateField("nicknameTextField")).setText("testNick");
        
        // Invoke the onJoin() method using reflection.
        Method onJoinMethod = JoinGameController.class.getDeclaredMethod("onJoin");
        onJoinMethod.setAccessible(true);
        onJoinMethod.invoke(controller);
        
        // Expect that the controller calls Client.getInstance().sendToServer("join testRoom testNick")
        String expectedMessage = "join testRoom testNick";
        verify(mockClient, times(1)).sendToServer(expectedMessage);
        
        // Verify that no alert has been triggered.
        assertNull("No alert should have been captured", AlertCaptor.getLastAlertTitle());
        assertNull("No alert content should have been captured", AlertCaptor.getLastAlertContent());
    }
}

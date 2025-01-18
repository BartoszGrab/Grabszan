package grab.szan.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import grab.szan.Client;
import grab.szan.utils.AlertCaptor;
import grab.szan.utils.Utils;
import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CreateGameControllerTest {

    private CreateGameController controller;
    private MockedStatic<Client> mockedClientStatic;
    private Client mockClient;

    // Initialization of the JavaFX toolkit and setting properties for Byte Buddy (Java 23)
    @BeforeClass
    public static void initJFX() throws InterruptedException {
        // Set the system property to allow Byte Buddy to handle Java 23.
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
        // Reset the alert captor.
        AlertCaptor.reset();
        
        // Set the alert handler in Utils to redirect alerts to AlertCaptor.
        Utils.setAlertHandler((title, message) -> {
            AlertCaptor.captureAlert(title, message);
        });

        // Mock the static method Client.getInstance() using Mockito.
        mockedClientStatic = mockStatic(Client.class);
        mockClient = mock(Client.class);
        // When Client.getInstance() is called, return our mock.
        mockedClientStatic.when(Client::getInstance).thenReturn(mockClient);

        // Create an instance of the controller.
        controller = new CreateGameController();

        // Manually set the controller's fields using reflection.
        setPrivateField("gameNameTextField", new TextField());
        setPrivateField("gameModeChoiceBox", createChoiceBox("Classic", "Ying-Yang", "Classic"));
        setPrivateField("noOfPlayersChoiceBox", createChoiceBox(2, 3, 4, 6, 2));
        setPrivateField("nicknameTextField1", new TextField());
    }

    @After
    public void tearDown() throws Exception {
        // Close the static mock for Client.
        mockedClientStatic.close();
    }

    /**
     * Helper method to set private fields using reflection.
     *
     * @param fieldName The name of the private field.
     * @param value     The value to set for the field.
     * @throws Exception if reflection operations fail.
     */
    private void setPrivateField(String fieldName, Object value) throws Exception {
        Field field = CreateGameController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    /**
     * Helper method to create a ChoiceBox with provided items.
     * This method is generic and can be used for ChoiceBox<Integer> or ChoiceBox<String>.
     *
     * @param items The items to add to the ChoiceBox.
     * @param <T>   The type of items in the ChoiceBox.
     * @return A ChoiceBox populated with the provided items.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private <T> ChoiceBox<T> createChoiceBox(T... items) {
        ChoiceBox<T> choiceBox = new ChoiceBox<>();
        if (items.length > 0) {
            // Assume that the last element is the default value.
            T defaultValue = items[items.length - 1];
            for (int i = 0; i < items.length - 1; i++) {
                choiceBox.getItems().add(items[i]);
            }
            choiceBox.setValue(defaultValue);
        }
        return choiceBox;
    }

    /**
     * Helper method to get private fields using reflection.
     *
     * @param fieldName The name of the private field.
     * @return The value of the private field.
     * @throws Exception if reflection operations fail.
     */
    private Object getPrivateField(String fieldName) throws Exception {
        Field field = CreateGameController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(controller);
    }

    @Test
    public void testOnCreateEmptyFields() throws Exception {
        // Set all fields to empty or invalid values.
        ((TextField) getPrivateField("gameNameTextField")).setText("");
        ((ChoiceBox<String>) getPrivateField("gameModeChoiceBox")).setValue(null);
        ((ChoiceBox<Integer>) getPrivateField("noOfPlayersChoiceBox")).setValue(0);
        ((TextField) getPrivateField("nicknameTextField1")).setText("");

        // Invoke the onCreate() method using reflection.
        Method onCreateMethod = CreateGameController.class.getDeclaredMethod("onCreate");
        onCreateMethod.setAccessible(true);
        onCreateMethod.invoke(controller);

        // Expect that Utils.showAlert is called, which redirects the alert to AlertCaptor.
        String alertTitle = AlertCaptor.getLastAlertTitle();
        String alertContent = AlertCaptor.getLastAlertContent();

        assertNotNull("Alert should have been captured", alertTitle);
        assertEquals("Error", alertTitle);
        assertNotNull("Alert content should have been captured", alertContent);
        assertTrue("Alert content should indicate that all fields must be filled",
                   alertContent.contains("All fields must be filled!"));

        // In this case, no message should be sent to the server.
        verify(mockClient, never()).sendToServer(anyString());
    }

    @Test
    public void testOnCreateValidFields() throws Exception {
        // Set valid data.
        ((TextField) getPrivateField("gameNameTextField")).setText("testRoom");
        ((ChoiceBox<String>) getPrivateField("gameModeChoiceBox")).setValue("Classic");
        ((ChoiceBox<Integer>) getPrivateField("noOfPlayersChoiceBox")).setValue(2);
        ((TextField) getPrivateField("nicknameTextField1")).setText("testNick");

        // Invoke the onCreate() method using reflection.
        Method onCreateMethod = CreateGameController.class.getDeclaredMethod("onCreate");
        onCreateMethod.setAccessible(true);
        onCreateMethod.invoke(controller);

        // Expect that the createGame() method calls Client.getInstance().sendToServer(...) with the expected message.
        String expectedMessage = "create testRoom 2 testNick Classic";
        verify(mockClient, times(1)).sendToServer(expectedMessage);

        // Ensure that no alerts were triggered.
        assertNull("No alert should have been captured", AlertCaptor.getLastAlertTitle());
        assertNull("No alert content should have been captured", AlertCaptor.getLastAlertContent());
    }
}

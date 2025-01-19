package grab.szan.utils;

import java.util.HashMap;
import java.util.function.BiConsumer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

/**
 * A utility class containing methods and data structures that assist with showing alerts,
 * managing color maps, and potential future general utilities.
 */
public class Utils {
    private static HashMap<Integer, Color> colorMap;

    /**
     * A static handler for alerts. By default, it displays alerts on the UI.
     * This can be overridden for testing or other purposes.
     */
    private static BiConsumer<String, String> alertHandler = (title, message) -> {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    };

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Utils() {
        throw new InstantiationError("Utils is a pure static class");
    }

    /**
     * Configures the utility class by initializing a default color map.
     */
    public static void configureUtils() {
        colorMap = new HashMap<>();
        colorMap.put(6, Color.LIGHTBLUE);
        colorMap.put(0, Color.DARKGREEN);
        colorMap.put(1, Color.YELLOWGREEN);
        colorMap.put(2, Color.ORANGE);
        colorMap.put(3, Color.RED);
        colorMap.put(4, Color.PURPLE);
        colorMap.put(5, Color.BLUE);
    }

    /**
     * Shows an alert dialog with the given title and message.
     *
     * @param title   the title of the alert
     * @param message the content text of the alert
     */
    public static void showAlert(String title, String message) {
        alertHandler.accept(title, message);
    }

    /**
     * Allows setting a custom alert handler.
     * Useful for testing to intercept alert calls.
     *
     * @param customHandler a {@link BiConsumer} accepting title and message
     */
    public static void setAlertHandler(BiConsumer<String, String> customHandler) {
        alertHandler = customHandler;
    }

    /**
     * Retrieves a {@link Color} from the internal map by ID. 
     * Defaults to {@link Color#BLACK} if the ID is not in the map.
     *
     * @param id an integer representing a color
     * @return the corresponding {@link Color}, or BLACK if not found
     */
    public static Color getColorById(int id) {
        return colorMap.getOrDefault(id, Color.BLACK);
    }
}

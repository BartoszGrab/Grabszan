package grab.szan.utils;

import java.util.HashMap;
import java.util.function.BiConsumer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

public class Utils {
    private static HashMap<Integer, Color> colorMap;

    // Statyczny handler alertów. Domyślnie alert wyświetlany jest na UI.
    private static BiConsumer<String, String> alertHandler = (title, message) -> {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    };

    // Klasa ma prywatny konstruktor, aby zapobiec instancjonowaniu.
    private Utils() {
        throw new InstantiationError("Utils is a pure static class");
    }

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

    public static void showAlert(String title, String message) {
        // Wywołujemy zdefiniowany handler – domyślnie pokazuje okno dialogowe,
        // a w testach można ustawić alternatywną implementację.
        alertHandler.accept(title, message);
    }

    /**
     * Pozwala ustawić niestandardowy handler alertów.
     * Przydatne np. w testach, aby przechwycić wywołania showAlert.
     *
     * @param customHandler handler, który przyjmie tytuł i treść alertu
     */
    public static void setAlertHandler(BiConsumer<String, String> customHandler) {
        alertHandler = customHandler;
    }

    public static Color getColorById(int id) {
        return colorMap.getOrDefault(id, Color.BLACK);
    }
}

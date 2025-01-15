package grab.szan.utils;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

public class Utils {
    private static HashMap<Integer, Color> colorMap;

    private Utils(){
        throw new InstantiationError("Utils is pure static class");
    }

    public static void configureUtils(){
        colorMap = new HashMap<>();
        colorMap.put(6, Color.LIGHTBLUE);
        colorMap.put(0, Color.GREEN);
        colorMap.put(3, Color.RED);
        colorMap.put(1, Color.BLUE);
        colorMap.put(4, Color.ORANGE);
        colorMap.put(2, Color.PINK);
        colorMap.put(5, Color.PURPLE);
    }

    public static void showAlert(String title, String message){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public static Color getColorById(int id){
        return colorMap.getOrDefault(id, Color.BLACK);
    }
}

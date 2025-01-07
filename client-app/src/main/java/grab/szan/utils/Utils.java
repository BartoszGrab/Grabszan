package grab.szan;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Utils {
    public static void showAlert(String title, String message){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}

package grab.szan.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MenuController {

    @FXML
    private void onCreateGame() {
        try {
            ViewManager.showCreateGameView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onJoinGame() {
        //TODO: powinien być wywołany widok dołączania do gry
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Join Game");
        alert.setHeaderText(null);
        alert.setContentText("Joining a game...");
        alert.showAndWait();
    }

    @FXML
    private void onExit() {
        System.exit(0);
    }
}

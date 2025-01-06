package grab.szan.controller;

import javafx.fxml.FXML;

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
        try {
            ViewManager.showJoinGameView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onExit() {
        System.exit(0);
    }
}

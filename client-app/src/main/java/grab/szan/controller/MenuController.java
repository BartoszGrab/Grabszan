package grab.szan.controller;

import javafx.fxml.FXML;

/**
 * Controller for the main menu view.
 * Allows navigation to creating a game, joining a game, or exiting the application.
 */
public class MenuController implements Controller {

    /**
     * Called when the user selects "Create Game".
     * Shows the CreateGameView.
     */
    @FXML
    private void onCreateGame() {
        try {
            ViewManager.showCreateGameView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the user selects "Join Game".
     * Shows the JoinGameView.
     */
    @FXML
    private void onJoinGame() {
        try {
            ViewManager.showJoinGameView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onReplayGame() {
        try {
            ViewManager.showChooseReplayView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the user selects "Exit".
     * Exits the application.
     */
    @FXML
    private void onExit() {
        System.exit(0);
    }
}

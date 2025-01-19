package grab.szan.controller;

import grab.szan.Client;
import grab.szan.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller for the "Join Game" view.
 * Allows the user to specify the name of the game room and the nickname to join with.
 */
public class JoinGameController implements Controller {

    @FXML
    private TextField gameNameTextField;

    @FXML
    private TextField nicknameTextField;

    /**
     * Called when the user clicks the "Join" button.
     * Validates input and sends a 'join' command to the server.
     */
    @FXML
    private void onJoin() {
        String gameName = gameNameTextField.getText();
        String nickname = nicknameTextField.getText();

        if (gameName.isEmpty() || nickname.isEmpty()) {
            Utils.showAlert("Error", "All fields must be filled!");
            return;
        }

        joinGame(gameName, nickname);
    }

    /**
     * Sends a 'join' command to the server with the specified room name and nickname.
     *
     * @param gameName the name of the game room to join
     * @param nickname the user's chosen nickname
     */
    private void joinGame(String gameName, String nickname) {
        try{
            Client.getInstance().sendToServer("join " + gameName + " " + nickname);
        } catch(NullPointerException e){
            e.printStackTrace();
            Utils.showAlert("Unexpected error", "client hasn't been initiated");
        }
    }
}

package grab.szan.controller;

import grab.szan.Client;
import grab.szan.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Controller for the "Create Game" view.
 * Allows the user to specify game name, game mode, number of players, and a nickname.
 */
public class CreateGameController implements Controller {

    @FXML
    private TextField gameNameTextField;

    @FXML
    private ChoiceBox<String> gameModeChoiceBox;

    @FXML
    private ChoiceBox<Integer> noOfPlayersChoiceBox;

    @FXML
    private TextField nicknameTextField1;

    /**
     * Initializes the UI controls with default values for game mode and number of players.
     */
    @FXML
    private void initialize() {
        gameModeChoiceBox.getItems().addAll("Classic", "Ying-Yang");
        gameModeChoiceBox.setValue("Classic");

        noOfPlayersChoiceBox.getItems().addAll(2, 3, 4, 6);
        noOfPlayersChoiceBox.setValue(2);
    }

    /**
     * Called when the user clicks the "Create" button.
     * Validates the input fields and sends a create game command to the server.
     */
    @FXML
    private void onCreate() {
        String gameName = gameNameTextField.getText();
        String gameMode = gameModeChoiceBox.getValue();
        String nickname = nicknameTextField1.getText();
        int noOfPlayers = noOfPlayersChoiceBox.getValue();

        if (gameName.isEmpty() || gameMode == null || noOfPlayers == 0 || nickname.isEmpty()) {
            Utils.showAlert("Error", "All fields must be filled!");
            return;
        }

        createGame(gameName, gameMode, noOfPlayers, nickname);
    }

    /**
     * Sends the 'create' command to the server with the given parameters.
     *
     * @param gameName    the name of the new game room
     * @param gameMode    the game mode (e.g., "Classic", "Ying-Yang")
     * @param noOfPlayers the maximum number of players
     * @param nickname    the user's chosen nickname
     */
    private void createGame(String gameName, String gameMode, int noOfPlayers, String nickname) {
        try {
            Client.getInstance().sendToServer("create " + gameName + " " + noOfPlayers + " " + nickname + " " + gameMode);
        } catch(NullPointerException e){
            e.printStackTrace();
            Utils.showAlert("Unexpected error", "client hasn't been initiated");
        }
    }
}

package grab.szan.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CreateGameController {
    @FXML
    private TextField gameNameTextField;

    @FXML
    private ChoiceBox<String> gameModeChoiceBox;    

    @FXML 
    private ChoiceBox<Integer> noOfPlayersChoiceBox;

    @FXML
    private TextField nicknameTextField;

    @FXML
    private void initialize() {
        gameModeChoiceBox.getItems().addAll("Classic", "Ying-Yang", "Order Out Of Chaos");
        gameModeChoiceBox.setValue("Classic");

        noOfPlayersChoiceBox.getItems().addAll(2, 3, 4, 6);
        noOfPlayersChoiceBox.setValue(2);
    }

    @FXML
    private void onCreate() {
        String gameName = gameNameTextField.getText();
        String gameMode = gameModeChoiceBox.getValue();
        String nickname = nicknameTextField.getText();
        int noOfPlayers = noOfPlayersChoiceBox.getValue();

        if (gameName.isEmpty() || gameMode == null || noOfPlayers == 0 || nickname.isEmpty()) {
            showAlert("Error", "All fields must be filled!");
            return;
        }

        try {
            boolean gameCreationSuccess = createGame(gameName, gameMode, noOfPlayers, nickname);

            if (gameCreationSuccess) {
                //TODO: powinien być wywołany widok pokoju gry
                showAlert("Success", "Game created successfully!");
            } else {
                showAlert("Error", "Failed to create a game!");
            }
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }

    }

    private boolean createGame(String gameName, String gameMode, int noOfPlayers, String nickname) {
        // TODO: do zaimplementowania wysyłanie zapytania do serwera o utworzenie nowej gry

        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

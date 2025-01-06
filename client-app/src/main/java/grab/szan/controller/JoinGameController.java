package grab.szan.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class JoinGameController {
    @FXML
    private TextField gameNameTextField;

    @FXML
    private TextField nicknameTextField;


    @FXML
    private void onJoin() {
        String gameName = gameNameTextField.getText();
        String nickname = nicknameTextField.getText();
        
        if (gameName.isEmpty() || nickname.isEmpty()) {
            showAlert("Error", "All fields must be filled!");
            return;
        }

        try {
            boolean joinSuccess = joinGame(gameName, nickname);

            if (joinSuccess) {
                // TODO: powinien być wywołany widok pokoju gry
                // ViewManager.showGameRoomView();
                showAlert("Success", "Joined the game successfully!");
            } else {
                showAlert("Error", "Failed to join the game!");
            }
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private boolean joinGame(String gameName, String nickname) {
        // TODO: do zaimplementowania wysyłanie zapytania do serwera o dołączenie do gry
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

package grab.szan.controller;
import grab.szan.Client;
import grab.szan.Utils;
import javafx.fxml.FXML;
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
    private TextField nicknameTextField1;

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
        String nickname = nicknameTextField1.getText();      
        int noOfPlayers = noOfPlayersChoiceBox.getValue();

        if (gameName.isEmpty() || gameMode == null || noOfPlayers == 0 || nickname.isEmpty()) {
            Utils.showAlert("Error", "All fields must be filled!");
            return;
        }

        createGame(gameName, gameMode, noOfPlayers, nickname);
    }

    private void createGame(String gameName, String gameMode, int noOfPlayers, String nickname) {
        try{
            Client.getInstance().sendToServer("create " + gameName + " " + noOfPlayers + " " + nickname + " " + gameMode);
        } catch(NullPointerException e){
            e.printStackTrace();
            Utils.showAlert("Unexpected error", "client hasn't been initiated");
        }
        
    }
}

package grab.szan.controller;

import java.io.IOException;

import grab.szan.Client;
import grab.szan.utils.Utils;
import javafx.fxml.FXML;
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
            Utils.showAlert("Error", "All fields must be filled!");
            return;
        }

        joinGame(gameName, nickname);
    }
    
    private void joinGame(String gameName, String nickname) {
        try{
            Client.getInstance().sendToServer("join " + gameName + " " + nickname);
            ViewManager.showGameView();
        } catch(NullPointerException e){
            e.printStackTrace();
            Utils.showAlert("Unexpected error", "client hasn't been initiated");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

package grab.szan.controller;

import grab.szan.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ChooseReplayController implements Controller{
    @FXML
    private TextField gameTextField;

    @FXML
    private void onReplay() {
        Client.getInstance().sendToServer("replay " + gameTextField.getText());
    }
}

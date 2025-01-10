package grab.szan.controller;

import grab.szan.Client;
import grab.szan.boardBuilders.Board;
import grab.szan.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class GameViewController implements Controller{
    @FXML
    private Pane boardPane;

    @FXML
    private ListView<String> playerListView;

    private static Board boardBuilder;

    public static void setBuilder(Board builder){
        boardBuilder = builder;
    }

    public void initialize(){
        boardBuilder.generateBoard(boardPane);
    }

    public static Board getBoardBuilder(){
        return boardBuilder;
    }

    public void addPlayer(String nickname){
        playerListView.getItems().add(nickname);
    }

    @FXML
    private void onStartClick(){
        Utils.showAlert("start", "starting game!");
        Client.getInstance().sendToServer("start");
    }

    @FXML 
    private void onPassClick(){
        Utils.showAlert("pass", "pass!");
    }

    @FXML
    private void onExitClick(){
        Utils.showAlert("exit", "exiting game!");
    }
}

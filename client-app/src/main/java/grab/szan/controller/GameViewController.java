package grab.szan.controller;

import grab.szan.Client;
import grab.szan.boardBuilders.BoardBuilder;
import grab.szan.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class GameViewController {
    @FXML
    private Pane boardPane;

    @FXML
    private ListView<String> playerListView;

    private static BoardBuilder boardBuilder;

    public static void setBuilder(BoardBuilder builder){
        boardBuilder = builder;
    }

    public void initialize(){
        boardBuilder.generateBoard(boardPane);
    }

    public static BoardBuilder getBoardBuilder(){
        return boardBuilder;
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

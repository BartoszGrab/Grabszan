package grab.szan.controller;

import grab.szan.boardBuilders.BoardBuilder;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class GameViewController {
    @FXML
    private Pane gamePane;

    private static BoardBuilder boardBuilder;

    public static void setBuilder(BoardBuilder builder){
        boardBuilder = builder;
    }

    public void initialize(){
        boardBuilder.generateBoard(gamePane);
    }
}

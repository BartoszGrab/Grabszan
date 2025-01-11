package grab.szan.controller;

import grab.szan.Client;
import grab.szan.boardBuilders.Board;
import grab.szan.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
        playerListView.setCellFactory(lv -> new ListCell<String>() {
            @Override 
            protected void updateItem(String item, boolean empty){
                super .updateItem(item, empty);

                if(empty || item == null){
                    return;
                } 

                setText(item);
                Color color = Utils.getColorById(getIndex()+1);
                
                String colorStyle = String.format("-fx-text-fill: rgb(%d, %d, %d);",
                    (int) (color.getRed() * 255),
                    (int) (color.getGreen() * 255),
                    (int) (color.getBlue() * 255));
                setStyle(colorStyle + "-fx-font-weight: bold;");
            }
        });
    }

    public static Board getBoardBuilder(){
        return boardBuilder;
    }

    public void addPlayer(String nickname){
        playerListView.getItems().add(nickname);
    }

    public void clearPlayers(){
        playerListView.getItems().clear();
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

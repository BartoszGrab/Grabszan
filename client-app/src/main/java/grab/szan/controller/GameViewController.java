package grab.szan.controller;

import java.util.ArrayList;
import java.util.List;

import grab.szan.Client;
import grab.szan.boardBuilders.Board;
import grab.szan.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameViewController implements Controller{
    @FXML
    private Pane boardPane;

    @FXML
    private ListView<String> playerListView;

    @FXML
    private Label turnLabel;
    private int currentPlayerIndex = 0;
    private final List<String> players = new ArrayList<>();

    private static Board boardBuilder;

    public static void setBuilder(Board builder){
        boardBuilder = builder;
    }

    public void initialize(){
        Utils.configureUtils();
        boardBuilder.generateBoard(boardPane);
        playerListView.setCellFactory(lv -> new ListCell<String>() {
            @Override 
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);

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

    public void updateTurnLabel(String nickname) {
        Platform.runLater(() -> {
            turnLabel.setText(nickname + "'s turn");
            int playerIndex = players.indexOf(nickname);
            if (playerIndex >= 0) {
                Color playerColor = Utils.getColorById(playerIndex + 1);
                turnLabel.setTextFill(playerColor);
            } else {
                turnLabel.setTextFill(Color.BLACK);
            }
        });
    }
    
    public static Board getBoardBuilder(){
        return boardBuilder;
    }

    public void addPlayer(String nickname) {
        players.add(nickname);
        playerListView.getItems().add(nickname);
        if (players.size() == 1) {
            updateTurnLabel(nickname);
        }
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

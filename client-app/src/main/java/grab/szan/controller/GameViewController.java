package grab.szan.controller;

import java.util.ArrayList;
import java.util.List;

import grab.szan.Client;
import grab.szan.Field;
import grab.szan.boardBuilders.Board;
import grab.szan.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameViewController implements Controller {
    @FXML
    private Pane boardPane;

    @FXML
    private ListView<String> playerListView;

    @FXML
    private Label turnLabel;
    
    private final List<String> players = new ArrayList<>();
    private static Board boardBuilder;
    
    private Field firstField = null;

    public static void setBuilder(Board builder) {
        boardBuilder = builder;
    }

    public void initialize() {
        Utils.configureUtils();
        boardBuilder.generateBoard(boardPane);
        
        // Rejestrujemy obsługę kliknięć na planszy (Pane)
        boardPane.setOnMouseClicked(event -> handleBoardClick(event));
        
        // Konfiguracja ListView graczy
        playerListView.setCellFactory(lv -> new ListCell<String>() {
            @Override 
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                    return;
                } 
                setText(item);
                Color color = Utils.getColorById(getIndex() + 1);
                String colorStyle = String.format("-fx-text-fill: rgb(%d, %d, %d);",
                    (int) (color.getRed() * 255),
                    (int) (color.getGreen() * 255),
                    (int) (color.getBlue() * 255));
                setStyle(colorStyle + "-fx-font-weight: bold;");
            }
        });
    }

    private void handleBoardClick(MouseEvent e) {
        Node node = e.getPickResult().getIntersectedNode();
        
        // Kliknięcie poza obiektami typu Field – anulowanie wyboru
        if (!(node instanceof Field)) {
            if (firstField != null) {
                firstField.removeHighlight();
                firstField = null;
            }
            return;
        }
        
        Field clickedField = (Field) node;
        
        // Jeśli kliknięto pole, które nie należy do Ciebie, a już masz wybrany pionek,
        // traktujemy to jako pole docelowe ruchu
        if (clickedField.getFieldId() != Client.getInstance().getId()) {
            if (firstField != null) {
                Client.getInstance().sendToServer("move " 
                        + firstField.getRow() + " " + firstField.getCol() + " " 
                        + clickedField.getRow() + " " + clickedField.getCol());
                firstField.removeHighlight();
                firstField = null;
            }
            return;
        }
        
        // Kliknięto na własny pionek
        if (firstField == null) {
            // Ustaw zaznaczenie na klikniętym pionku
            firstField = clickedField;
            firstField.highlight();
        } else {
            if (firstField == clickedField) {
                // Kliknięcie na ten sam pionek – anuluj zaznaczenie
                firstField.removeHighlight();
                firstField = null;
            } else {
                // Kliknięto na inny pionek należący do Ciebie – zmień zaznaczenie
                firstField.removeHighlight();
                firstField = clickedField;
                firstField.highlight();
            }
        }
    }

    public static Board getBoardBuilder() {
        return boardBuilder;
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
    
    public void addPlayer(String nickname) {
        players.add(nickname);
        playerListView.getItems().add(nickname);
        if (players.size() == 1) {
            updateTurnLabel(nickname);
        }
    }
    
    public void clearPlayers() {
        players.clear();
        playerListView.getItems().clear();
    }

    @FXML
    private void onStartClick() {
        Utils.showAlert("start", "starting game!");
        Client.getInstance().sendToServer("start");
    }

    @FXML 
    private void onPassClick() {
        Utils.showAlert("pass", "pass!");
    }

    @FXML
    private void onExitClick() {
        Utils.showAlert("exit", "exiting game!");
    }
}

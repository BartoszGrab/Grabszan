package grab.szan.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
    private Board board;

    /**List of colors (color at index i should be a font color of element at index i in playerListView)*/
    private List<Color> colors;
    
    private Field firstField = null;

    public void setBoard(Board board) {
        this.board = board;
        board.generateBoard(boardPane);
    }

    public void initialize() {
        Utils.configureUtils();
        
        // Rejestrujemy obsługę kliknięć na planszy (Pane)
        boardPane.setOnMouseClicked(event -> handleBoardClick(event));

        //at the beggining all colors should be black, we'll later change the colors when the game starts
        colors = Arrays.asList(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);

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
                Color color = colors.get(getIndex());
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

    public Board getBoard() {
        return board;
    }

    public void updateTurnLabel(String nickname) {
        Platform.runLater(() -> {
            turnLabel.setText(nickname + "'s turn");
            int playerIndex = players.indexOf(nickname);
            if (playerIndex >= 0) {
                Color playerColor = colors.get(playerIndex);
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

    /**
     * changes color at i-th position
     * @param i index of color to change
     * @param color color to set
     */
    public void changeColor(int i, Color color){
        colors.set(i, color);
        playerListView.refresh();
    }

    /**
     * returns index of nickname in playerListView
     * @param nickname 
     * @return index of nickname
     */
    public int getId(String nickname){
        return players.indexOf(nickname);
    }

    @FXML
    private void onStartClick() {
        Utils.showAlert("start", "starting game!");
        Client.getInstance().sendToServer("start");
    }

    @FXML 
    private void onPassClick() {
        Client.getInstance().sendToServer("pass");
    }

    @FXML
    private void onExitClick() {
        Utils.showAlert("exit", "exiting game!");
    }
}

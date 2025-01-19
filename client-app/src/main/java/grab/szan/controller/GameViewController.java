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

/**
 * Controller for the in-game view.
 * Manages the board display, player list, and turn information.
 */
public class GameViewController implements Controller {

    @FXML
    private Pane boardPane;

    @FXML
    private ListView<String> playerListView;

    @FXML
    private Label turnLabel;

    /**
     * A list of player nicknames.
     */
    private final List<String> players = new ArrayList<>();

    /**
     * The board instance used in this game view.
     */
    private Board board;

    /**
     * A list of colors where the color at index i should match
     * the font color for the player at index i in the playerListView.
     */
    private List<Color> colors;

    /**
     * The first selected field if a player is in the process of making a move.
     * If not currently selecting a piece, this will be null.
     */
    private Field firstField = null;

    /**
     * Sets the board for this game and generates its fields on the provided Pane.
     *
     * @param board the Board to be set
     */
    public void setBoard(Board board) {
        this.board = board;
        board.generateBoard(boardPane);
    }

    /**
     * Initializes UI-related aspects, including click handlers and player list styling.
     */
    public void initialize() {
        Utils.configureUtils();

        // Register click handling on the board
        boardPane.setOnMouseClicked(event -> handleBoardClick(event));

        // Initially, all colors are black; they will be updated as needed
        colors = Arrays.asList(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);

        // Configure playerListView to display each player with a custom color
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

    /**
     * Handles mouse clicks on the board. Differentiates between selecting a player's own piece
     * and selecting a destination field for a move.
     *
     * @param e the MouseEvent triggered by the click
     */
    private void handleBoardClick(MouseEvent e) {
        Node node = e.getPickResult().getIntersectedNode();

        // If clicked outside any Field, clear selection
        if (!(node instanceof Field)) {
            if (firstField != null) {
                firstField.removeHighlight();
                firstField = null;
            }
            return;
        }

        Field clickedField = (Field) node;

        // If clicked field does not belong to the current player but a piece is already selected,
        // treat this as the destination field for the move
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

        // Otherwise, the clicked field belongs to the current player
        if (firstField == null) {
            // Highlight the newly selected piece
            firstField = clickedField;
            firstField.highlight();
        } else {
            if (firstField == clickedField) {
                // Clicking the same piece again - unselect
                firstField.removeHighlight();
                firstField = null;
            } else {
                // Switching selection to a different piece of the current player
                firstField.removeHighlight();
                firstField = clickedField;
                firstField.highlight();
            }
        }
    }

    /**
     * Gets the board instance used by this controller.
     *
     * @return the {@link Board}
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Updates the turn label to indicate whose turn it is, changing the text color
     * to match that player's color if possible.
     *
     * @param nickname the nickname of the player whose turn it is
     */
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

    /**
     * Adds a new player to the list of players and updates the UI.
     *
     * @param nickname the nickname of the new player
     */
    public void addPlayer(String nickname) {
        players.add(nickname);
        playerListView.getItems().add(nickname);
        if (players.size() == 1) {
            updateTurnLabel(nickname);
        }
    }

    /**
     * Clears the current list of players and updates the UI.
     */
    public void clearPlayers() {
        players.clear();
        playerListView.getItems().clear();
    }

    /**
     * Changes the color for the player at the specified index in the colors list,
     * then refreshes the ListView to reflect the change.
     *
     * @param i     the index of the player
     * @param color the new {@link Color} to use
     */
    public void changeColor(int i, Color color){
        colors.set(i, color);
        playerListView.refresh();
    }

    /**
     * Retrieves the index of a given nickname in the player list.
     *
     * @param nickname the player's nickname
     * @return the index of the nickname or -1 if not found
     */
    public int getId(String nickname){
        return players.indexOf(nickname);
    }

    /**
     * Sends a "start" command to the server, indicating that the game should begin.
     */
    @FXML
    private void onStartClick() {
        Utils.showAlert("start", "starting game!");
        Client.getInstance().sendToServer("start");
    }

    /**
     * Sends a "pass" command to the server, indicating that the current player passes their turn.
     */
    @FXML
    private void onPassClick() {
        Client.getInstance().sendToServer("pass");
    }

    /**
     * Displays an alert to indicate the game is exiting.
     * (The actual exit logic can be implemented as needed.)
     */
    @FXML
    private void onExitClick() {
        Utils.showAlert("exit", "exiting game!");
    }
}

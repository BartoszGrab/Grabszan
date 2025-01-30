package grab.szan.controller;

import grab.szan.boardBuilders.Board;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class ReplayController implements Controller {
    @FXML
    private Pane boardPane;

    private Board board;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board, int numOfPlayers) {
        this.board = board;
        board.generateBoard(boardPane);
        board.initializePieces(numOfPlayers);
    }
}

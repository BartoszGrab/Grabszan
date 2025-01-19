package grab.szan.gameModes;

import java.util.List;

import grab.szan.Player;
import grab.szan.boards.Board;

/**
 * Interface defining the structure and behavior of a game mode.
 */
public interface GameMode {

    /**
     * Returns a list of allowed numbers of players for the game mode.
     *
     * @return a list of integers representing allowed player counts.
     */
    public List<Integer> getAllowedNumOfPlayers();

    /**
     * Returns a board specific to the game mode.
     *
     * @return a new Board object.
     */
    public Board getBoard();

    /**
     * Initializes the pieces on the board based on the game mode and player count.
     *
     * @param board   the Board object representing the game board.
     * @param players the list of players participating in the game.
     */
    public void initializePieces(Board board, List<Player> players);

    /**
     * Checks if there is a winner at the current state of the game.
     *
     * @param board   the Board object representing the game board.
     * @param players the list of players participating in the game.
     * @return the Player who has won, or null if there is no winner.
     */
    public Player getWinner(Board board, List<Player> players);
}

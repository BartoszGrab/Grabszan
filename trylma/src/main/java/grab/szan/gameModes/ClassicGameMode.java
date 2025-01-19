package grab.szan.gameModes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grab.szan.Field;
import grab.szan.Player;
import grab.szan.boards.Board;
import grab.szan.boards.ClassicBoard;

/**
 * ClassicGameMode class implements the GameMode interface for a classic game mode.
 */
public class ClassicGameMode implements GameMode {

    /**
     * Returns a new instance of ClassicBoard.
     * 
     * @return a new ClassicBoard instance.
     */
    @Override
    public Board getBoard() {
        return new ClassicBoard();
    }

    /**
     * Initializes the pieces on the board for the given players.
     * 
     * @param board the board to initialize pieces on.
     * @param players the list of players.
     * @throws IllegalArgumentException if the board is not an instance of ClassicBoard or the number of players is not allowed.
     */
    @Override
    public void initializePieces(Board board, List<Player> players) {
        if (!(board instanceof ClassicBoard)) {
            throw new IllegalArgumentException("wrong board type for this gamemode!");
        }

        if (!getAllowedNumOfPlayers().contains(players.size())) {
            throw new IllegalArgumentException("this game mode can only be played by 2, 3, 4 or 6 players!");
        }

        List<Integer> availableCorners = board.getAvailableCorners(players.size());
        for (int i = 0; i < players.size(); i++) {
            for (Field field : board.getCorner(availableCorners.get(i))) {
                field.setPlayer(players.get(i));
            }

            players.get(i).setId(availableCorners.get(i));
        }
    }

    /**
     * Determines the winner of the game.
     * 
     * @param board the board to check for a winner.
     * @param players the list of players.
     * @return the winning player, or null if there is no winner yet.
     * @throws IllegalArgumentException if the board is not an instance of ClassicBoard.
     */
    @Override
    public Player getWinner(Board board, List<Player> players) {
        if (!(board instanceof ClassicBoard)) {
            throw new IllegalArgumentException("wrong board type for this gamemode!");
        }

        for (int i = 0; i < players.size(); i++) {
            int count = 0;

            Player player = players.get(i);
            int destinationId = (player.getId() + 3) % 6;

            for (Field field : board.getCorner(destinationId)) {
                if (field.getPlayer() != null && field.getPlayer().equals(player)) {
                    count++;
                }
            }

            if (count == 10) {
                return player;
            }
        }

        return null;
    }

    /**
     * Returns the list of allowed number of players for this game mode.
     * 
     * @return a list of allowed number of players.
     */
    @Override
    public List<Integer> getAllowedNumOfPlayers() {
        return new ArrayList<>(Arrays.asList(2, 3, 4, 6));
    }
}

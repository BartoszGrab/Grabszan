package grab.szan.gameModes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import grab.szan.Field;
import grab.szan.Player;
import grab.szan.boards.Board;
import grab.szan.boards.ClassicBoard;

/**
 * A special variation of the classic game mode that supports only two players.
 * Player pieces are placed in two opposite corner groups randomly selected 
 * from the available corners.
 */
public class YingYangMode extends ClassicGameMode {

    /**
     * Initializes pieces on the board for the YingYang game mode.
     * Uses random corners for the two players within specific corner ranges.
     *
     * @param board   the board on which to place the pieces
     * @param players the list of players
     * @throws IllegalArgumentException if the board is not a ClassicBoard 
     *                                  or if the number of players is not allowed
     */
    @Override
    public void initializePieces(Board board, List<Player> players) {
        if(!(board instanceof ClassicBoard)){
            throw new IllegalArgumentException("wrong board type for this gamemode!");
        }

        if(!getAllowedNumOfPlayers().contains(players.size())){
            throw new IllegalArgumentException("this game mode can only be played by two players!");
        }

        Random rand = new Random();
        int idx1 = rand.nextInt(0, 3);
        int idx2 = idx1;

        players.get(0).setId(idx1);
        for(Field field: board.getCorner(idx1)){
            field.setPlayer(players.get(0));
        }

        while(idx2 == idx1){
            idx2 = rand.nextInt(3, 6);
        }

        players.get(1).setId(idx2);
        for(Field field: board.getCorner(idx2)){
            field.setPlayer(players.get(1));
        }
    }

    /**
     * Returns the list of allowed number of players for this game mode.
     *
     * @return a list containing only 2
     */
    @Override
    public List<Integer> getAllowedNumOfPlayers() {
        return new ArrayList<>(Arrays.asList(2));
    }
}

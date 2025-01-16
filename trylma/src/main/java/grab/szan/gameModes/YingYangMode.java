package grab.szan.gameModes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import grab.szan.Field;
import grab.szan.Player;
import grab.szan.boards.Board;
import grab.szan.boards.ClassicBoard;

public class YingYangMode extends ClassicGameMode{
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

    @Override
    public List<Integer> getAllowedNumOfPlayers() {
        return new ArrayList<>(Arrays.asList(2));
    }
}

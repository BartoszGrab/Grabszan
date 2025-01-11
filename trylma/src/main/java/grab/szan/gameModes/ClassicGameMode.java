package grab.szan.gameModes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grab.szan.Field;
import grab.szan.Player;
import grab.szan.boards.Board;
import grab.szan.boards.ClassicBoard;

public class ClassicGameMode implements GameMode {

    @Override
    public Board getBoard(){
        return new ClassicBoard();
    }

    @Override
    public void initializePieces(Board board, List<Player> players) {
        if(!(board instanceof ClassicBoard)){
            throw new IllegalArgumentException("wrong board type for this gamemode!");
        }

        if(!getAllowedNumOfPlayers().contains(players.size())){
            throw new IllegalArgumentException("this game mode can only be played by 2, 3, 4, 5 or 6 players!");
        }
        
        for(int i = 0; i < players.size(); i++){
            for(Field field: board.getCorner(i)){
                field.setPlayer(players.get(i));
            }
        }
    }

    @Override
    public Player getWinner(Board board, List<Player> players) {
        if(!(board instanceof ClassicBoard)){
            throw new IllegalArgumentException("wrong board type for this gamemode!");
        }

        for(int i = 0; i < players.size(); i++) {
            int count = 0;
            int idx = i%2 == 0 ? i+1 : i-1;
            Player player = players.get(i);
            
            for(Field field : board.getCorner(idx)) {
                if(field.getPlayer() != null && field.getPlayer().equals(player)) {
                    count++;
                }
            }

            if(count == 10){
                return player;
            }
        }

        return null;
    }

    @Override
    public List<Integer> getAllowedNumOfPlayers() {
        return new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6));
    }
}

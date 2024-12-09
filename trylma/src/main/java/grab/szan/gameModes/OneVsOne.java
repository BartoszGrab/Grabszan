package grab.szan.gameModes;

import java.util.List;
import grab.szan.Player;
import grab.szan.boards.Board;
import grab.szan.boards.ClassicBoard;

public class OneVsOne implements GameMode {

    private Player player1;
    private Player player2;
    private ClassicBoard classicBoard;

    @Override
    public void initializePieces(Board board, List<Player> players) {
        classicBoard = (ClassicBoard) board;

        player1 = players.get(0);
        player2 = players.get(1);

        for(int i = 0; i < 4; i++){
            for(int j = 12-i; j <= 12+i; j += 2){
                classicBoard.getField(i, j).setPlayer(player1);
            }
        }

        for(int i = 13; i <= 17; i++){
            for(int j = i-4; j <= 28-i; j += 2){
                classicBoard.getField(i, j).setPlayer(player2);
            }
        }
    }

    @Override
    public Player getWinner(Board board, List<Player> players) {
        int count = 0;

        for(int i = 0; i < 4; i++){
            for(int j = 12-i; j <= 12+i; j += 2){
                Player cur = classicBoard.getField(i, j).getPlayer();
                if(cur != null && cur.equals(player2)){
                    count++;
                }
            }
        }

        if(count == 10) return player2;
        count = 0;

        for(int i = 13; i <= 17; i++){
            for(int j = i-4; j <= 28-i; j += 2){
                Player cur = classicBoard.getField(i, j).getPlayer();
                if(cur != null && cur.equals(player1)){
                    count++;
                }
            }
        }

        if(count == 10) return player1;

        return null;
    }
}

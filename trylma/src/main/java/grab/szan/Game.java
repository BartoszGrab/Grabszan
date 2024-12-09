package grab.szan;

import java.util.ArrayList;
import java.util.List;

import grab.szan.boards.Board;
import grab.szan.gameModes.GameMode;

public class Game {
    /*room to nazwa dla danej rozgrywki */
    private String room;
    private int maxPlayers;
    private List<Player> players;
    private GameMode mode;
    private Board board;

    public String getRoom(){
        return room;
    }

    public Game(String room, int maxPlayers, GameMode mode, Board board){
        players = new ArrayList<>();
        this.room = room;
        this.maxPlayers = maxPlayers;
        this.mode = mode;
        this.board = board;
    }

    public boolean addPlayer(Player player){
        if(players.size() < maxPlayers){
            players.add(player);
            return true;
        }
        return false;
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public void broadcast(String message) {
        for (Player player : players) {
            player.sendMessage(message);
        }
    }

    public void move(int startRow, int startCol, int endRow, int endCol, Player player){
        if(!isValidMove(startRow, startCol, endRow, endCol, player)){
            player.sendMessage("invalid move!");
            return;
        }

        board.getField(endRow, endCol).setPlayer(player);
        board.getField(startRow, startCol).setPlayer(null);
    }

    private boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Player player){
        //TODO zaimplementowac sprawdzanie poprawnosci ruchu
        return false;
    }
    
}

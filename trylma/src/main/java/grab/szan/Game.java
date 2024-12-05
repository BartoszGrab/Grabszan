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
    
}

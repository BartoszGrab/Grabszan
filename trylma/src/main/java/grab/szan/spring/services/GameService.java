package grab.szan.spring.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import grab.szan.Game;
import grab.szan.Player;
import grab.szan.database.entities.GameEntity;
import grab.szan.database.repositories.GameRepository;

/**
 * GameService manages the "registry" of active games in memory,
 * plus saving games to DB.
 * 
 * It replaces the old "games" map in Server.
 */
@Service
public class GameService {

    // Replaces the "games" map that used to be in Server
    private final Map<String, Game> games = new HashMap<>();

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Checks if a game with the specified name exists in memory.
     */
    public boolean gameExists(String gameName) {
        return games.containsKey(gameName);
    }

    /**
     * Adds a new game to the in-memory registry.
     */
    public boolean addGame(Game game) {
        if (games.containsKey(game.getRoom())) {
            return false;
        }
        games.put(game.getRoom(), game);
        return true;
    }

    /**
     * Attempts to add a player to the given game.
     */
    public boolean addPlayerToGame(Game game, Player player) {
        return game.addPlayer(player);
    }

    /**
     * Retrieves the in-memory game by name, if needed.
     */
    public Game getGame(String roomName) {
        return games.get(roomName);
    }

    /**
     * Saves a new game row in DB.
     */
    public GameEntity createNewGame(String roomName, int maxPlayers) {
        GameEntity entity = new GameEntity();
        entity.setRoomName(roomName);
        entity.setMaxPlayers(maxPlayers);
        entity.setStartTime(LocalDateTime.now());
        // endTime can remain null until game is finished
        return gameRepository.save(entity);
    }
}

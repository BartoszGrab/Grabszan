package grab.szan.gameModes;

import java.util.HashMap;

/**
 * Handles the management and retrieval of different game modes.
 */
public class GameModeHandler {
    private static GameModeHandler gameModeHandler;

    private HashMap<String, GameMode> gameModeMap;

    /**
     * Private constructor to initialize the game mode mappings.
     */
    private GameModeHandler() {
        gameModeMap = new HashMap<>();
        gameModeMap.put("Classic", new ClassicGameMode());
        gameModeMap.put("Ying-Yang", new YingYangMode());
    }

    /**
     * Retrieves the singleton instance of GameModeHandler.
     *
     * @return the singleton instance of GameModeHandler.
     */
    public static GameModeHandler getGameModeHandler() {
        GameModeHandler localHandler = gameModeHandler;

        // Double-checked locking to ensure thread-safe singleton instantiation.
        if (localHandler == null) {
            synchronized (GameModeHandler.class) {
                localHandler = gameModeHandler;
                if (localHandler == null) {
                    gameModeHandler = localHandler = new GameModeHandler();
                }
            }
        }
        return gameModeHandler;
    }

    /**
     * Maps a command string to a GameMode object.
     *
     * @param gameModeLine the command string representing the game mode.
     * @param gameMode     the GameMode object to map.
     */
    public void addGameMode(String gameModeLine, GameMode gameMode) {
        gameModeMap.put(gameModeLine, gameMode);
    }

    /**
     * Retrieves a GameMode object associated with the given command string.
     *
     * @param gameModeLine the command string representing the game mode.
     * @return the corresponding GameMode object.
     * @throws IllegalArgumentException if the command string does not exist in the mapping.
     */
    public GameMode getGameMode(String gameModeLine) {
        if (!gameModeMap.containsKey(gameModeLine)) {
            throw new IllegalArgumentException("Invalid GameMode line");
        }
        return gameModeMap.get(gameModeLine);
    }
}

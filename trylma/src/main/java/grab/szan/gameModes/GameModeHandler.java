package grab.szan.gameModes;

import java.util.HashMap;

public class GameModeHandler {
    private static GameModeHandler gameModeHandler;

    private HashMap<String, GameMode> gameModeMap;

    private GameModeHandler(){
        gameModeMap = new HashMap<>();
        gameModeMap.put("Classic", new ClassicGameMode());
        gameModeMap.put("Ying-Yang", new YingYangMode());
    }

    /**
     * Metoda pozwalająca na pozyskanie instancji obiektu gameModeHandler
     * 
     * @return instancja gameModeHandler
     */
    public static GameModeHandler getGameModeHandler(){
        GameModeHandler localHandler = gameModeHandler;

        //double-checked locking
        if(localHandler == null){
            synchronized(GameModeHandler.class){
                localHandler = gameModeHandler;
                if(localHandler == null){
                    gameModeHandler = localHandler = new GameModeHandler();
                }
            }
        }
        return gameModeHandler;
    }

   /**
    * Metoda do mapowania polecenia w postaci String z 
    * obiektem typu GameMode 
    * @param GameModeLine - polecenie wpisywane w terminalu
    * @param executable - obiekt implementujacy GameMode z metodą execute
    */
    public void addGameMode(String gameModeLine, GameMode gameMode){
        gameModeMap.put(gameModeLine, gameMode);
    }


    /**
     * Metoda do pozyskiwania obiektu typu GameMode odpowiadającemu wprowadzonej komendzie
     * @param GameModeLine - wprowadzona komenda
     * @return obiekt typu GameMode jeśli wprowadzona komenda istnieje
     * @throws IllegalArgumentException jeślli komenda nie istnieje
     */
    public GameMode getGameMode(String gameModeLine){
        if(!gameModeMap.containsKey(gameModeLine)){
            throw new IllegalArgumentException("invalid GameMode line");
        }
        return gameModeMap.get(gameModeLine);
    }
}

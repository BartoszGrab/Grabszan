package grab.szan.commands;

import java.util.HashMap;

/**
 * klasa do mapowania poleceń
 */
public class CommandHandler {
    private static CommandHandler commandHandler;

    private HashMap<String, Command> commandMap;

    private CommandHandler(){
        commandMap = new HashMap<>();
        commandMap.put("create",  new CreateGameCommand());
        commandMap.put("join", new JoinGameCommand());
        commandMap.put("move", new MoveCommand());
        commandMap.put("start", new StartGameCommand());
    }

    /**
     * Metoda pozwalająca na pozyskanie instancji obiektu CommandHandler
     * 
     * @return instancja CommandHandler
     */
    public static CommandHandler getCommandHandler(){
        CommandHandler localHandler = commandHandler;

        //double-checked locking
        if(localHandler == null){
            synchronized(CommandHandler.class){
                localHandler = commandHandler;
                if(localHandler == null){
                    commandHandler = localHandler = new CommandHandler();
                }
            }
        }
        return commandHandler;
    }

   /**
    * Metoda do mapowania polecenia w postaci String z 
    * obiektem typu Command 
    * @param commandLine - polecenie wpisywane w terminalu
    * @param executable - obiekt implementujacy Command z metodą execute
    */
    public void addCommand(String commandLine, Command executable){
        commandMap.put(commandLine, executable);
    }


    /**
     * Metoda do pozyskiwania obiektu typu Command odpowiadającemu wprowadzonej komendzie
     * @param commandLine - wprowadzona komenda
     * @return obiekt typu Command jeśli wprowadzona komenda istnieje
     * @throws IllegalArgumentException jeślli komenda nie istnieje
     */
    public Command getCommand(String commandLine){
        if(!commandMap.containsKey(commandLine)){
            throw new IllegalArgumentException("invalid command line");
        }
        return commandMap.get(commandLine);
    }

}

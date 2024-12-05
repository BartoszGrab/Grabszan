package grab.szan.commands;

import java.util.HashMap;

/*
 * klasa singleton do obslugi komend w celu latwiejszej komunikacji klient-serwer
 */
public class CommandHandler {
    private static CommandHandler commandHandler;

    private HashMap<String, Command> commandMap;

    private CommandHandler(){
        commandMap = new HashMap<>();
        commandMap.put("create",  new CreateGameCommand());
        commandMap.put("join", new JoinGameCommand());
    }

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

    /*
     * funkcja do mapowania nowych komend
     */
    public void addCommand(String commandLine, Command executable){
        commandMap.put(commandLine, executable);
    }


    public Command getCommand(String commandLine){
        return commandMap.get(commandLine);
    }

}

package grab.szan.commands;

import java.util.HashMap;

/**
 * A class responsible for mapping string commands to their corresponding {@link Command} implementations.
 */
public class CommandHandler {
    private static CommandHandler commandHandler;

    private HashMap<String, Command> commandMap;

    /**
     * Private constructor initializes the command map with default commands.
     */
    private CommandHandler(){
        commandMap = new HashMap<>();
        commandMap.put("display", new DisplayCommand());
        commandMap.put("set", new SetFieldCommand());
        commandMap.put("acceptJoin", new AcceptJoinCommand());
        commandMap.put("updateList", new UpdatePlayerListCommand());
        commandMap.put("updateTurn", new UpdateTurnCommand());
        commandMap.put("setId", new SetIdCommand());
        commandMap.put("changeColor", new ChangePlayerColorCommand());
    }

    /**
     * Provides a singleton instance of {@link CommandHandler}.
     *
     * @return the singleton CommandHandler instance
     */
    public static CommandHandler getCommandHandler(){
        CommandHandler localHandler = commandHandler;

        // double-checked locking
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
     * Adds a command mapping to the command map.
     *
     * @param commandLine the string representing the command
     * @param executable  the {@link Command} implementation
     */
    public void addCommand(String commandLine, Command executable){
        commandMap.put(commandLine, executable);
    }

    /**
     * Retrieves a {@link Command} object corresponding to the given command string.
     *
     * @param commandLine the input command string
     * @return the {@link Command} if it exists
     * @throws IllegalArgumentException if the command does not exist in the map
     */
    public Command getCommand(String commandLine){
        if(!commandMap.containsKey(commandLine)){
            throw new IllegalArgumentException("invalid command line");
        }
        return commandMap.get(commandLine);
    }
}

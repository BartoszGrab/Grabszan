package grab.szan.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handles mapping of command names to Command instances.
 */
@Component
public class CommandHandler {

    private final Map<String, Command> commandMap = new HashMap<>();

    @Autowired
    public CommandHandler(List<Command> commands) {
        for (Command command : commands) {
            commandMap.put(command.getName(), command);
        }
    }

    public Command getCommand(String name) {
        Command cmd = commandMap.get(name.toLowerCase());
        if (cmd == null) {
            throw new IllegalArgumentException("invalid command line");
        }
        return cmd;
    }
}

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
        for (Command c : commands) {
            String simpleName = c.getClass().getSimpleName().toLowerCase();
            if (simpleName.contains("creategame")) {
                commandMap.put("create", c);
            } else if (simpleName.contains("joingame")) {
                commandMap.put("join", c);
            } else if (simpleName.contains("move")) {
                commandMap.put("move", c);
            } else if (simpleName.contains("startgame")) {
                commandMap.put("start", c);
            } else if (simpleName.contains("setnick")) {
                commandMap.put("nick", c);
            } else if (simpleName.contains("pass")) {
                commandMap.put("pass", c);
            }
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

package grab.szan.commands;

import grab.szan.Player;

/**
 * Interface for command pattern.
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @param args   the command arguments
     * @param player the player issuing the command
     */
    public void execute(String[] args, Player player);
}

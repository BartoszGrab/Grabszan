package grab.szan.commands;

import grab.szan.Game;
import grab.szan.Player;

/**
 * A command that allows a player to pass their turn in the game.
 */
public class PassCommand implements Command {

    /**
     * Executes the pass command, which ends the player's current turn without making a move.
     *
     * @param args   the command arguments
     * @param player the player issuing the command
     */
    @Override
    public void execute(String[] args, Player player) {
        // Retrieve the game object
        Game game = player.getActiveGame();

        if (game == null) {
            player.sendMessage("display Error You are not in a game!");
            return;
        }

        // Check if it's currently this player's turn
        if (!game.getCurrentPlayer().equals(player)) {
            player.sendMessage("display Error It's not your turn!");
            return;
        }

        // Notify the player that they have passed
        player.sendMessage("display Info You passed your turn.");

        // Proceed to the next turn
        game.nextTurn();

        // Notify all players of the turn change
        game.broadcast("updateTurn " + game.getCurrentPlayer().getNickname());
    }
}

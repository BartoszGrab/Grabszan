package grab.szan.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import grab.szan.Game;
import grab.szan.Player;
import grab.szan.spring.services.GameService;

/**
 * A command that allows a player to join an existing game by name and set their nickname.
 */
@Component
public class JoinGameCommand implements Command {

    @Autowired
    private GameService gameService;

    /**
     * Executes the join game command.
     * <p>
     * Usage: join <roomName> <nickname>
     *
     * @param args   the command arguments
     * @param player the player issuing the command
     */
    @Override
    public void execute(String[] args, Player player) {
        // Validate arguments
        if (args.length < 3) {
            player.sendMessage("display Error: Game name and nickname are required to join.");
            return;
        }

        String roomName = args[1];
        String nickname = args[2];

        // Check if the specified game exists
        if (!gameService.gameExists(roomName)) {
            player.sendMessage("display Error: Game room '" + roomName + "' does not exist.");
            return;
        }

        // Retrieve the game from the GameService
        Game game = gameService.getGame(roomName);

        if (game == null) {
            player.sendMessage("display Error: Failed to retrieve the game '" + roomName + "'.");
            return;
        }

        // Check for duplicate nickname
        for (Player p : game.getPlayers()) {
            if (p.getNickname().equalsIgnoreCase(nickname)) {
                player.sendMessage("display Error: A player with this nickname already exists.");
                return;
            }
        }

        // Try to add the player to the game via GameService
        boolean playerAdded = gameService.addPlayerToGame(game, player);
        if (playerAdded) {
            player.sendMessage("display Success: You have joined the game '" + roomName + "'.");
            player.setActiveGame(game);
            player.setNickname(nickname);

            // Optionally, update other players about the new player
            game.broadcast("updateList " + nickname);

            // Send information about accepting join request
            // acceptJoin <gameType> <room name> <player Id> <players> <nickname>
            StringBuilder playersList = new StringBuilder();
            for (Player p : game.getPlayers()) {
                playersList.append(p.getNickname()).append(" ");
            }
            player.sendMessage("acceptJoin " + game.getGameType() + " " + roomName + " " + player.getId() + " " + playersList.toString().trim() + " " + nickname);
        } else {
            player.sendMessage("display Error: Game '" + roomName + "' is full.");
        }
    }
}

package grab.szan.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import grab.szan.Game;
import grab.szan.Player;
import grab.szan.database.entities.GameEntity;
import grab.szan.gameModes.GameMode;
import grab.szan.gameModes.GameModeHandler;
import grab.szan.spring.services.GameService;

/**
 * Command to create a new game.
 */
@Component
public class CreateGameCommand implements Command {

    @Autowired
    private GameService gameService;

    @Override
    public void execute(String[] args, Player player) {
        if (args.length < 5) {
            player.sendMessage("display Use: create <name> <num_of_players> <nickname> <gamemode>");
            return;
        }

        String gameName = args[1];
        int maxPlayers;
        try {
            maxPlayers = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage("display Error Number of players must be an integer number");
            return;
        }

        // Check if game exists in memory
        if (gameService.gameExists(gameName)) {
            player.sendMessage("display Error Game with this name already exists");
            return;
        }

        // Validate game mode
        GameMode mode = GameModeHandler.getGameModeHandler().getGameMode(args[4]);
        if (!mode.getAllowedNumOfPlayers().contains(maxPlayers)) {
            player.sendMessage("display Error cannot create room for " + maxPlayers + " players in this gamemode");
            return;
        }

        // Create a new Game object
        Game game = new Game(gameName, maxPlayers, args[4], null);

        // Add the game to the registry
        boolean added = gameService.addGame(game);
        if (!added) {
            player.sendMessage("display Error Couldn't create game, try again");
            return;
        }

        // Add the initiating player
        boolean playerAdded = gameService.addPlayerToGame(game, player);
        if (!playerAdded) {
            player.sendMessage("display Error Couldn't join new game");
            return;
        }

        // Set player's game and nickname
        player.setActiveGame(game);
        player.setNickname(args[3]);

        // Save the game to DB
        GameEntity createdEntity = gameService.createNewGame(gameName, maxPlayers);
        game.setGameEntityId(createdEntity.getId());

        // Final messages
        player.sendMessage("acceptJoin " + args[4] + " " + gameName + " " + player.getId() + " " + args[3]);
        player.sendMessage("display Success created new game: " + gameName
                           + " with maximum number of players: " + maxPlayers);
    }
}

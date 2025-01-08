package grab.szan.commands;

import grab.szan.Game;
import grab.szan.Player;
import grab.szan.Server;
import grab.szan.boards.Board;
import grab.szan.gameModes.GameMode;
import grab.szan.gameModes.GameModeHandler;

public class CreateGameCommand implements Command {

    @Override
    public void execute(String[] args, Player player) {
        if (args.length < 5) {
            player.sendMessage("Use: create <name> <num_of_players> <nickname> <gamemode>");
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

        Server server = Server.getInstance();

        // Sprawdzamy, czy gra o takiej nazwie już istnieje
        if (server.gameExists(gameName)) {
            player.sendMessage("display Error Game with this name already exists");
            return;
        }

        // Wybór trybu gry i planszy 
        GameMode mode = GameModeHandler.getGameModeHandler().getGameMode(args[4]);
        Board board = mode.getBoard();
        board.generateBoard(); // generujemy układ planszy

        // Tworzymy nową grę
        Game game = new Game(gameName, maxPlayers, mode, board);

        // Dodajemy grę do serwera
        boolean added = server.addGame(game);
        if (!added) {
            player.sendMessage("display Error Couldn't create game, try again");
            return;
        }

        // Dodajemy inicjującego gracza do gry
        boolean playerAdded = game.addPlayer(player);

        if (playerAdded) {
            player.sendMessage("display Success created new game: " + gameName + " with maximum number of players: " + maxPlayers);
            player.setActiveGame(game);
            //ustawianie nicku gracza
            player.setNickname(args[3]);
        } else {
            player.sendMessage("display Error Couldn't join new game");
        }
    }
}

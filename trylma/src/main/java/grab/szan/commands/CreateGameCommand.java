package grab.szan.commands;

import grab.szan.Game;
import grab.szan.Player;
import grab.szan.Server;
import grab.szan.boards.ClassicBoard;
import grab.szan.gameModes.GameMode;
import grab.szan.gameModes.OneVsOne; // przykładowa plansza, do dostosowania

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


        // TODO: Sprawdzenie, czy liczba graczy jest poprawna dla wybranego trybu gry
        // TODO: switch case na tryby gry, sprawdzanie liczby graczy (?)

        // Wybór trybu gry i planszy (przykładowe, do zmiany!!!)
        GameMode mode = new OneVsOne(); 
        ClassicBoard board = new ClassicBoard();
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

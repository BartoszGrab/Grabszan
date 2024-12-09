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
        if (args.length < 3) {
            player.sendMessage("Użycie: create <nazwa_gry> <liczba_graczy>");
            return;
        }

        String gameName = args[1];
        int maxPlayers;
        try {
            maxPlayers = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage("Liczba graczy musi być liczbą całkowitą.");
            return;
        }

        Server server = Server.getInstance();

        // Sprawdzamy, czy gra o takiej nazwie już istnieje
        if (server.gameExists(gameName)) {
            player.sendMessage("Gra o takiej nazwie już istnieje.");
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
            player.sendMessage("Nie udało się utworzyć gry. Spróbuj ponownie.");
            return;
        }

        // Dodajemy inicjującego gracza do gry
        boolean playerAdded = game.addPlayer(player);

        if (playerAdded) {
            player.sendMessage("Utworzono nową grę: " + gameName + " z maksymalną liczbą graczy: " + maxPlayers);
            player.setActiveGame(game);
        } else {
            player.sendMessage("Nie udało się dołączyć do nowo utworzonej gry.");
        }
    }
}

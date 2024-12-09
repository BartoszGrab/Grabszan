package grab.szan.commands;

import grab.szan.Game;
import grab.szan.Player;
import grab.szan.Server;

public class JoinGameCommand implements Command {

    @Override
    public void execute(String[] args, Player player) {
        // Sprawdzenie poprawności argumentów
        if (args.length < 2) {
            player.sendMessage("Error: Game name is required to join.");
            return;
        }

        String roomName = args[1]; // Nazwa pokoju z argumentów
        Server server = Server.getInstance();

        // Sprawdzenie, czy gra o podanej nazwie istnieje
        if (!server.gameExists(roomName)) {
            player.sendMessage("Error: Game room '" + roomName + "' does not exist.");
            return;
        }

        // Pobranie gry z serwera
        Game game = server.getGame(roomName);

        // Próba dodania gracza do gry
        if (game.addPlayer(player)) {
            player.sendMessage("Success: You have joined the game '" + roomName + "'.");
            game.broadcast("Player joined the game."); // Informacja dla pozostałych graczy
            player.setActiveGame(game);
        } else {
            player.sendMessage("Error: Game '" + roomName + "' is full.");
        }
    }
}

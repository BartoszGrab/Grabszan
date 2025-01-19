package grab.szan.commands;

import grab.szan.Game;
import grab.szan.Player;

public class PassCommand implements Command {
    @Override
    public void execute(String[] args, Player player) {
        // Pobranie obiektu gry
        Game game = player.getActiveGame();

        if (game == null) {
            player.sendMessage("display Error You are not in a game!");
            return;
        }

        // Sprawdzenie, czy gracz ma obecnie turę
        if (!game.getCurrentPlayer().equals(player)) {
            player.sendMessage("display Error It's not your turn!");
            return;
        }

        // Informacja o przejściu tury
        player.sendMessage("display Info You passed your turn.");

        // Przechodzenie tury
        game.nextTurn();

        // Powiadomienie wszystkich graczy o zmianie tury
        game.broadcast("updateTurn " + game.getCurrentPlayer().getNickname());
    }
}

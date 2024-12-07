package grab.szan.gameModes;

import java.util.List;

import grab.szan.Player;
import grab.szan.boards.Board;

public class OneVsOne implements GameMode {

    @Override
    public void initializePieces(Board board, List<Player> players) {
        // TODO: Zaimplementować rozstawianie 9 pionków dla każdego z graczy w odpowiednich narożach planszy.
    }

    @Override
    public Player getWinner(Board board, List<Player> players) {
        // TODO: Zaimplementować logikę sprawdzającą czy wszystkie pionki jednego z graczy
        // znalazły się w strefie startowej przeciwnika.
        return null;
    }
}

package grab.szan.gameModes;

import java.util.List;

import grab.szan.Player;
import grab.szan.boards.Board;

public interface GameMode {
    public Board getBoard();

    /**
     * Metoda odpowiedzialna za początkowe rozstawienie pionków na planszy
     * w zależności od trybu gry i liczby graczy.
     * 
     * @param board - obiekt reprezentujący planszę do gry
     * @param players - lista graczy biorących udział w grze
     */
    public void initializePieces(Board board, List<Player> players);

    /**
     * Metoda sprawdzająca czy istnieje zwycięzca w danej chwili.
     * Jeżeli istnieje, zwraca gracza, który wygrał, w przeciwnym wypadku null.
     * 
     * @param board - obiekt reprezentujący planszę do gry
     * @param players - lista graczy biorących udział w grze
     * @return Player który wygrał, lub null jeśli brak zwycięzcy
     */
    public Player getWinner(Board board, List<Player> players);
}

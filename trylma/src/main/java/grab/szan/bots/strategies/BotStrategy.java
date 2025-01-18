package grab.szan.bots.strategies;

import grab.szan.boards.Board;

public interface BotStrategy {
    /**
     * algorithm for making moves
     * @param board - board on which bot is playing
     * @param id - id of bot
     */
    public void makeMove(Board board, int id);
}

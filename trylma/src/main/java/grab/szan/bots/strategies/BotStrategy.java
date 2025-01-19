package grab.szan.bots.strategies;

import java.util.List;
import java.util.Map;

import grab.szan.Field;
import grab.szan.bots.Bot;

public interface BotStrategy {
    /**
     * algorithm for making moves
     * @param bot - bot player for we want to make a move
     */
    public void makeMove(Bot bot);

    /**
     * chooses the best possible move
     * @param moves map where the key is a starting field and value is a list of possible destinations
     * @return Field array where [0] is start and [1] is destination of best move
     */
    public Field[] evaluateMoves(Map<Field, List<Field>> moves);
}

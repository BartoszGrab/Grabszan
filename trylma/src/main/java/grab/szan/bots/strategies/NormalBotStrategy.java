package grab.szan.bots.strategies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grab.szan.Field;
import grab.szan.boards.Board;
import grab.szan.bots.Bot;

public class NormalBotStrategy implements BotStrategy{

    @Override
    public void makeMove(Bot bot) {
        Board board = bot.getActiveGame().getBoard();
        Map<Field, List<Field>> moves = new HashMap<>();

        for(int i = 0; i < board.getRows(); i++){
            for(int j = 0; j < board.getCols(); j++){
                Field field = board.getField(i, j);
                if(field == null || field.getPlayer() == null) continue;
                if(field.getPlayer().getId() == bot.getId()){
                    List<Field> possibleMoves = board.getReachableFields(field);
                    moves.put(field, possibleMoves);
                }
            }
        }

        Field[] bestMove = evaluateMoves(moves);
        int startRow, startCol, endRow, endCol;
        startRow = bestMove[0].getRow();
        startCol = bestMove[0].getColumn();
        endRow = bestMove[1].getRow();
        endCol = bestMove[1].getColumn();

        bot.getActiveGame().broadcast("set " + startRow + " " + startCol + " " + 6);
        bot.getActiveGame().broadcast("set " + endRow + " " + endCol + " " + bot.getId());
        bot.getActiveGame().moveCurrentPlayer(startRow, startCol, endRow, endCol);

    }

    @Override
    public Field[] evaluateMoves(Map<Field, List<Field>> moves) {
        for(Map.Entry<Field, List<Field>> entry: moves.entrySet()){
            if(entry.getValue().size()>0){
                return new Field[]{entry.getKey(), entry.getValue().get(0)};
            }
        }
        return null;
    }
    
    
}

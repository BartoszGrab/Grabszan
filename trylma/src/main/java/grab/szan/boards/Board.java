package grab.szan.boards;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import grab.szan.Field;

/*
 * abstrakcyjna klasa reprezentujaca plansze do gry
 */
public abstract class Board {
    protected Field[][] fields;
    protected int rows, cols;
    protected int[][] dirs;
    protected List<List<Field>> corners;

    /**maps number of players with list of indexes of corners that can be filled */
    protected HashMap<Integer, List<Integer>> playersToCornersMap;

    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        fields = new Field[rows][cols];
        corners = new ArrayList<>();
        playersToCornersMap = new HashMap<>();
    }

    public int[][] getAvailableDirections(){
        return dirs;
    }

    public abstract void generateBoard();

    public String displayBoard(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                if(fields[i][j]==null){
                    builder.append(" ");
                }else if(fields[i][j].getPlayer() != null){
                    builder.append(""+fields[i][j].getPlayer().getId());
                }else{
                    builder.append("*");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public Field[][] getFields() {
        return fields;
    }

    public Field getField(int row, int col){
        return fields[row][col];
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public List<Field> getCorner(int i){
        return corners.get(i);
    }

    /**
     * returns list of indexes of corners that should be filled when there is numOfPlayers in the game
     * @param numOfPlayers - number of players in the game
     * @return List<Integer> representing indexes of corners
     */
    public List<Integer> getAvailableCorners(int numOfPlayers){
        return playersToCornersMap.get(numOfPlayers);
    }

    /**
     * method to make list of fields you can move to certain position
     * @param startRow - row of the starting position
     * @param startCol - column of the starting position
     * @return List of fields
     */
    public List<Field> getReachableFields(Field start){
        ArrayList<Field> result = new ArrayList<>();
        //najpierw musimy osobno sprawdzic wszystkie miejsca wokol pozycji startowej
        for(int[] nextPos: getAvailableDirections()){
            int newRow = start.getRow() + nextPos[0], newCol = start.getColumn() + nextPos[1];
            if(newRow >= getRows() || newCol >= getCols() || newRow < 0 || newCol < 0) continue;

            if(getField(newRow, newCol) != null && getField(newRow, newCol).getPlayer() == null)
                result.add(getField(newRow, newCol));
        }

        Deque<Field> deque = new ArrayDeque<>();
        HashSet<Field> visitedFields = new HashSet<>();

        deque.offer(start);

        //teraz zajmujemy sie przypadkami kiedy 
        while(!deque.isEmpty()){
            Field curr = deque.poll();
            int x = curr.getRow(), y = curr.getColumn();
            //check if we already visited that field
            if(visitedFields.contains(curr)) continue;

            //add this field to visited fields
            visitedFields.add(curr);
            
            for(int[] nextPos: getAvailableDirections()){
                //przesuwamy sie o 2 miejsca w danym kierunku (stad *2)
                int newRow = x + nextPos[0]*2, newCol = y + nextPos[1]*2;
                if(newRow >= getRows() || newCol >= getCols() || newRow < 0 || newCol < 0) continue;
                if(getField(newRow, newCol) == null) continue;

                //sprawdzamy czy w danym kierunku kolejne miejsce jest zajete i dwa miejsca dalej są puste
                if(getField(x+nextPos[0], y+nextPos[1]).getPlayer() != null && getField(newRow, newCol).getPlayer() == null){
                    deque.offer(getField(newRow, newCol));
                    result.add(getField(newRow, newCol));
                }
            }
        }

        return result;
    }
    
}

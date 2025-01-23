package grab.szan.boards;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import grab.szan.Field;

/**
 * An abstract class representing a game board. It provides the basic structure 
 * and utility methods for managing fields, corners, and directions for movement.
 */
public abstract class Board {
    
    /**
     * A 2D array of Fields representing the board layout.
     */
    protected Field[][] fields;

    /**
     * The number of rows in the board.
     */
    protected int rows;

    /**
     * The number of columns in the board.
     */
    protected int cols;

    /**
     * Possible directions in which moves can be made.
     */
    protected int[][] dirs;

    /**
     * A list of corner regions on the board, each corner represented as a list of Fields.
     */
    protected List<List<Field>> corners;

    /**
     * Maps the number of players to the indices of corners that should be used for that number of players.
     */
    protected HashMap<Integer, List<Integer>> playersToCornersMap;

    /**
     * Constructs a Board with the specified number of rows and columns.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        fields = new Field[rows][cols];
        corners = new ArrayList<>();
        playersToCornersMap = new HashMap<>();
    }

    /**
     * Retrieves the available movement directions on this board.
     *
     * @return a 2D array of integers representing movement vectors
     */
    public int[][] getAvailableDirections(){
        return dirs;
    }

    /**
     * Abstract method that must be implemented to build the board layout.
     */
    public abstract void generateBoard();

    /**
     * Returns a textual representation of the board. 
     * '*' indicates an empty field; a digit indicates a field occupied by the player with that ID.
     * Empty or null fields are represented by spaces.
     *
     * @return a string representing the board
     */
    public String displayBoard(){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(fields[i][j] == null){
                    builder.append(" ");
                } else if(fields[i][j].getPlayer() != null){
                    builder.append(fields[i][j].getPlayer().getId());
                } else {
                    builder.append("*");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Gets the entire 2D array of fields for this board.
     *
     * @return the fields array
     */
    public Field[][] getFields() {
        return fields;
    }

    /**
     * Retrieves a specific field from the board.
     *
     * @param row the row index
     * @param col the column index
     * @return the field at (row, col), or null if it does not exist
     */
    public Field getField(int row, int col){
        return fields[row][col];
    }

    /**
     * Gets the number of rows in the board.
     *
     * @return the number of rows
     */
    public int getRows(){
        return rows;
    }

    /**
     * Gets the number of columns in the board.
     *
     * @return the number of columns
     */
    public int getCols(){
        return cols;
    }

    /**
     * Retrieves the list of fields belonging to a particular corner index.
     *
     * @param i the corner index
     * @return the list of fields in that corner
     */
    public List<Field> getCorner(int i){
        return corners.get(i);
    }

    /**
     * Returns the list of corner indices to be filled for a given number of players.
     *
     * @param numOfPlayers the number of players in the game
     * @return a list of corner indices suitable for the specified number of players
     */
    public List<Integer> getAvailableCorners(int numOfPlayers){
        return playersToCornersMap.get(numOfPlayers);
    }

    /**
     * Generates a list of fields reachable from a given starting field, including
     * direct moves and jumps.
     *
     * @param start the starting Field
     * @return a list of reachable Fields
     */
    public List<Field> getReachableFields(Field start){
        ArrayList<Field> result = new ArrayList<>();

        // First, check all adjacent positions around the starting field
        for(int[] nextPos: getAvailableDirections()){
            int newRow = start.getRow() + nextPos[0];
            int newCol = start.getColumn() + nextPos[1];

            if(newRow >= getRows() || newCol >= getCols() || newRow < 0 || newCol < 0) {
                continue;
            }
            if(getField(newRow, newCol) != null && getField(newRow, newCol).getPlayer() == null) {
                result.add(getField(newRow, newCol));
            }
        }

        Deque<Field> deque = new ArrayDeque<>();
        HashSet<Field> visitedFields = new HashSet<>();

        deque.offer(start);

        // Now handle jumps
        while(!deque.isEmpty()){
            Field curr = deque.poll();
            int x = curr.getRow();
            int y = curr.getColumn();

            if(visitedFields.contains(curr)) {
                continue;
            }
            visitedFields.add(curr);

            for(int[] nextPos: getAvailableDirections()){
                // Move 2 positions in the direction
                int newRow = x + nextPos[0]*2;
                int newCol = y + nextPos[1]*2;
                if(newRow >= getRows() || newCol >= getCols() || newRow < 0 || newCol < 0) {
                    continue;
                }
                if(getField(newRow, newCol) == null) {
                    continue;
                }

                // If the adjacent field is occupied and the space beyond is free, jump is possible
                if(getField(x+nextPos[0], y+nextPos[1]).getPlayer() != null 
                   && getField(newRow, newCol).getPlayer() == null) {
                    deque.offer(getField(newRow, newCol));
                    result.add(getField(newRow, newCol));
                }
            }
        }

        return result;
    }

    /**
     * method to access list of fields in the destination corner of player with given id
     * @param id - id of player
     * @return list of fields
     */
    public List<Field> getDestinationCorner(int id){
        return corners.get((id+3)%6);
    }

    /**
     * method to calculate distance between 2 fields
     * @param start - first field
     * @param end - second field
     * @return - distance between those fields
     */
    public int countDistance(Field start, Field end){
        int difRow = Math.abs(start.getRow() - end.getRow());
        int difCol = Math.abs(start.getColumn() - end.getColumn())/2;
        return difRow + difCol;
    }

    /**
     * method to calculate score of move made by player
     * @param id - id of player who made the move
     * @param start - field from which player moves
     * @param end - field where player moves to
     * @return score of this move
     */
    public int moveScore(int id, Field start, Field end) {
        List<Field> destinationCorner = getDestinationCorner(id);
        int max = Integer.MIN_VALUE;
        for(Field destination : destinationCorner) {
            int score = countDistance(start, destination) -  countDistance(end, destination);
            if(destinationCorner.contains(start)) {
                score /= 2;
                if(!destinationCorner.contains(end)){
                    score /= 2;
                }
            }
            else if(destinationCorner.contains(end))
                score += 2;

            max = Math.max(max, score);
        }
        return max;
    }
}

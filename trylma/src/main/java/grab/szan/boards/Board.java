package grab.szan.boards;

import java.util.ArrayList;
import java.util.HashMap;
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
    
}

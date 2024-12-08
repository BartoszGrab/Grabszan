package grab.szan.boards;

import grab.szan.Field;

/*
 * abstrakcyjna klasa reprezentujaca plansze do gry
 */
public abstract class Board {
    protected Field[][] fields;
    protected int rows, cols;

    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        fields = new Field[rows][cols];
    }

    public void generateBoard(){

    }

    public void displayBoard(){
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                if(fields[i][j]==null){
                    System.out.print(" ");
                }else if(fields[i][j].getPlayer() != null){
                    System.out.print(fields[i][j].getPlayer().getMark());
                }else{
                    System.out.print("*");
                }
            }
            System.out.println();
        }
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
    
}

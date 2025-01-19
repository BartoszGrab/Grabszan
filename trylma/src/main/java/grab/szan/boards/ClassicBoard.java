package grab.szan.boards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grab.szan.Field;

/*
 * klasa przedstawiajaca klasyczną plansze do gry w trylme
 */
public class ClassicBoard extends Board{

    public ClassicBoard(){
        super(17, 25);
        dirs = new int[][]{{-1, -1}, {-1, 1}, {0, -2}, {0, 2}, {1, -1}, {1, 1}};

        playersToCornersMap.put(2, Arrays.asList(0, 3));
        playersToCornersMap.put(3, Arrays.asList(0, 2, 4));
        playersToCornersMap.put(4, Arrays.asList(1, 2, 4, 5));
        playersToCornersMap.put(6, Arrays.asList(0, 1, 2, 3, 4, 5));
    }


    @Override
    public void generateBoard(){
        //generowanie gornego naroznika
        List<Field> upperCorner  = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 12-i; j <= 12+i; j += 2){
                fields[i][j] = new Field(i, j);
                upperCorner.add(fields[i][j]);
            }
        }

        //generowanie gornego lewego narożnika
        List<Field> upperLeftCorner = new ArrayList<>();
        for(int i = 4; i <= 7; i++){
            for(int j = i-4; j <= 10 - i; j += 2){
                fields[i][j] = new Field(i, j);
                upperLeftCorner.add(fields[i][j]);
            }
        }

        //generowanie gornego prawego naroznika
        List<Field> upperRightCorner = new ArrayList<>();
        for(int i = 4; i <= 7; i++){
            for(int j = 14 + i; j <= 28 - i; j += 2){
                fields[i][j] = new Field(i, j);
                upperRightCorner.add(fields[i][j]);
            }
        }

        //generowanie środkowej czesci 1.
        for(int i = 4; i <= 8; i++){
            for(int j = 12 - i; j <= 12 + i; j += 2){
                fields[i][j] = new Field(i, j);
            }
        }

        //generowanie srodkowej czesci 2.
        for(int i = 9; i <= 12; i++){
            for(int j = i - 4; j<= 28 - i; j += 2){
                fields[i][j] = new Field(i, j);
            }
        }

        //generowanie dolnego naroznika
        List<Field> bottomCorner = new ArrayList<>();
        for(int i = 13; i <= 17; i++){
            for(int j = i-4; j <= 28-i; j += 2){
                fields[i][j] = new Field(i, j);
                bottomCorner.add(fields[i][j]);
            }
        }

        //generowanie dolnego lewego narożnika
        List<Field> bottomLeftCorner = new ArrayList<>();
        for(int i = 9; i <= 12; i++){
            for(int j = 12 - i; j <= i - 6; j += 2){
                fields[i][j] = new Field(i, j);
                bottomLeftCorner.add(fields[i][j]);
            }
        }

        //generowanie dolnego prawego narożnika
        List<Field> bottomRightCorner = new ArrayList<>();
        for(int i = 9; i <= 12; i++){
            for(int j = 30 - i; j <= 12 + i; j += 2){
                fields[i][j] = new Field(i, j);
                bottomRightCorner.add(fields[i][j]);
            }
        }

        corners.add(upperCorner);
        corners.add(upperRightCorner);
        corners.add(bottomRightCorner);
        corners.add(bottomCorner);
        corners.add(bottomLeftCorner);
        corners.add(upperLeftCorner);
    }
}

package grab.szan.boardBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import grab.szan.Field;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ClassicBoard extends Board {

    public ClassicBoard(){
        colorMap = new HashMap<>();
        //default color
        colorMap.put(6, Color.LIGHTBLUE);
        
        //player pieces
        colorMap.put(0, Color.DARKGREEN);
        colorMap.put(1, Color.YELLOWGREEN);
        colorMap.put(2, Color.ORANGE);
        colorMap.put(3, Color.RED);
        colorMap.put(4, Color.PURPLE);
        colorMap.put(5, Color.BLUE);
    }

    @Override
    public void generateBoard(Pane pane) {
        corners = new ArrayList<>();
        fields = new Field[17][25];
        double radius = 15; 

        // Generowanie górnego narożnika
        List<Field> upperCorner  = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 12 - i; j <= 12 + i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                upperCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Generowanie górnego lewego narożnika
        List<Field> upperLeftCorner = new ArrayList<>();
        for (int i = 4; i <= 7; i++) {
            for (int j = i - 4; j <= 10 - i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                upperLeftCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Generowanie górnego prawego narożnika
        List<Field> upperRightCorner = new ArrayList<>();
        for (int i = 4; i <= 7; i++) {
            for (int j = 14 + i; j <= 28 - i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                upperRightCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Generowanie środkowej części 1.
        for (int i = 4; i <= 8; i++) {
            for (int j = 12 - i; j <= 12 + i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Generowanie środkowej części 2.
        for (int i = 9; i <= 12; i++) {
            for (int j = i - 4; j <= 28 - i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Generowanie dolnego narożnika
        List<Field> bottomCorner = new ArrayList<>();
        for (int i = 13; i <= 17; i++) {
            for (int j = i - 4; j <= 28 - i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                bottomCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Generowanie dolnego lewego narożnika
        List<Field> bottomLeftCorner = new ArrayList<>();
        for (int i = 9; i <= 12; i++) {
            for (int j = 12 - i; j <= i - 6; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                bottomLeftCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Generowanie dolnego prawego narożnika
        List<Field> bottomRightCorner = new ArrayList<>();
        for (int i = 9; i <= 12; i++) {
            for (int j = 30 - i; j <= 12 + i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                bottomRightCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Dodanie wszystkich narożników do listy corners
        corners.add(upperCorner);
        corners.add(bottomCorner);
        corners.add(upperLeftCorner);
        corners.add(bottomRightCorner);
        corners.add(upperRightCorner);
        corners.add(bottomLeftCorner);
    }

    @Override
    public Field getField(int i, int j) {
        return fields[i][j];
    }
}

package grab.szan.boardBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import grab.szan.Field;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Represents the classic board layout on the client side.
 * Extends {@link Board} and is responsible for generating
 * and displaying the classic board fields.
 */
public class ClassicBoard extends Board {

    /**
     * Constructs a ClassicBoard with a predefined color map.
     * The color map maps integer IDs to specific colors.
     */
    public ClassicBoard(){
        colorMap = new HashMap<>();
        // default color (6 stands for an empty field)
        colorMap.put(6, Color.LIGHTBLUE);

        // player pieces
        colorMap.put(0, Color.DARKGREEN);
        colorMap.put(1, Color.YELLOWGREEN);
        colorMap.put(2, Color.ORANGE);
        colorMap.put(3, Color.RED);
        colorMap.put(4, Color.PURPLE);
        colorMap.put(5, Color.BLUE);
    }

    /**
     * Generates and places all the fields for a classic board layout onto the specified {@link Pane}.
     * This method also constructs the corner lists for later reference.
     *
     * @param pane the JavaFX {@link Pane} to which the fields will be added
     */
    @Override
    public void generateBoard(Pane pane) {
        corners = new ArrayList<>();
        fields = new Field[17][25];
        double radius = 15;

        // Upper corner
        List<Field> upperCorner  = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 12 - i; j <= 12 + i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                upperCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Upper left corner
        List<Field> upperLeftCorner = new ArrayList<>();
        for (int i = 4; i <= 7; i++) {
            for (int j = i - 4; j <= 10 - i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                upperLeftCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Upper right corner
        List<Field> upperRightCorner = new ArrayList<>();
        for (int i = 4; i <= 7; i++) {
            for (int j = 14 + i; j <= 28 - i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                upperRightCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Middle part 1
        for (int i = 4; i <= 8; i++) {
            for (int j = 12 - i; j <= 12 + i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Middle part 2
        for (int i = 9; i <= 12; i++) {
            for (int j = i - 4; j <= 28 - i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Bottom corner
        List<Field> bottomCorner = new ArrayList<>();
        for (int i = 13; i <= 17; i++) {
            for (int j = i - 4; j <= 28 - i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                bottomCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Bottom left corner
        List<Field> bottomLeftCorner = new ArrayList<>();
        for (int i = 9; i <= 12; i++) {
            for (int j = 12 - i; j <= i - 6; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                bottomLeftCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Bottom right corner
        List<Field> bottomRightCorner = new ArrayList<>();
        for (int i = 9; i <= 12; i++) {
            for (int j = 30 - i; j <= 12 + i; j += 2) {
                fields[i][j] = new Field(radius, i, j);
                bottomRightCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        // Add all corners to the 'corners' list
        corners.add(upperCorner);
        corners.add(bottomCorner);
        corners.add(upperLeftCorner);
        corners.add(bottomRightCorner);
        corners.add(upperRightCorner);
        corners.add(bottomLeftCorner);
    }

    /**
     * Retrieves the {@link Field} at the specified row and column.
     *
     * @param i the row index
     * @param j the column index
     * @return the {@link Field} object, or null if it doesn't exist
     */
    @Override
    public Field getField(int i, int j) {
        return fields[i][j];
    }
}

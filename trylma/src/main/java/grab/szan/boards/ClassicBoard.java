package grab.szan.boards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import grab.szan.Field;

/**
 * Represents a classic board for the game.
 */
public class ClassicBoard extends Board {

    /**
     * Initializes the ClassicBoard with a predefined size and player directions.
     */
    public ClassicBoard() {
        super(17, 25); // Classic board size: 17 rows by 25 columns.
        dirs = new int[][]{{-1, -1}, {-1, 1}, {0, -2}, {0, 2}, {1, -1}, {1, 1}};

        // Maps the number of players to the corners they occupy.
        playersToCornersMap.put(2, Arrays.asList(0, 3));
        playersToCornersMap.put(3, Arrays.asList(0, 2, 4));
        playersToCornersMap.put(4, Arrays.asList(1, 2, 4, 5));
        playersToCornersMap.put(6, Arrays.asList(0, 1, 2, 3, 4, 5));
    }

    /**
     * Generates the board by populating fields and assigning them to the correct corners.
     */
    @Override
    public void generateBoard() {
        // Generate the upper corner.
        List<Field> upperCorner = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 12 - i; j <= 12 + i; j += 2) {
                fields[i][j] = new Field(i, j);
                upperCorner.add(fields[i][j]);
            }
        }

        // Generate the upper-left corner.
        List<Field> upperLeftCorner = new ArrayList<>();
        for (int i = 4; i <= 7; i++) {
            for (int j = i - 4; j <= 10 - i; j += 2) {
                fields[i][j] = new Field(i, j);
                upperLeftCorner.add(fields[i][j]);
            }
        }

        // Generate the upper-right corner.
        List<Field> upperRightCorner = new ArrayList<>();
        for (int i = 4; i <= 7; i++) {
            for (int j = 14 + i; j <= 28 - i; j += 2) {
                fields[i][j] = new Field(i, j);
                upperRightCorner.add(fields[i][j]);
            }
        }

        // Generate the middle section (first part).
        for (int i = 4; i <= 8; i++) {
            for (int j = 12 - i; j <= 12 + i; j += 2) {
                fields[i][j] = new Field(i, j);
            }
        }

        // Generate the middle section (second part).
        for (int i = 9; i <= 12; i++) {
            for (int j = i - 4; j <= 28 - i; j += 2) {
                fields[i][j] = new Field(i, j);
            }
        }

        // Generate the bottom corner.
        List<Field> bottomCorner = new ArrayList<>();
        for (int i = 13; i <= 17; i++) {
            for (int j = i - 4; j <= 28 - i; j += 2) {
                fields[i][j] = new Field(i, j);
                bottomCorner.add(fields[i][j]);
            }
        }

        // Generate the bottom-left corner.
        List<Field> bottomLeftCorner = new ArrayList<>();
        for (int i = 9; i <= 12; i++) {
            for (int j = 12 - i; j <= i - 6; j += 2) {
                fields[i][j] = new Field(i, j);
                bottomLeftCorner.add(fields[i][j]);
            }
        }

        // Generate the bottom-right corner.
        List<Field> bottomRightCorner = new ArrayList<>();
        for (int i = 9; i <= 12; i++) {
            for (int j = 30 - i; j <= 12 + i; j += 2) {
                fields[i][j] = new Field(i, j);
                bottomRightCorner.add(fields[i][j]);
            }
        }

        // Assign corners to the board.
        corners.add(upperCorner);
        corners.add(upperRightCorner);
        corners.add(bottomRightCorner);
        corners.add(bottomCorner);
        corners.add(bottomLeftCorner);
        corners.add(upperLeftCorner);
    }
}

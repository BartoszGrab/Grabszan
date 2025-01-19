package grab.szan.boardBuilders;

import java.util.HashMap;

import javafx.scene.paint.Color;

/**
 * Represents the Ying-Yang game board with specific color mappings.
 */
public class YingYangBoard extends ClassicBoard {

    /**
     * Initializes the Ying-Yang board with custom colors for players and empty fields.
     */
    public YingYangBoard() {
        colorMap = new HashMap<>();

        // Default color for empty fields.
        colorMap.put(6, Color.LIGHTBLUE);

        // Colors for player pieces.
        colorMap.put(0, Color.BLACK);
        colorMap.put(1, Color.BLACK);
        colorMap.put(2, Color.BLACK);
        colorMap.put(3, Color.WHITE);
        colorMap.put(4, Color.WHITE);
        colorMap.put(5, Color.WHITE);
    }
}

package grab.szan.boardBuilders;

import java.util.List;
import java.util.Map;

import grab.szan.Field;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public abstract class Board {
    protected Field[][] fields;
    protected Field firstField;
    protected List<List<Field>> corners;

    /**maps corner indexes with colors */
    protected Map<Integer, Color> colorMap;

    /**
     * generates board and updates corners
     * @param pane Pane object to which the generated board will be added
     */
    public abstract void generateBoard(Pane pane);

    /**
     * return field at specific position on board
     * @param i - row
     * @param j - column
     * @return Field object at given position
     */
    public abstract Field getField(int i, int j);

    /**
     * returns Color mapped with corner at index i
     * @param i - index
     * @return Color assigned to corner i
     */
    public Color getColor(int i){
        return colorMap.getOrDefault(i, Color.BLACK);
    }

}

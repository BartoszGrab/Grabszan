package grab.szan.boardBuilders;

import java.util.List;

import grab.szan.Field;
import javafx.scene.layout.Pane;


public abstract class Board {
    protected Field[][] fields;
    protected Field firstField;
    protected List<List<Field>> corners;

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

}

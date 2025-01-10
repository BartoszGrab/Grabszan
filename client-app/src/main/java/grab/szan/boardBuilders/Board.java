package grab.szan.boardBuilders;

import java.util.List;

import grab.szan.Field;
import javafx.scene.layout.Pane;

public abstract class Board {
    protected Field[][] fields;
    protected Field firstField;
    protected List<List<Field>> corners;

    public abstract void generateBoard(Pane pane);
    public abstract Field getField(int i, int j);

}

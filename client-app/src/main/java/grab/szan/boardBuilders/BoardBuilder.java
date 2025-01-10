package grab.szan.boardBuilders;

import grab.szan.Field;
import javafx.scene.layout.Pane;

public interface BoardBuilder {
    public void generateBoard(Pane pane);
    public Field getField(int i, int j);
}

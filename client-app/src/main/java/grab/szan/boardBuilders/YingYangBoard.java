package grab.szan.boardBuilders;

import java.util.HashMap;

import javafx.scene.paint.Color;

public class YingYangBoard extends ClassicBoard{
    public YingYangBoard(){
        colorMap = new HashMap<>();

        //default color
        colorMap.put(6, Color.LIGHTBLUE);

        //player pieces
        colorMap.put(0, Color.BLACK);
        colorMap.put(1, Color.BLACK);
        colorMap.put(2, Color.BLACK);
        colorMap.put(3, Color.WHITE);
        colorMap.put(4, Color.WHITE);
        colorMap.put(5, Color.WHITE);
    }
}

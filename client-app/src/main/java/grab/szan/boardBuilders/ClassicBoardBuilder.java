package grab.szan.boardBuilders;

import grab.szan.utils.Utils;
import javafx.scene.layout.Pane;

public class ClassicBoardBuilder implements BoardBuilder {

    @Override
    public void generateBoard(Pane pane) {
        double radius = 15; 
        double diameter = 2 * radius; 
        double verticalSpacingFactor = 1.5; // Mnożnik odległości w pionie
    
        // Generowanie górnego trójkąta
        for (int i = 0; i < 4; i++) {
            for (int j = 12 - i; j <= 12 + i; j += 2) {
                double x = j * diameter; 
                double y = i * diameter * verticalSpacingFactor; 
                Utils.addCircle(pane, x, y, radius);
            }
        }
    
        // Generowanie środkowej części 1
        for (int i = 4; i <= 8; i++) {
            for (int j = i - 4; j < 29 - i; j += 2) {
                double x = j * diameter; 
                double y = i * diameter * verticalSpacingFactor; 
                Utils.addCircle(pane, x, y, radius);
            }
        }
    
        // Generowanie środkowej części 2
        for (int i = 9; i <= 12; i++) {
            for (int j = 12 - i; j <= 12 + i; j += 2) {
                double x = j * diameter; 
                double y = i * diameter * verticalSpacingFactor; 
                Utils.addCircle(pane, x, y, radius);
            }
        }
    
        // Generowanie dolnego trójkąta
        for (int i = 13; i <= 17; i++) {
            for (int j = i - 4; j <= 28 - i; j += 2) {
                double x = j * diameter; 
                double y = i * diameter * verticalSpacingFactor;
                Utils.addCircle(pane, x, y, radius);
            }
        }
    }

}

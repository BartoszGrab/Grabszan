package grab.szan;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Field extends Circle{
    public Field(double radius, int i, int j){
        super();

        double diameter = radius * 1.5; 
        double verticalSpacingFactor = 1.5;
        double x = j * diameter + 20; 
        double y = i * diameter * verticalSpacingFactor + 20;
        
        setCenterX(x);
        setCenterY(y);
        setRadius(radius);
        setStroke(Color.BLACK);
        setFill(Color.LIGHTBLUE);
    }
}

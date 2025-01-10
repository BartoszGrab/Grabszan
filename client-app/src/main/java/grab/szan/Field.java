package grab.szan;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Field extends Circle{
    private int row;
    private int col;
    private int id;

    public Field(double radius, int i, int j){
        super();
        id = 0;

        row = i;
        col = j;

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

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void setFieldId(int id){
        this.id = id;
    }

    public int getFieldId(){
        return id;
    }
}

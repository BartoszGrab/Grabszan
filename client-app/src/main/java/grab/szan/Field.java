package grab.szan;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Represents a single field (circle) on the client-side board.
 * Each field has coordinates (row, col), an ID representing its occupant,
 * and methods for highlighting or un-highlighting itself.
 */
public class Field extends Circle {

    /**
     * The row index of this field.
     */
    private int row;

    /**
     * The column index of this field.
     */
    private int col;

    /**
     * The field's ID (6 indicates empty, otherwise it corresponds to a player's ID).
     */
    private int id;

    /**
     * Creates a new Field (circle) with the specified radius, row, and column.
     * Calculates its position on the board based on row, column, and radius.
     *
     * @param radius the radius of the circle
     * @param i      the row index
     * @param j      the column index
     */
    public Field(double radius, int i, int j){
        super();
        id = 6; // 6 means empty field by default

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

    /**
     * Gets the row index of this field.
     *
     * @return the row index
     */
    public int getRow(){
        return row;
    }

    /**
     * Gets the column index of this field.
     *
     * @return the column index
     */
    public int getCol(){
        return col;
    }

    /**
     * Sets the ID of this field.
     *
     * @param id the new ID for the field
     */
    public void setFieldId(int id){
        this.id = id;
    }

    /**
     * Gets the ID of this field.
     *
     * @return the field ID
     */
    public int getFieldId(){
        return id;
    }

    /**
     * Highlights this field by changing its stroke color and width.
     */
    public void highlight() {
        setStroke(Color.YELLOW);
        setStrokeWidth(3);
    }

    /**
     * Removes the highlight from this field, resetting stroke color and width to default.
     */
    public void removeHighlight() {
        setStroke(Color.BLACK);
        setStrokeWidth(1);
    }
}

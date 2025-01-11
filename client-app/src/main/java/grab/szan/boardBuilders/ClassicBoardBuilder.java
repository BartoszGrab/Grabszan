package grab.szan.boardBuilders;

import java.util.ArrayList;
import java.util.List;

import grab.szan.Client;
import grab.szan.Field;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ClassicBoardBuilder extends Board {

    @Override
    public void generateBoard(Pane pane) {
        corners = new ArrayList<>();
        fields = new Field[17][25];
        double radius = 15; 

        //generowanie gornego naroznika
        List<Field> upperCorner  = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            for(int j = 12-i; j <= 12+i; j += 2){
                fields[i][j] = new Field(radius, i, j);
                upperCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        //generowanie gornego lewego narożnika
        List<Field> upperLeftCorner = new ArrayList<>();
        for(int i = 4; i <= 7; i++){
            for(int j = i-4; j <= 10 - i; j += 2){
                fields[i][j] = new Field(radius, i, j);
                upperLeftCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        //generowanie gornego prawego naroznika
        List<Field> upperRightCorner = new ArrayList<>();
        for(int i = 4; i <= 7; i++){
            for(int j = 14 + i; j <= 28 - i; j += 2){
                fields[i][j] = new Field(radius, i, j);
                upperRightCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        //generowanie środkowej czesci 1.
        for(int i = 4; i <= 8; i++){
            for(int j = 12 - i; j <= 12 + i; j += 2){
                fields[i][j] = new Field(radius, i, j);
                pane.getChildren().add(fields[i][j]);
            }
        }

        //generowanie srodkowej czesci 2.
        for(int i = 9; i <= 12; i++){
            for(int j = i - 4; j<= 28 - i; j += 2){
                fields[i][j] = new Field(radius, i, j);
                pane.getChildren().add(fields[i][j]);
            }
        }

        //generowanie dolnego naroznika
        List<Field> bottomCorner = new ArrayList<>();
        for(int i = 13; i <= 17; i++){
            for(int j = i-4; j <= 28-i; j += 2){
                fields[i][j] = new Field(radius, i, j);
                bottomCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        //generowanie dolnego lewego narożnika
        List<Field> bottomLeftCorner = new ArrayList<>();
        for(int i = 9; i <= 12; i++){
            for(int j = 12 - i; j <= i - 6; j += 2){
                fields[i][j] = new Field(radius, i, j);
                bottomLeftCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        //generowanie dolnego prawego narożnika
        List<Field> bottomRightCorner = new ArrayList<>();
        for(int i = 9; i <= 12; i++){
            for(int j = 30 - i; j <= 12 + i; j += 2){
                fields[i][j] = new Field(radius, i, j);
                bottomRightCorner.add(fields[i][j]);
                pane.getChildren().add(fields[i][j]);
            }
        }

        corners.add(upperCorner);
        corners.add(bottomCorner);
        corners.add(upperLeftCorner);
        corners.add(bottomRightCorner);
        corners.add(upperRightCorner);
        corners.add(bottomLeftCorner);  

        pane.setOnMouseClicked(event -> onClick(event));
    }

    @Override
    public Field getField(int i, int j) {
        return fields[i][j];
    }

    /**
     * this method should be called when user clicks on the Pane that contains this Board object
     * @param e mouse event
     */
    private void onClick(MouseEvent e) {
        Node node = e.getPickResult().getIntersectedNode();
        //check if node we clicked on is actually instance of Field
        if(!(node instanceof Field)){
            return;
        }

        Field field = (Field)node;
        if(firstField == null || firstField.getFieldId() != Client.getInstance().getId()){
            firstField = field;
            return;
        }

        //send move request to server
        Client.getInstance().sendToServer("move " + firstField.getRow() + " " + firstField.getCol() + " " + field.getRow() + " " + field.getCol());
        firstField = null;
    }

}

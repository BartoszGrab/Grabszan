package grab.szan.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Utils {
    public static void showAlert(String title, String message){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public static void addCircle(Pane pane, double x, double y, double radius) {
        Circle circle = new Circle(x, y, radius);
        circle.setStroke(Color.BLACK); // Obrys okręgu
        circle.setFill(Color.LIGHTBLUE); // Wypełnienie okręgu
        pane.getChildren().add(circle);
    }
    
    public static void addHexagon(Pane pane, double x, double y, double radius) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i); // Konwertuj na radiany
            double xPoint = x + radius * Math.cos(angle);
            double yPoint = y + radius * Math.sin(angle);
            hexagon.getPoints().addAll(xPoint, yPoint);
        }
        hexagon.setStroke(Color.BLACK);
        hexagon.setFill(Color.LIGHTBLUE);
        pane.getChildren().add(hexagon);
    }
}

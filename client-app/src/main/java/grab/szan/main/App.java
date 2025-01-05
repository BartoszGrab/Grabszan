package grab.szan.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Ładowanie pliku FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grab/szan/view/MenuView.fxml"));
        Parent root = loader.load();

        // Ustawienie sceny
        Scene scene = new Scene(root);
        primaryStage.setTitle("Grabszan Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

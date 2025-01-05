package grab.szan.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showConnectingView();
    }

    // Metoda do wyświetlania widoku łączenia z serwerem
    public static void showConnectingView() throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/grab/szan/view/ConnectingServerView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Connect to Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metoda do wyświetlania widoku menu
    public static void showMenuView() throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/grab/szan/view/MenuView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}

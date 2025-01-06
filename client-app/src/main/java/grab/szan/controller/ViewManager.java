package grab.szan.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {

    private static Stage primaryStage;


    public static void init(Stage stage) {
        primaryStage = stage;
    }


    public static void showConnectingView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/ConnectingServerView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Connect to Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void showMenuView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/MenuView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showCreateGameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/CreateGameView.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Create Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

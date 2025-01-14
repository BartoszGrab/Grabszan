package grab.szan.controller;

import java.io.IOException;

import grab.szan.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {

    private static Stage primaryStage;
    private static Controller currentController;

    /**
     * @return Controller of the current view
     */
    public static Controller getController() {
        return currentController;
    }

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static void showConnectingView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/ConnectingServerView.fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        primaryStage.setTitle("Connect to Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMenuView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/MenuView.fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showCreateGameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/CreateGameView.fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        primaryStage.setTitle("Create Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showJoinGameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/JoinGameView.fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        primaryStage.setTitle("Join Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showGameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/GameView.fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        String roomName = Client.getInstance().getRoomName();
        String nickname = Client.getInstance().getNickname();
        primaryStage.setTitle(roomName + " " + nickname);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

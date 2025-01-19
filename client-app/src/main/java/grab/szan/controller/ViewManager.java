package grab.szan.controller;

import java.io.IOException;

import grab.szan.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages switching between different views (scenes) in the JavaFX application.
 */
public class ViewManager {

    private static Stage primaryStage;
    private static Controller currentController;

    /**
     * Gets the controller of the currently displayed view.
     *
     * @return the current {@link Controller} instance
     */
    public static Controller getController() {
        return currentController;
    }

    /**
     * Initializes the ViewManager with a primary {@link Stage}.
     *
     * @param stage the primary Stage
     */
    public static void init(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Shows the view for connecting to the server.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public static void showConnectingView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/ConnectingServerView.fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        primaryStage.setTitle("Connect to Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the main menu view.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public static void showMenuView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/MenuView.fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the Create Game view.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public static void showCreateGameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/CreateGameView.fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        primaryStage.setTitle("Create Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the Join Game view.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public static void showJoinGameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource("/grab/szan/view/JoinGameView.fxml"));
        Scene scene = new Scene(loader.load());
        currentController = loader.getController();
        primaryStage.setTitle("Join Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Shows the in-game view (GameView).
     *
     * @throws IOException if the FXML file cannot be loaded
     */
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

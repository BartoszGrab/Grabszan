package grab.szan.main;

import grab.szan.commands.CommandHandler;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point for the application.
 */
public class App extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage for the application.
     * @throws Exception if an error occurs during startup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        CommandHandler.getCommandHandler();
        Utils.configureUtils();
        ViewManager.init(stage);
        ViewManager.showConnectingView();
    }

    /**
     * The main method, used to launch the application.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

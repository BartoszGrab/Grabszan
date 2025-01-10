package grab.szan.main;

import grab.szan.commands.CommandHandler;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        CommandHandler.getCommandHandler();
        Utils.configureUtils();
        ViewManager.init(stage);
        ViewManager.showConnectingView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

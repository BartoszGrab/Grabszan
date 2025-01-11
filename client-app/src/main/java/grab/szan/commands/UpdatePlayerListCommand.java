package grab.szan.commands;
import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import javafx.application.Platform;

public class UpdatePlayerListCommand implements Command{

    @Override
    public void execute(String[] args) {
        Platform.runLater(() -> {
            if(!(ViewManager.getController() instanceof GameViewController)){
                return;
            }
            GameViewController gameController = (GameViewController)ViewManager.getController();
            gameController.addPlayer(args[1]);
        });
    }
    
}

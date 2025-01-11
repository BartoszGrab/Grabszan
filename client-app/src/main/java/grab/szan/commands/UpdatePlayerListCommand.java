package grab.szan.commands;

import grab.szan.controller.Controller;
import grab.szan.controller.GameViewController;
import javafx.application.Platform;

public class UpdatePlayerListCommand implements Command{

    @Override
    public void execute(String[] args, Controller controller) {
        Platform.runLater(() -> {
            if(!(controller instanceof GameViewController)){
                return;
            }
            GameViewController gameController = (GameViewController)controller;
            gameController.addPlayer(args[1]);
        });
    }
    
}

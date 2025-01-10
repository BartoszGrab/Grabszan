package grab.szan.commands;

import java.io.IOException;

import grab.szan.Client;
import grab.szan.boardBuilders.BoardHandler;
import grab.szan.controller.Controller;
import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;
import javafx.application.Platform;

public class AcceptJoinCommand implements Command {

    @Override
    public void execute(String[] args, Controller controller) {
        if(args.length < 4){
            Utils.showAlert("Error", "Error occured while accepting join request");
            return;
        }

        GameViewController.setBuilder(BoardHandler.getBoardHandler().getBoard(args[1]));

        Platform.runLater(() -> {
            try {
                ViewManager.showGameView();
                Client.getInstance().setId(Integer.parseInt(args[2]));
                GameViewController gameController = (GameViewController)Client.getInstance().getCurrentController();
                gameController.addPlayer(args[3]);
            } catch(IOException e){
                e.printStackTrace();
                Utils.showAlert("Error", "Unknown error while displaying game view");
            } catch(NumberFormatException e){
                e.printStackTrace();
                Utils.showAlert("Error", "unknown error occured when assigning player id");
            }
        });

    }
    
}

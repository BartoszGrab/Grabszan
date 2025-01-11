package grab.szan.commands;

import java.io.IOException;

import grab.szan.Client;
import grab.szan.boardBuilders.BoardHandler;
import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;
import javafx.application.Platform;

/**
 * this command should be executed when server accepts our join request
 */
public class AcceptJoinCommand implements Command {

    @Override
    public void execute(String[] args) {
        if(args.length < 3){
            Utils.showAlert("Error", "Error occured while accepting join request");
            return;
        }

        GameViewController.setBuilder(BoardHandler.getBoardHandler().getBoard(args[1]));

        Platform.runLater(() -> {
            try {
                //displaying game view
                ViewManager.showGameView();
                Client.getInstance().setId(Integer.parseInt(args[2]));
                GameViewController gameController = (GameViewController)ViewManager.getController();
                for(int i = 3; i < args.length; i++){
                    gameController.addPlayer(args[i]);
                }
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

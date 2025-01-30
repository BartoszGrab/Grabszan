package grab.szan.commands;

import java.io.IOException;

import grab.szan.boardBuilders.BoardHandler;
import grab.szan.controller.ReplayController;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;
import javafx.application.Platform;

public class AcceptReplayCommand implements Command {

    @Override
    public void execute(String[] args) {
        if(args.length < 3) {
            Utils.showAlert("Error", "Unexpected error occured when accepting replay request.");
            return;
        }
        try {

            String gameType = args[1];
            int maxPlayers = Integer.parseInt(args[2]);

            Platform.runLater(() -> {
                try {
                    ViewManager.showReplayView();
                    ReplayController replayController = (ReplayController) ViewManager.getController();
                    
                    replayController.setBoard(BoardHandler.getBoardHandler().getBoard(gameType), maxPlayers);

                } catch (IOException e) {
                    e.printStackTrace();
                    Utils.showAlert("Error", "Unknown error while displaying game view");
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
}

package grab.szan.commands;

import java.io.IOException;

import grab.szan.Client;
import grab.szan.boardBuilders.BoardHandler;
import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;
import javafx.application.Platform;

/**
 * Komenda wykonywana po zaakceptowaniu żądania dołączenia,
 * zawiera komunikat: 
 * acceptJoin <gameType> <roomName> <playerId> <[lista innych graczy…]> <nickname>
 */
public class AcceptJoinCommand implements Command {

    @Override
    public void execute(String[] args) {
        // Spodziewamy się minimum: acceptJoin gameType roomName playerId nickname
        if (args.length < 5) {
            Utils.showAlert("Error", "Error occurred while accepting join request (too few arguments)");
            return;
        }
        
        try {
            // args[1] - gameType, args[2] - roomName, args[3] - playerId,
            // args[args.length - 1] - nickname
            String gameType = args[1];
            String roomName = args[2];
            int playerId = Integer.parseInt(args[3]);
            String nickname = args[args.length - 1]; 

            // Ustawiamy lokalne dane klienta
            Client.getInstance().setId(playerId);
            Client.getInstance().setRoomName(roomName);
            Client.getInstance().setNickname(nickname);

            // Przygotowujemy listę graczy – argumenty od indeksu 4 do args.length - 2
            // (jeśli nie ma innych graczy, pętla nie wykona się)
            Platform.runLater(() -> {
                try {
                    ViewManager.showGameView();
                    GameViewController gameController = (GameViewController) ViewManager.getController();

                    //setting up board for chosen game mode
                    gameController.setBoard(BoardHandler.getBoardHandler().getBoard(gameType));
                    
                    for (int i = 4; i < args.length; i++) {
                        gameController.addPlayer(args[i]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Utils.showAlert("Error", "Unknown error while displaying game view");
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Utils.showAlert("Error", "Error occurred when parsing player id");
        }
    }
}

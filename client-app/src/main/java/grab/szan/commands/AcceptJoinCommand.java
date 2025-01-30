package grab.szan.commands;

import java.io.IOException;

import grab.szan.Client;
import grab.szan.boardBuilders.BoardHandler;
import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;
import javafx.application.Platform;

/**
 * Command that is executed when the server accepts a player's join request.
 * <p>
 * Usage example:
 * <pre>
 * acceptJoin &lt;gameType&gt; &lt;roomName&gt; &lt;playerId&gt; &lt;...other players...&gt; &lt;nickname&gt;
 * </pre>
 */
public class AcceptJoinCommand implements Command {

    /**
     * Executes the acceptJoin command. Initializes client data such as game type,
     * room name, player ID, nickname, and updates the client UI accordingly.
     *
     * @param args an array where args[1] is the game type, args[2] is the room name,
     *             args[3] is the player ID, and args[args.length - 1] is the player's nickname
     */
    @Override
    public void execute(String[] args) {
        // Expecting at least: acceptJoin gameType roomName playerId nickname
        if (args.length < 5) {
            Utils.showAlert("Error", "Error occurred while accepting join request (too few arguments)");
            return;
        }

        try {
            String gameType = args[1];
            String roomName = args[2];
            int playerId = Integer.parseInt(args[3]);
            String nickname = args[args.length - 1];

            // Set client data
            Client.getInstance().setId(playerId);
            Client.getInstance().setRoomName(roomName);
            Client.getInstance().setNickname(nickname);

            // Prepare list of players (arguments from index 4 to args.length - 2)
            Platform.runLater(() -> {
                try {
                    ViewManager.showGameView();
                    GameViewController gameController = (GameViewController) ViewManager.getController();

                    // Set up board for the chosen game mode
                    gameController.setBoard(BoardHandler.getBoardHandler().getBoard(gameType));

                    gameController.addPlayer(args[4]);
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

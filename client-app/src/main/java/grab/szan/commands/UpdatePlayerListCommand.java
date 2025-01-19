package grab.szan.commands;

import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import javafx.application.Platform;

/**
 * Command that updates the client's player list in the {@link GameViewController}.
 * <p>
 * Usage: updateList &lt;playerNickname&gt;
 */
public class UpdatePlayerListCommand implements Command {

    /**
     * Executes the updateList command to add a new player to the player's list in the UI.
     *
     * @param args the command arguments; args[1] is the nickname of the new player
     */
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

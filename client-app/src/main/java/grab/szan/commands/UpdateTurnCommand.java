package grab.szan.commands;

import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import javafx.application.Platform;

/**
 * Command that updates the UI to indicate whose turn it is.
 * <p>
 * Usage: updateTurn &lt;playerNickname&gt;
 */
public class UpdateTurnCommand implements Command {

    /**
     * Executes the updateTurn command.
     *
     * @param args the command arguments; args[1] is the nickname of the player whose turn it is
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            return;
        }
        String playerNickname = args[1];
        Platform.runLater(() -> {
            if (!(ViewManager.getController() instanceof GameViewController)) {
                return;
            }
            GameViewController controller = (GameViewController) ViewManager.getController();
            controller.updateTurnLabel(playerNickname);
        });
    }
}

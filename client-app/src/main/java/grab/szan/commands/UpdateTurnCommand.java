package grab.szan.commands;

import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import javafx.application.Platform;

public class UpdateTurnCommand implements Command {

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

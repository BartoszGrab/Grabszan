package grab.szan.commands;

import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;

/**
 * Command responsible for changing the color of a player's nickname in the {@link GameViewController#playerListView}
 * so that the nickname color matches the color of the player's pieces on the board.
 */
public class ChangePlayerColorCommand implements Command {

    /**
     * Executes the changeColor command.
     * <p>
     * Usage: changeColor &lt;nickname&gt; &lt;color id&gt;
     *
     * @param args the command arguments
     */
    @Override
    public void execute(String[] args) {
        if(args.length < 3){
            Utils.showAlert("Error", "too few arguments usage changeColor <nickname> <color id>");
            return;
        }

        if(!(ViewManager.getController() instanceof GameViewController)){
            return;
        }

        GameViewController controller = (GameViewController)ViewManager.getController();

        try {
            int colorId = Integer.parseInt(args[2]);
            controller.changeColor(controller.getId(args[1]), controller.getBoard().getColor(colorId));
        } catch(NumberFormatException e){
            e.printStackTrace();
        }
    }
}

package grab.szan.commands;

import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;

/**Command responsible for changing color at index i in GameViewController.playerListView so that the color of nickname
 * matches the color of player's pieces on board
*/
public class ChangePlayerColorCommand implements Command{

    @Override
    public void execute(String[] args) {
        if(args.length < 3){
            Utils.showAlert("Error", "too few arguments usage changeColor <nickname> <color id>");
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

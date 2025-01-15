package grab.szan.commands;

import grab.szan.Client;
import grab.szan.utils.Utils;

/**Command responsible for changing client id by server (used when starting game 
 * because id of the corner where each player starts might vary for different number of players in the game)*/ 
public class SetIdCommand implements Command {

    @Override
    public void execute(String[] args) {
        if(args.length < 2){
            Utils.showAlert("Error", "Unexpected error while setting id (too few arguments)");
            return;
        }

        try{
            int id = Integer.parseInt(args[1]);
            Client.getInstance().setId(id);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
    
}

package grab.szan.commands;

import grab.szan.Client;
import grab.szan.utils.Utils;

/**
 * Command responsible for setting the client's ID, usually triggered by the server
 * when the game starts or a player's ID is reassigned.
 */
public class SetIdCommand implements Command {

    /**
     * Executes the setId command.
     * <p>
     * Usage: setId &lt;newId&gt;
     *
     * @param args the command arguments, where args[1] is the new ID
     */
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

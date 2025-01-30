package grab.szan.commands;

import grab.szan.Field;
import grab.szan.boardBuilders.Board;
import grab.szan.controller.GameViewController;
import grab.szan.controller.ReplayController;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;

/**
 * A command that updates the state of a specific field on the board (e.g., setting player pieces).
 */
public class SetFieldCommand implements Command {

    /**
     * Executes the 'set' command to modify a field on the board.
     * <p>
     * Usage: set &lt;row&gt; &lt;column&gt; &lt;id&gt;
     *
     * @param args the command arguments, where args[1] is the row, args[2] is the column, and args[3] is the ID
     */
    @Override
    public void execute(String[] args) {
        if(args.length < 4){
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board (too few arguments)");
            return;
        }

        if(!(ViewManager.getController() instanceof GameViewController) && !(ViewManager.getController() instanceof ReplayController)){
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board (wrong view)");
            return;
        }
        Board board;
        if(ViewManager.getController() instanceof GameViewController)
            board = ((GameViewController)ViewManager.getController()).getBoard();
        else
            board = ((ReplayController)ViewManager.getController()).getBoard();

        try{
            int y = Integer.parseInt(args[1]); // row
            int x = Integer.parseInt(args[2]); // column
            int id = Integer.parseInt(args[3]); // field ID

            Field field = board.getField(y, x);
            field.setFill(board.getColor(id));
            field.setFieldId(id);

        } catch(NumberFormatException e){
            e.printStackTrace();
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board");
        }
    }
}

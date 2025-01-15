package grab.szan.commands;

import grab.szan.Field;
import grab.szan.boardBuilders.Board;
import grab.szan.controller.GameViewController;
import grab.szan.controller.ViewManager;
import grab.szan.utils.Utils;

/**
 * this command should be executed when servers wants to change information about specific field on the board
 */
public class SetFieldCommand implements Command{

    @Override
    public void execute(String[] args) {
        if(args.length < 4){
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board (too few arguments)");
            return;
        }

        if(!(ViewManager.getController() instanceof GameViewController)){
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board (wrong view)");
            return;
        }

        Board board = ((GameViewController)ViewManager.getController()).getBoard();

        try{
            //row
            int y = Integer.parseInt(args[1]);
            //column
            int x = Integer.parseInt(args[2]);
            //id
            int id = Integer.parseInt(args[3]);

            Field field = board.getField(y, x);
            field.setFill(board.getColor(id));
            field.setFieldId(id);
            
        } catch(NumberFormatException e){
            e.printStackTrace();
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board");
            return;
        }
        
    }
    
}

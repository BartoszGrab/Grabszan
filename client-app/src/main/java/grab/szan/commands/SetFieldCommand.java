package grab.szan.commands;

import grab.szan.Field;
import grab.szan.boardBuilders.Board;
import grab.szan.controller.Controller;
import grab.szan.controller.GameViewController;
import grab.szan.utils.Utils;

public class SetFieldCommand implements Command{

    @Override
    public void execute(String[] args, Controller controller) {
        Board builder = GameViewController.getBoardBuilder();
        if(builder == null || args.length < 4){
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board");
            return;
        }

        try{
            int y = Integer.parseInt(args[1]);
            int x = Integer.parseInt(args[2]);
            int id = Integer.parseInt(args[3]);
            Field field = builder.getField(y, x);
            field.setFill(Utils.getColorById(id));
            field.setFieldId(id);
            
        } catch(NumberFormatException e){
            e.printStackTrace();
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board");
            return;
        }
        
    }
    
}

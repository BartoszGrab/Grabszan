package grab.szan.commands;

import grab.szan.Field;
import grab.szan.boardBuilders.BoardBuilder;
import grab.szan.controller.GameViewController;
import grab.szan.utils.Utils;

public class SetFieldCommand implements Command{

    @Override
    public void execute(String[] args) {
        BoardBuilder builder = GameViewController.getBoardBuilder();
        if(builder == null || args.length < 4){
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board");
            return;
        }

        try{
            int y = Integer.parseInt(args[1]);
            int x = Integer.parseInt(args[2]);
            int id = Integer.parseInt(args[3]);
            Field field = builder.getField(x, y);
            field.setFill(Utils.getColorById(id));
            
        } catch(NumberFormatException e){
            e.printStackTrace();
            Utils.showAlert("Error", "Unexpected error occured while setting pieces on board");
            return;
        }
        
    }
    
}

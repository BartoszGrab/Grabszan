package grab.szan.commands;

import grab.szan.boardBuilders.BoardHandler;
import grab.szan.controller.GameViewController;

public class SetGameModeCommand implements Command {

    @Override
    public void execute(String[] args) {
        if(args.length < 2){
            return;
        }

        GameViewController.setBuilder(BoardHandler.getBoardHandler().getBoard(args[1]));
    }
    
}
